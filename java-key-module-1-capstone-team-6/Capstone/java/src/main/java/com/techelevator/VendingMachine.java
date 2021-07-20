package com.techelevator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class VendingMachine {

    private Double balance;
    private Map<String, Slot> slots = new TreeMap();
    private Double totalSales;
    private DecimalFormat centFormat = new DecimalFormat("###.##");

    public VendingMachine() {
        balance = 0.0;
        totalSales = 0.0;
    }



    public Double getBalance() {
        return balance;
    }

    public void displayItems() {
        Set<String> allSlots = slots.keySet();
        for (String slotNumber : allSlots) {
            System.out.println(slots.get(slotNumber).toString());
        }
    }

    public void loadSlots(String slotNumber, Slot slot) {
        slots.put(slotNumber, slot);
    }

    public Double getTotalSales() {
        return totalSales;
    }

    public void feedMoney(Double tender) {
        if (tender == 1.0 || tender == 2.0 || tender == 5.0 || tender == 10.0 || tender == 20.0 ||
        tender == 50.0 || tender == 100.0) {
            balance += tender;
        }  else {
            System.out.println("This machine only accepts bills, one at a time. Please enter in a valid tender.");
        }
    }

    public void giveChange() {
        double coins = 0;
        int quarters = 0;
        int dimes = 0;
        int nickels = 0;

        if (balance < .04) {
            System.out.println("The balance is currently zero. No change due.");
        } else {
            if (balance > .24) {
                coins = balance / .25;
                quarters = (int) coins;
                balance -= (quarters * .25);
            }
            if (balance > .09) {
                coins = balance / .1;
                dimes = (int) coins;
                balance -= (dimes * .1);
            }
            if (balance >= 0.04) {
                coins = balance / .04;
                nickels = (int) coins;
                balance = 0.00;
            }
            System.out.println("Your change is " + quarters + " quarters, "
                    + dimes + " dimes, " + nickels + " nickels");
        }
    }

    public void makePurchase(String slotNumber) {
        if (slots.containsKey(slotNumber)) {
            if (balance >= slots.get(slotNumber).getPrice() && slots.get(slotNumber).getInventory() >= 1) {
                balance -= slots.get(slotNumber).getPrice();
                totalSales += slots.get(slotNumber).getPrice();
                slots.get(slotNumber).setInventory(slots.get(slotNumber).getInventory() - 1);
                slots.get(slotNumber).setNumberSold(slots.get(slotNumber).getNumberSold() + 1);
                System.out.println(slots.get(slotNumber).getProductName() + " $" + slots.get(slotNumber).getPrice() +
                        ", you have $" + centFormat.format(balance) + " remaining");
                if ((slots.get(slotNumber).getProductType()).equalsIgnoreCase("Chip")) {
                    System.out.println("Crunch Crunch, Yum!");
                } else if ((slots.get(slotNumber).getProductType()).equalsIgnoreCase("Candy")) {
                    System.out.println("Munch Munch, Yum!");
                } else if ((slots.get(slotNumber)).getProductType().equalsIgnoreCase("Drink")) {
                    System.out.println("Glug Glug, Yum!");
                } else {
                    System.out.println("Chew Chew, Yum!");
                }

            } else if (balance < slots.get(slotNumber).getPrice()) {
                System.out.println("Your balance is currently $" + centFormat.format(balance) + " and the price of that item is $" +
                        slots.get(slotNumber).getPrice() + ", please add $" +
                        centFormat.format(slots.get(slotNumber).getPrice() - balance)
                        + " to make this purchase.");
            } else if (slots.get(slotNumber).getInventory() == 0) {
                System.out.println("This item is sold out.");

            }
        } else {
            System.out.println("That is not a valid product number.");}
    }

    public String getProductName(String slotNumber) {
        return slots.get(slotNumber).getProductName();
    }

    public Double getProductPrice(String slotNumber) {
        return slots.get(slotNumber).getPrice();
    }

    public Boolean isSlotNumber(String slotNumber) {
        return slots.containsKey(slotNumber);
    }

    public void getSalesReport() throws IOException {

        Timestamp dailySalesReport = Timestamp.valueOf(LocalDateTime.now());
        String salesReportAsString = dailySalesReport.toString().substring(0,19).replace(":", "-");
        String fileName = "./" + salesReportAsString + ".txt";
        File salesReport = new File(fileName);
        salesReport.createNewFile();
        PrintWriter salesWriter = new PrintWriter(salesReport);
        Set<String> allSlots = slots.keySet();
        for (String slotNumber : allSlots) {
            salesWriter.println(slots.get(slotNumber).getProductName() + "|"
                    + slots.get(slotNumber).getNumberSold());
        }
        salesWriter.println("\nTotal Sales: $" + centFormat.format(totalSales));
        salesWriter.close();
        System.out.println("Sales Report generated.");
    }
}
