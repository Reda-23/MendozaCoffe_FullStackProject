package org.mendozaproject.web;


import org.mendozaproject.models.Users;
import org.mendozaproject.security.JWTService;
import org.mendozaproject.services.impl.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class UsersController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private JWTService jwtService;

    @PostMapping("/register")
    public Users register(@RequestBody Users users){
        return usersService.register(users);
    }
    @PostMapping("/login")
    public String login(@RequestBody Users users){
        return usersService.verify(users.getUsername(), users.getPassword());
    }
    @GetMapping("/auth")
    public Authentication authentication( Authentication authentication){
        return authentication;
    }



}
