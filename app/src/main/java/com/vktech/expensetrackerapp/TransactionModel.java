package com.vktech.expensetrackerapp;

public class TransactionModel {
    private int id;
    private String date;
    private double amount;
    private String type;
    private String description;

    public TransactionModel(int id, String date, double amount, String type, String description) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}