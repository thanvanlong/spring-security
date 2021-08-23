package com.example.login.registration;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller

public class RegistrationController {
    @Autowired
    RegistrationRequestService registrationRequestService;

    @PostMapping("/register")
    public String register(HttpServletRequest httpServletRequest)  {
        String firstName = httpServletRequest.getParameter("firstName");
        String lastName = httpServletRequest.getParameter("lastName");
        String password = httpServletRequest.getParameter("password");
        String email = httpServletRequest.getParameter("email");
        RegistrationRequest request = new RegistrationRequest(firstName,lastName,password,email);
        System.out.println(request.getFirstName());
        System.out.println(registrationRequestService.register(request));
        return "login";
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token){
        System.out.println(token);
        System.out.println(registrationRequestService.confirmToken(token));

        return "login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @GetMapping("/signup")
    public String signup(){
        return "register";
    }

    @GetMapping("/home")
    public String home(){
        return "index";
    }
}
