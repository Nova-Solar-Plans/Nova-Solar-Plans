package com.user.visitor.modal;

public class RequestModal {


    String type;
    String description;
    String customerName;
    String customerAddress;
    String customerEmail;
    String customerContact;
    String customerOrderNo;

    public RequestModal() {
    }

    public RequestModal(String type, String description, String customerName, String customerAddress, String customerOrderNo
            ,String customerContact,String customerEmail) {

        this.type = type;
        this.description = description;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerOrderNo = customerOrderNo;
        this.customerEmail = customerEmail;
        this.customerContact = customerContact;
    }

    public String getCustomerOrderNo() {
        return customerOrderNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }
}

