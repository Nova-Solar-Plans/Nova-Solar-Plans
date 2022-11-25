package com.user.manager.modal;

public class ManagerModal {

    public String name;
    public String email;
    public String address;
    public String nic;
    public String password;

    public ManagerModal(){}

    public ManagerModal(String name,String email,String address,String nic,String password){

        this.name = name;
        this.email = email;
        this.address = address;
        this.password = password;
        this.nic = nic;


    }

    public String getNic() {
        return nic;
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
