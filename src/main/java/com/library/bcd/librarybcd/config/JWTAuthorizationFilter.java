package com.library.bcd.librarybcd.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.library.bcd.librarybcd.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    JWTService jwtService;

    @Autowired
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Cookie[] cookies = request.getCookies();

        if (cookies == null || cookies.length == 0) {
            chain.doFilter(request, response);
            return;
        }

        Cookie tokenCookie = null;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                tokenCookie = cookie;
            }
        }

        if (tokenCookie == null) {
            chain.doFilter(request, response);
            return;
        }

        if (jwtService == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            jwtService = wac.getBean(JWTService.class);
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(tokenCookie.getValue());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String jwtToken) {
        try {
            String payload = jwtService.validateToken(jwtToken);
            JsonParser jsonParser = JsonParserFactory.getJsonParser();
            Map<String, Object> payloadMap = jsonParser.parseMap(payload);
            String user = payloadMap.get("user").toString();
            String role = payloadMap.get("role").toString();
            List<GrantedAuthority> roles = new ArrayList<>();
            GrantedAuthority ga = new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return "ROLE_" + role;
                }
            };
            roles.add(ga);
            return new UsernamePasswordAuthenticationToken(user, null, roles);
        } catch (JWTVerificationException e) {
            return null; //token is not valid
        }
    }

}
