package com.kenzie.appserver.service.model;

public class Rsvp {
    private String name;
    private String email;
    private boolean isAttending;
    private String mealChoice;
    private String plus1Name;
    private String plus1MealChoice;

    public Rsvp(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAttending() {
        return isAttending;
    }


    public void setAttending(boolean isAttending) {

        this.isAttending = isAttending;
    }

    public String getMealChoice() {
        return mealChoice;
    }

    public void setMealChoice(String mealChoice) {
        this.mealChoice = mealChoice;
    }

    public String getPlus1Name() {
        return plus1Name;
    }

    public void setPlus1Name(String plus1Name) {
        this.plus1Name = plus1Name;
    }

    public String getPlus1MealChoice() {
        return plus1MealChoice;
    }

    public void setPlus1MealChoice(String plus1MealChoice) {
        this.plus1MealChoice = plus1MealChoice;
    }
}
