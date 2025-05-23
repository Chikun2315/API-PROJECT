package com.authService.Controller;

import com.authService.Payload.APIResponse;
import com.authService.Payload.LoginDto;
import com.authService.Payload.UserDto;
import com.authService.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<APIResponse<String>> register(@RequestBody UserDto userDto){
        APIResponse<String> register = authService.register(userDto);
        return new ResponseEntity<>(register, HttpStatusCode.valueOf(register.getStatusCode()));
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse<String>> login(@RequestBody LoginDto loginDto){
        APIResponse<String> response = new APIResponse<>();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
try{
    Authentication authenticate = authenticationManager.authenticate(token);
    if(authenticate.isAuthenticated()){
        response.setMessage("User verified");
        response.setStatusCode(201);
        response.setData("Registration Sucessful");
    }
}catch(Exception e){
    e.printStackTrace();
}
        response.setMessage("Registration failed");
        response.setStatusCode(500);
        response.setData("Wrong username password");

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }



}
