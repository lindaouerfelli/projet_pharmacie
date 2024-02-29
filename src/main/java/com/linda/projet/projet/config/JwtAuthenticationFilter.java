package com.linda.projet.projet.config;

import com.linda.projet.projet.repositories.UserRepository;
import java.io.IOException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

// la on a implémneté notre jwt filtre

// des que luser envoie une requete la premiere contact ca sera avec le JwTAuthenticationFilter et il va jouer le role dunfiltre pour filtrer les infos ou les users
// de facon global pour voir si cet user peut accéder a notre api ou non !
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter { // unfiltre par requete

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";


    // cette methode va faire le necessaire pour filtrer toute requete entrante anotre systeme !
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    { // cette methode va filtrer totue requete entrante a notre systeme

        String authHeader = request.getHeader(AUTHORIZATION);
        String userEmail;
        String jwt;

        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            filterChain.doFilter(request, response); //ici si le header est vide on va passer a executrerles autres flter le filterchain
            return; // cad je passe pas a lasuite --> ca cest un void pas un type de retour
            // si jai pas de token je vais appeler le filterchain pour coninuer a exécuter les autres filtres
            // cad ici si jai pas de token je suis pas authentifié cad jappel el filterchain pour s'exécuter et parmiles filtres quil va exécuter
            // cest mon filtre jwtAuthentificationFilter qui va dispatcher le servlet pour exécuter les requets par exemple qui ont
            // pas besoin detre authentifier pour s'exécuter ! et si jai un token je vais passer juste a la partie suivante
            // de cette classe et faire l'AUTHENTIFICAION et une fois lauth sera faite je passe a exécuter aussi le reste de chainfilter
            // pour dispatcher le servlet et excéuter le reste des requete et des servoces par exemple rajouter une transaction !
        }

        // dans le cas ou jai fais le register.
        // dans ce cas cest a die jai un token cest a dire je suis authentifié !!!!

        jwt = authHeader.substring(7);
        userEmail = jwtUtils.extractUsername(jwt);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserDetails userDetails = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new EntityNotFoundException("User not found while validation JWT"));
            if (jwtUtils.isTokenValid(jwt, userDetails))

            { /// je valide et je vérifie que jai déjaluser dans ma bd avec cette ligne te ensuite je verifie quele token est valide pou rcet user je dois
                //mettre a jour le contexte de sécurité cad je dois authentifier et dire a spring mon user est désormias authentifié
                // puis on doit préciser dou vient cette requete entrnte et onlajoute dans un obket detype WebAuthenticationDetailsSource et enfin on dit a spring voila cest ca l'authentification

                // ici je vais creer un objet  pur lauthentification de user
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response); // ceci pour que apres avoir authentifié le user je continue le reste
        /// le reste = continuer a dispatcher le servlet cest a dire continuer a

    }
}

