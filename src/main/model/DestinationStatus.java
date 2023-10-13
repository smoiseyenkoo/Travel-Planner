package model;

public enum DestinationStatus {
    PLANNED(1), VISITED(2), WISHLIST(3);

    DestinationStatus(int value) {
        id = value;
    }

    final int id;

    public static DestinationStatus valueOf(int id) {
        switch (id) {
            case 1: return PLANNED;
            case 2: return VISITED;
            case 3: return WISHLIST;
            default: return PLANNED;
        }
    }
}
