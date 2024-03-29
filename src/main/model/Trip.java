package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a trip having a name and a list of destinations
public class Trip implements Writable {
    private final String tripName;
    private List<Destination> destinations;
    private int tripExpense;
    private int plannedTripExpense;

    // EFFECTS: creates a trip with a list of destinations planned, list of destinations already
    // visited, list of destinations on a wishlist, and total trip expenses so far in dollars,
    // starting at 0.
    public Trip(String tripName, List<Destination> destinations) {
        this.tripName = tripName;
        this.destinations = destinations;
        this.tripExpense = 0;
        this.plannedTripExpense = 0;
    }

    // MODIFIES: this, theLog
    // EFFECTS: adds destination to planned destinations and adds
    // the planned travel costs to the total planned expenses, and logs the event to EventLog.getInstance()
    public void addDestination(Destination destination) {
        this.destinations.add(destination);
        if (destination.getDestinationStatus() == DestinationStatus.PLANNED) {
            this.plannedTripExpense += destination.getTravelCost();
        }
        EventLog.getInstance().logEvent(new Event("Adding new destination: " + destination.getCity() + ", "
                + destination.getCountry()));

    }

    // REQUIRES: Planned destinations list must be at least one element long
    // MODIFIES: this
    // EFFECTS: removes destination from planned destinations and subtracts
    // the planned travel costs from the total planned expenses
    public void removeDestination(Destination destination) {
        if (destination.getDestinationStatus() == DestinationStatus.PLANNED) {
            this.plannedTripExpense -= destination.getTravelCost();
        }
        this.destinations.remove(destination);
    }


    // EFFECTS: returns the total trip expense of the Visited destinations
    // in dollars
    public int getTotalTripExpense() {
        int visitedExpenses = 0;
        for (Destination destination : this.destinations) {
            if (destination.getDestinationStatus() == DestinationStatus.VISITED) {
                visitedExpenses += destination.getTravelCost();
            }
        }
        tripExpense = visitedExpenses;
        return visitedExpenses;
    }

    // EFFECTS: returns the list of destinations
    public List<Destination> getDestinations() {
        return this.destinations;
    }

    // EFFECTS: returns the name of the trip
    public String getTripName() {
        return this.tripName;
    }

    // EFFECTS: returns the total number of destinations in the list
    public int getNumDestinations() {
        return this.destinations.size();
    }

    // EFFECTS: returns the planned trip expense of the Planned destinations
    // in dollars
    public int getPlannedExpenses() {
        int plannedExpenses = 0;
        for (Destination destination : this.destinations) {
            if (destination.getDestinationStatus() == DestinationStatus.PLANNED) {
                plannedExpenses += destination.getTravelCost();
            }
        }
        plannedTripExpense = plannedExpenses;
        return plannedExpenses;
    }

    // EFFECTS: returns a list of the destinations of the specified
    // status (PLANNED, VISITED, WISHLIST)
    public List<Destination> getStatusDestinations(DestinationStatus status) {
        List<Destination> statusDestination = new ArrayList<>();
        for (Destination destination : destinations) {
            if (destination.getDestinationStatus() == status) {
                statusDestination.add(destination);
            }
        }
        return statusDestination;

    }

// JSON Code inspired by JsonSerializationDemo
// EFFECTS: returns a trip as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tripName", tripName);
        json.put("destinations", destinationsToJSon());
        return json;
    }

    // EFFECTS: returns Destinations in this Trip as a JSON array
    private JSONArray destinationsToJSon() {
        JSONArray jsonArray = new JSONArray();
        for (Destination d : destinations) {
            jsonArray.put(d.toJson());
        }
        return jsonArray;
    }
}