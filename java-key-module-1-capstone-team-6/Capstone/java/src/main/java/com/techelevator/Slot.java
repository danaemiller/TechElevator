package com.techelevator;

public class Slot {
    private Double price;
    private String slotNumber;
    private Product product;
    private int inventory;
    private int numberSold;


    public Slot(String slotNumber, Product product, Double price){
        this.slotNumber = slotNumber;
        this.product = product;
        this.price = price;
        inventory = 5;
        numberSold = 0;
    }

    public Double getPrice() {
        return price;
    }

    public String getProductName() {
        return product.getName();
    }

    public String getProductType() {
        return product.getType();
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getNumberSold() {
        return numberSold;
    }

    public void setNumberSold(int numberSold) {
        this.numberSold = numberSold;
    }

    public String toString(){
        if (inventory == 0) {
            return slotNumber + " " + product.getName() + " $" + price + " SOLD OUT";
        } else {
             return slotNumber + " " + product.getName() + " $" + price + " " + inventory + " in stock";

        }

    }
}
