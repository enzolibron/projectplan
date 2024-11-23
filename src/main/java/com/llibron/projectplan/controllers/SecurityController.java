package com.llibron.projectplan.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/")
@RestController
public class SecurityController {

    @GetMapping
    public String index(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        for(Cookie cookie : cookies) {
            System.out.println(cookie.getName() + " " + cookie.getValue());
        }

        Cookie testCookie = new Cookie("test", "test");
        testCookie.setPath("/");
        testCookie.setHttpOnly(true);
        testCookie.setSecure(true);

        response.addCookie(testCookie);

        return "Hello " + request.getSession().getId();
    }
}
