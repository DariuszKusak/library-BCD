package com.library.bcd.librarybcd.controller;

import com.library.bcd.librarybcd.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/basicAuth")
public class ValidateUserController {

    @Autowired
    JWTService jwtService;

    @RequestMapping("validate")
    public Map<String, String> userIsValid() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) auth.getPrincipal();
        String name = principal.getUsername();
        String role = principal.getAuthorities().toArray()[0].toString().substring(5);

        String token = jwtService.generateToken(name, role);

        Map<String, String> results = new HashMap<>();
        results.put("result", token);

        return results;
    }

}
