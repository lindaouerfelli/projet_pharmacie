package com.linda.projet.projet.controllers;

import com.linda.projet.projet.config.JwtUtils;
import com.linda.projet.projet.dto.AuthenticationRequest;
import com.linda.projet.projet.dto.AuthenticationResponse;
import com.linda.projet.projet.dto.UserDto;
import com.linda.projet.projet.services.UserService;
//import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {



    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserDto user)

    {
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) // ici on a cree un objet authenticationrequest qui contient suelement le usename et le password ou bien on pouvait appeler le userdto directement cat il contient le password et leusrname et dautres infos

    {
        return ResponseEntity.ok(userService.authenticate(request));
    }

}
