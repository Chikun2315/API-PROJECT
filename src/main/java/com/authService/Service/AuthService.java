package com.authService.Service;

import com.authService.Entity.User;
import com.authService.Payload.APIResponse;
import com.authService.Payload.UserDto;
import com.authService.Repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public APIResponse<String> register(UserDto userDto){

        APIResponse<String> response = new APIResponse<>();

        //check if user name exist or not
        if(userRepository.existsByUsername(userDto.getUsername())){
            response.setMessage("Registration Failed");
            response.setStatusCode(500);
            response.setData("User name alredy exists");
            return response;
        }
        //check if email exists
        if(userRepository.existsByEmail(userDto.getEmail())){
            response.setMessage("Registration Failed");
            response.setStatusCode(500);
            response.setData("Email alredy exists");
            return response;
        }

        //Save the password with encryption
      String encrypted = passwordEncoder.encode(userDto.getPassword());
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        userRepository.save(user);

        //return response with APIResponse
        response.setMessage("Registration Sucessful");
        response.setStatusCode(201);
        response.setData("User Created");
        return response;

    }


}
