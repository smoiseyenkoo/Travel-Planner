package model;

import java.util.List;

public class Destination {
    private String city;
    private String country;
    private int travelCost;
    private List<String> activities;

    // REQUIRES: travelCosts >= 0
    // EFFECTS: creates a destination with a city, country, amount of travel costs in dollars, and
    // a list of activities to do at that destination
    public Destination(String city, String country, int travelCost, List<String> activities) {
        this.city = city;
        this.country = country;
        this.travelCost = travelCost;
        this.activities = activities;

    }

    public void addActivity(String activity) {
        this.activities.add(activity);
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public int getTravelCost() {
        return this.travelCost;
    }

    public List<String> getActivities() {
        return this.activities;
    }
}
