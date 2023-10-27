package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// represents a destination having a name of a city, it's corresponding country, the travel cost in dollars,
// a list of activities at that destination, it's visitation status, and an ID number for selection.
public class Destination implements Writable {
    private final String city;
    private final String country;
    private int travelCost;
    private List<Activity> activities;
    private DestinationStatus destinationStatus;
    private final int id;
    private static int nextId = 0;


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
        this.id = ++nextId;
    }

    // EFFECTS: Adds activity to the list and adds activity cost to travel cost
    // MODIFIES: this
    public void addActivity(Activity activity) {
        this.activities.add(activity);
        this.travelCost += activity.getActivityCost();
    }

    // EFFECTS: returns name of city
    public String getCity() {
        return this.city;
    }

    // EFFECTS: returns name of country
    public String getCountry() {
        return this.country;
    }

    // EFFECTS: returns cost of travel in dollars
    public int getTravelCost() {
        return this.travelCost;
    }

    // EFFECTS: returns list of activities
    public List<Activity> getActivities() {
        return this.activities;
    }

    // EFFECTS: returns destination status (PLANNED, VISITED, WISHLIST)
    public DestinationStatus getDestinationStatus() {
        return destinationStatus;
    }

    // EFFECTS: sets the destination status to the input
    // MODIFIES: this
    public void setDestinationStatus(DestinationStatus destinationStatus) {
        this.destinationStatus = destinationStatus;
    }

    // EFFECTS: returns ID of the destination
    public int getId() {
        return id;
    }

    // EFFECTS: returns the destination as a string
    @Override
    public String toString() {
        return "Destination{"
                +
                "ID= " + id
                +
                "city='" + city + '\''
                +
                ", country='" + country + '\''
                +
                ", travelCost=" + travelCost
                +
                ", amount of activities=" + activities.size()
                +
                ", destinationStatus=" + destinationStatus
                +
                '}';
    }

    // JSON Code inspired by JsonSerializationDemo
    // EFFECTS: returns a destination as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("city", city);
        json.put("country", country);
        json.put("travelCost", travelCost);
        json.put("activities", activities);
        json.put("destinationStatus", destinationStatus);
        return json;
    }



}