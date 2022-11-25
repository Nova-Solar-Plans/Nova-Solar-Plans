package com.user.customer.modal;

public class CustomerTechnicianModal {

    public String name;
    public String email;
    public String address;
    public String nic;
    public String password;
    public String rating;

    public CustomerTechnicianModal(){}

    public CustomerTechnicianModal(String name, String email, String address, String nic, String password, String rating){

        this.name = name;
        this.email = email;
        this.address = address;
        this.password = password;
        this.rating = rating;
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

    public String getRating() {
        return rating;
    }
}
