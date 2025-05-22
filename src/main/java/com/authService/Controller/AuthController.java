package com.authService.Controller;

import com.authService.Payload.APIResponse;
import com.authService.Payload.UserDto;
import com.authService.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<APIResponse<String>> register(@RequestBody UserDto userDto){
        APIResponse<String> register = authService.register(userDto);
        return new ResponseEntity<>(register, HttpStatusCode.valueOf(register.getStatusCode()));
    }
}
