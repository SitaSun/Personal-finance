package com.example.test.bean;

public class Budget {
    private int id;
    private double amount;
    private int month;
    private int year;

    public Budget(int id, double amount, int month, int year) {
        this.id = id;
        this.amount = amount;
        this.month = month;
        this.year = year;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
