package com.ecom.Ecom_Application.controller;

import com.ecom.Ecom_Application.dto.UserRequest;
import com.ecom.Ecom_Application.dto.UserResponse;
import com.ecom.Ecom_Application.model.User;
import com.ecom.Ecom_Application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponse>> getAllUSers(){
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/api/users")
    public ResponseEntity<String> createUser(@RequestBody UserRequest user){
        userService.addUser(user);
        return new ResponseEntity<>("User added successfully",HttpStatus.OK);
   }

   @GetMapping("/api/user/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable long id){
        Optional<UserResponse> u1 = userService.findUser(id);
        if(u1.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(u1.get(),HttpStatus.OK);
   }

   @PutMapping("/api/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id,@RequestBody UserResponse updatedUser){
        boolean flag = userService.updateUser(id,updatedUser);
        if(flag) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }
}
