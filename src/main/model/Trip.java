package model;

import java.util.ArrayList;
import java.util.List;

public class Trip {
    private final String tripName;
    private final List<Destination> destinations;
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

    // MODIFIES: this
    // EFFECTS: adds destination to planned destinations and adds
    // the planned travel costs to the total planned expenses
    public void addDestination(Destination destination) {
        this.destinations.add(destination);
        if (destination.getDestinationStatus() == DestinationStatus.PLANNED) {
            this.plannedTripExpense += destination.getTravelCost();
        }
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


//     REQUIRES: visited destination should have already been in planned
//     destinations
//     MODIFIES: this, Destination
//     EFFECTS: adds the destination to the visited list and removes it from the
//     planned list, and adds the travel cost to the total trip cost
    public void addVisitedDestination(Destination destination) {
        destination.setDestinationStatus(DestinationStatus.VISITED);
        this.tripExpense += destination.getTravelCost();
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
}
