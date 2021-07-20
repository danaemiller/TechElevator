package com.techelevator;

public class Product {
    private String name;
    private String type;

    public Product(String name, String type){
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String toString(){

        return name + " " + type;
    }
}


