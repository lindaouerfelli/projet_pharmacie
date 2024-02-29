package com.linda.projet.projet.services.impl;

import com.linda.projet.projet.config.JwtUtils;
import com.linda.projet.projet.dto.AuthenticationRequest;
import com.linda.projet.projet.dto.AuthenticationResponse;
import com.linda.projet.projet.dto.UserDto;
import com.linda.projet.projet.models.Role;
import com.linda.projet.projet.models.User;
import com.linda.projet.projet.repositories.RoleRepository;
import com.linda.projet.projet.repositories.UserRepository;
import com.linda.projet.projet.services.UserService;
import com.linda.projet.projet.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ObjectsValidator<UserDto> validator;
    private final RoleRepository roleRepository;

    private  final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private  final AuthenticationManager authManager;
    private static final String ROLE_USER = "ROLE_USER";

    @Override
    public User getUserById(Integer userId) {
        return repository.findById(userId).orElse(null);
    }

    @Override
    public Integer save(UserDto dto) {

        validator.validate(dto);
        User user = UserDto.toEntity(dto);
        return repository.save(user).getId();


    }

    @Override
    @Transactional
    public List<UserDto> findAll() {
        return repository.findAll()
                .stream()
                .map(UserDto::fromEntity)
                //.map(u -> UserDto.fromEntity(u))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Integer id) {
        return repository.findById(id)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No user was found" ));
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);


    }

    @Override
    public Integer validateAccount(Integer id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("NO USER WAS FOUND FOR USER ACCOUNT VALIDATION"));

        user.setActive(true);
        repository.save(user);
        return user.getId();
    }

    @Override
    public Integer invalidateAccount(Integer id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("NO USER WAS FOUND FOR USER ACCOUNT VALIDATION"));

        user.setActive(false);
        repository.save(user);
        return user.getId();
    }

    @Override
    @Transactional
    public AuthenticationResponse register(UserDto dto) {
        validator.validate(dto);
        User user = UserDto.toEntity(dto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(findOrCreateRole(ROLE_USER));

        var savedUser = repository.save(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", savedUser.getId());
        claims.put("fullName", savedUser.getFirstname() + " " + savedUser.getLastname());
        String token = jwtUtils.generateToken(savedUser, claims);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        final User user = repository.findByEmail(request.getEmail()).get();
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("fullName", user.getFirstname() + " " + user.getLastname());
        final String token = jwtUtils.generateToken(user, claims);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    private Role findOrCreateRole(String roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElse(null);
        if (role == null) {
            return roleRepository.save(
                    Role.builder()
                            .name(roleName)
                            .build()
            );
        }
        return role;
    }
}
