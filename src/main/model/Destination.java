package model;

import java.util.ArrayList;
import java.util.List;

public class Destination {
    private String city;
    private String country;
    private int travelCost;
    private List<Activity> activities;
    private DestinationStatus destinationStatus;
    private int id;



    // REQUIRES: travelCosts >= 0
    // EFFECTS: creates a destination with a city, country, amount of travel costs in dollars, and
    // a list of activities to do at that destination
    public Destination(String city, String country, int travelCost, List<Activity> activities,
                       DestinationStatus destinationStatus) {
        this.city = city;
        this.country = country;
        this.travelCost = travelCost;
        this.activities = activities == null ? new ArrayList<>() : activities;
        this.destinationStatus = destinationStatus;
        ++this.id;
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
        this.travelCost += activity.getActivityCost();
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

    public List<Activity> getActivities() {
        return this.activities;
    }

    public DestinationStatus getDestinationStatus() {
        return destinationStatus;
    }

    public void setDestinationStatus(DestinationStatus destinationStatus) {
        this.destinationStatus = destinationStatus;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "ID= " + id +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", travelCost=" + travelCost +
                ", amount of activities=" + activities.size() +
                ", destinationStatus=" + destinationStatus +
                '}';
    }

}
