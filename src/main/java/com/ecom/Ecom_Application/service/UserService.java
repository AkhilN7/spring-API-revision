package com.ecom.Ecom_Application.service;

import com.ecom.Ecom_Application.dto.AddressDTO;
import com.ecom.Ecom_Application.dto.UserRequest;
import com.ecom.Ecom_Application.dto.UserResponse;
import com.ecom.Ecom_Application.model.Address;
import com.ecom.Ecom_Application.model.User;
import com.ecom.Ecom_Application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    //public long nextId = 0;
    //public List<User> userList = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    public List<UserResponse> fetchAllUsers(){
        List<User> users  = userRepository.findAll();
        List<UserResponse> ur = new ArrayList<>();
        for(User u:users){
            ur.add(mapToUserResponse(u));
        }
        return ur;
    }

    public void addUser(UserRequest userRequest){
        //user.setId(nextId++);
        User user = new User();
        updateUserFromRequest(user,userRequest);
        userRepository.save(user);
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if(userRequest.getAddress()!=null){
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setState((userRequest.getAddress().getState()));
            address.setZipcode((userRequest.getAddress().getZipcode()));
            address.setCity((userRequest.getAddress().getCity()));
            address.setCountry((userRequest.getAddress().getCountry()));
            user.setAddress(address);
        }
    }

//    public User findUser(long id){
//        for(int i=0;i<userList.size();i++){
//            if(userList.get(i).getId()==id){
//                return userList.get(i);
//            }
//        }
//        return null;
//    }

    public Optional<UserResponse> findUser(Long id){
        //return userRepository.findById(id).map(this::mapToUserResponse);
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(mapToUserResponse(optionalUser.get()));

    }

    public boolean updateUser(Long id,UserResponse updatedUser){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            userRepository.save(user); // ensure persistence
            return true;
        }

        return false;

    }
    private UserResponse mapToUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if(user.getAddress()!=null){
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDTO);
        }

        return response;
    }

}
