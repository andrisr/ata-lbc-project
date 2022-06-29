package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Objects;

@DynamoDBTable(tableName = "Rsvp")
public class RsvpRecord {
    private String name;
    private String email;
    private boolean attending;
    private String mealChoice;
    private String plus1Name;
    private String plus1MealChoice;

    @DynamoDBHashKey(attributeName = "Id")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @DynamoDBAttribute(attributeName = "Attending")
    public boolean isAttending() {
        return attending;
    }

    public void setAttending(boolean attending) {
        this.attending = attending;
    }

    @DynamoDBAttribute(attributeName = "Meal Choice")
    public String getMealChoice() {
        return mealChoice;
    }

    public void setMealChoice(String mealChoice) {
        this.mealChoice = mealChoice;
    }

    @DynamoDBAttribute(attributeName = "Plus 1 Name")
    public String getPlus1Name() {
        return plus1Name;
    }

    public void setPlus1Name(String plus1Name) {
        this.plus1Name = plus1Name;
    }

    @DynamoDBAttribute(attributeName = "Plus 1 Meal Choice")
    public String getPlus1MealChoice() {
        return plus1MealChoice;
    }

    public void setPlus1MealChoice(String plus1MealChoice) {
        this.plus1MealChoice = plus1MealChoice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RsvpRecord that = (RsvpRecord) o;
        return name.equalsIgnoreCase(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
