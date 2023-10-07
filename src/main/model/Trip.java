package model;

import java.util.List;

public class Trip {
    private List<Destination> destinationsPlanned;
    private List<Destination> destinationsVisited;
    private List<Destination> destinationsWishList;
    private int tripCost;

    // EFFECTS: creates a trip with a list of destinations planned, list of destinations already
    // visited, list of destinations on a wishlist, and total trip costs so far in dollars,
    // starting at 0.
    public Trip(List<Destination> destinationsPlanned, List<Destination> destinationsAlreadyVisited,
                 List<Destination> destinationsWishList) {
        this.destinationsPlanned = destinationsPlanned;
        this.destinationsVisited = destinationsAlreadyVisited;
        this.destinationsWishList = destinationsWishList;
        this.tripCost = 0;
    }

    public void addPlannedDestination(Destination destination) {
        this.destinationsPlanned.add(destination);
    }

    public void removePlannedDestination(Destination destination) {
        this.destinationsPlanned.remove(destination);
    }

    public void addVisitedDestination(Destination destination) {
        this.destinationsVisited.add(destination);
    }

    public void removeVisitedDestination(Destination destination) {
        this.destinationsVisited.remove(destination);
    }
    public void addWishlist(Destination destination) {
        this.destinationsWishList.add(destination);
    }

    public void removeWishlist(Destination destination) {
        this.destinationsWishList.remove(destination);
    }

    public void addTripCost(int money) {
        this.tripCost += money;
    }

    public List<Destination> getDestinationsPlanned() {
        return this.destinationsPlanned;
    }

    public List<Destination> getDestinationsVisited() {
        return this.destinationsVisited;
    }

    public List<Destination> getDestinationsWishList() {
        return this.destinationsWishList;
    }



}
