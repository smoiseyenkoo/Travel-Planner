package model;

// represents a set of destination statuses being planned, visited, or wishlist
public enum DestinationStatus {
    PLANNED(1), VISITED(2), WISHLIST(3);

    DestinationStatus(int value) {
        id = value;
    }

    final int id;

// EFFECTS: Assigns the input value to a Destination status

    public static DestinationStatus valueOf(int id) {
        switch (id) {
            case 1: return PLANNED;
            case 2: return VISITED;
            case 3: return WISHLIST;
            default: return PLANNED;
        }
    }
}
