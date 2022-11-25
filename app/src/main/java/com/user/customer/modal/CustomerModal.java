package com.user.customer.modal;

public class CustomerModal {

    public String name;
    public String email;
    public String address;
    public String password;

    public CustomerModal(){}

    public CustomerModal(String name,String email,String address,String password){

        this.name = name;
        this.email = email;
        this.address = address;
        this.password = password;



    }


    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
