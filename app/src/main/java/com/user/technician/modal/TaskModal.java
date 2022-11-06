package com.user.technician.modal;

import com.google.android.gms.tasks.Task;

public class TaskModal {

    String key;
    String type;
    String description;
    String customerName;
    String customerAddress;
    int customerOrderNo;
    String date;
    String status;
    int rating;
    String ratingDescription;
    String technicianFeedback;

    public TaskModal(){}

    public TaskModal(String type,String description,String customerName, String customerAddress, int customerOrderNo
        ,String date,String status,int rating,String ratingDescription,String technicianFeedback){

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
    }


    public int getRating() {
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

    public int getCustomerOrderNo() {
        return customerOrderNo;
    }

    public String getRatingDescription() {
        return ratingDescription;
    }

    public String getTechnicianFeedback() {
        return technicianFeedback;
    }
}
