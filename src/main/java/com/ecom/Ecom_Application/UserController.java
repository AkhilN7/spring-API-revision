package com.ecom.Ecom_Application;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUSers(){
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/api/users")
    public ResponseEntity<String> createUser(@RequestBody User user){
        userService.addUser(user);
        return new ResponseEntity<>("User added successfully",HttpStatus.OK);
   }

   @GetMapping("/api/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id){
        User u1 = userService.findUser(id).get();
        if(u1==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(u1,HttpStatus.OK);
   }

   @PutMapping("/api/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id,@RequestBody User updatedUser){
        boolean flag = userService.updateUser(id,updatedUser);
        if(flag) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }
}
