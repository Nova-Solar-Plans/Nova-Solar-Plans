package com.user.manager.modal;

public class ProductModal {

    String title;
    String description;
    String price;
    String highlight;

    public ProductModal(){}

    public ProductModal(String title,String description,String price, String highlight){

        this.title = title;
        this.description = description;
        this.price = price;
        this.highlight = highlight;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getHighlight() {
        return highlight;
    }

    public String getTitle() {
        return title;
    }
}
