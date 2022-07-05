package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Objects;

@DynamoDBTable(tableName = "Rsvp")
public class RsvpRecord {
    private String name;
    private String email;
    private Boolean isAttending;
    private String mealChoice;
    private String plus1Name;
    private String plus1MealChoice;

    @DynamoDBHashKey(attributeName = "name")
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


    @DynamoDBAttribute(attributeName = "attending")
    public Boolean isAttending() {
        return isAttending;
    }

    public void setAttending(Boolean attending) {
        this.isAttending = attending;
    }

    @DynamoDBAttribute(attributeName = "meal choice")
    public String getMealChoice() {
        return mealChoice;
    }

    public void setMealChoice(String mealChoice) {
        this.mealChoice = mealChoice;
    }

    @DynamoDBAttribute(attributeName = "plus 1 name")
    public String getPlus1Name() {
        return plus1Name;
    }

    public void setPlus1Name(String plus1Name) {
        this.plus1Name = plus1Name;
    }

    @DynamoDBAttribute(attributeName = "plus 1 meal choice")
    public String getPlus1MealChoice() {
        return plus1MealChoice;
    }

    public void setPlus1MealChoice(String plus1MealChoice) {
        this.plus1MealChoice = plus1MealChoice;
    }

    @Override
    public String toString() {
        return "RsvpRecord{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isAttending=" + isAttending +
                ", mealChoice='" + mealChoice + '\'' +
                ", plus1Name='" + plus1Name + '\'' +
                ", plus1MealChoice='" + plus1MealChoice + '\'' +
                '}';
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
