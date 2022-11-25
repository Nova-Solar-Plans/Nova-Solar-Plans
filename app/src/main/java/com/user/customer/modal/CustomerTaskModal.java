package com.user.customer.modal;

public class CustomerTaskModal {

    String key;
    String type;
    String description;
    String customerName;
    String customerAddress;
    String customerEmail;
    String customerContact;
    String customerOrderNo;
    String date;
    String status;
    String rating;
    String ratingDescription;
    String technicianFeedback;

    public CustomerTaskModal(){}

    public CustomerTaskModal(String type, String description, String customerName, String customerAddress,String customerEmail,String customerContact, String customerOrderNo
        , String date, String status, String rating, String ratingDescription, String technicianFeedback){

        this.type = type;
        this.description = description;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerOrderNo = customerOrderNo;
        this.date = date;
        this.status = status;
        this.rating = rating;
        this.ratingDescription = ratingDescription;
        this.technicianFeedback = technicianFeedback;
        this.customerEmail = customerEmail;
        this.customerContact = customerContact;
    }

    public String getRating() {
        return rating;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerOrderNo() {
        return customerOrderNo;
    }

    public String getRatingDescription() {
        return ratingDescription;
    }

    public String getTechnicianFeedback() {
        return technicianFeedback;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerContact() {
        return customerContact;
    }
}
