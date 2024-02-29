package com.linda.projet.projet.services;

import com.linda.projet.projet.dto.AuthenticationRequest;
import com.linda.projet.projet.dto.AuthenticationResponse;
import com.linda.projet.projet.dto.UserDto;
import com.linda.projet.projet.models.User;

public interface UserService extends AbstractService<UserDto>{

    User getUserById(Integer userId);

    //valider un compte utilisateur
    Integer validateAccount(Integer id);

    // un userpeut invalider ou desactiver un compte user suite a des actiosn anormals
    Integer invalidateAccount(Integer id);

    AuthenticationResponse register(UserDto user);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
