package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class RsvpCreateRequest {

    @NotEmpty
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("isAttending")
    private boolean isAttending;
    @JsonProperty("mealChoice")
    private String mealChoice;
    @JsonProperty("plus1Name")
    private String plus1Name;
    @JsonProperty("plus1MealChoice")
    private String plus1MealChoice;

    public RsvpCreateRequest (String name, String email, boolean isAttending,
                              String mealChoice, String plus1Name, String plus1MealChoice) {
        this.name = name;
        this.email = email;
        this.isAttending = isAttending;
        this.mealChoice = mealChoice;
        this.plus1Name = plus1Name;
        this.plus1MealChoice = plus1MealChoice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAttending() {
        return isAttending;
    }

    public void setAttending(boolean attending) {
        isAttending = attending;
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
