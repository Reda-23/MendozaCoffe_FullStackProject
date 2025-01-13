package org.mendozaproject.services.impl;

import org.mendozaproject.models.Users;
import org.mendozaproject.repos.UsersRepository;
import org.mendozaproject.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;


    public UsersService() {
    }

    public Users register(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    public String verify(String username,String password){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        if (authentication.isAuthenticated()){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
             return jwtService.generateToken(userDetails);
        }else {
            return "Authentication Failed";
        }
    }




}
