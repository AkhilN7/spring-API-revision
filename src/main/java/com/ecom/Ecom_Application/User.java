package com.ecom.Ecom_Application;

import lombok.Data;
import org.springframework.context.annotation.Primary;

@Data
public class User {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private Long id;
    private String firstName;
    private String lastName;
}
