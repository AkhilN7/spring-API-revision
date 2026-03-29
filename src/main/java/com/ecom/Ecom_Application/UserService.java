package com.ecom.Ecom_Application;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    public long nextId = 0;
    public List<User> userList = new ArrayList<>();

    public List<User> fetchAllUsers(){
        return userList;
    }

    public List<User> addUser(User user){
        user.setId(nextId++);
        userList.add(user);
        return userList;
    }

//    public User findUser(long id){
//        for(int i=0;i<userList.size();i++){
//            if(userList.get(i).getId()==id){
//                return userList.get(i);
//            }
//        }
//        return null;
//    }

    public Optional<User> findUser(Long id){
        return userList.stream().filter(user-> user.getId().equals(id)).findFirst();
    }

    public boolean updateUser(Long id,User updatedUser){
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    existingUser.setId(id);
                    return true;
                })
                .orElse(false);
    }

}
