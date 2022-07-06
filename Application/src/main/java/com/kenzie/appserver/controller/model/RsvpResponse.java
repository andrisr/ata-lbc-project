package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RsvpResponse {

    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("isAttending")
    private Boolean isAttending;
    @JsonProperty("mealChoice")
    private String mealChoice;
    @JsonProperty("plus1Name")
    private String plus1Name;
    @JsonProperty("plus1MealChoice")
    private String plus1MealChoice;

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

    public Boolean getIsAttending() {
        return isAttending;
    }

    public void setAttending(Boolean attending) {
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

    @Override
    public String toString() {
        return "RsvpResponse{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isAttending=" + isAttending +
                ", mealChoice='" + mealChoice + '\'' +
                ", plus1Name='" + plus1Name + '\'' +
                ", plus1MealChoice='" + plus1MealChoice + '\'' +
                '}';
    }
}
