package com.linda.projet.projet.services.auth;

import com.linda.projet.projet.repositories.UserRepository;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    @Transactional
    //renvoyer le firstname etc ..  duser enf onctionde son email ! pour vérifier si luser existe ou pas dans le cas ou je vais m'authentifier  siexiste on le renvoie sinon on renvoie une exception
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("No user was found with the provided email"));
    }
}