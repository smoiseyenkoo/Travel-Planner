package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TripTest {
    private List<Destination> destinationsPlanned;
    private List<Destination> destinationsVisited;
    private List<Destination> destinationsWishList;
    private Trip testTrip;
    private Destination testDestination1;
    private Destination testDestination2;
    private Destination testDestination3;
    private List<String> activities;

    @BeforeEach
    void runBefore() {
        destinationsPlanned = new ArrayList<>();
        destinationsVisited = new ArrayList<>();
        destinationsWishList = new ArrayList<>();
        activities = new ArrayList<>();

        testTrip = new Trip(destinationsPlanned, destinationsVisited, destinationsWishList);
        testDestination1 = new Destination("Sofia", "Bulgaria", 700, activities);
        testDestination2 = new Destination("Toronto", "Canada", 300, activities);
        testDestination3 = new Destination("North Pole", "Arctic", 10000, activities);

    }

    @Test
    void testConstructor() {
        assertEquals(destinationsPlanned, testTrip.getDestinationsPlanned());
        assertEquals(destinationsVisited, testTrip.getDestinationsVisited());
        assertEquals(destinationsWishList, testTrip.getDestinationsWishList());

    }

    // adds one planned destination to the trip
    @Test
    void testAddPlannedDestination() {
        testTrip.addPlannedDestination(testDestination1);
        assertTrue(testTrip.getDestinationsPlanned().contains(testDestination1));
        assertFalse(testTrip.getDestinationsPlanned().contains(testDestination2));
        assertFalse(testTrip.getDestinationsVisited().contains(testDestination2));

    }

    // adds one visited destination to the trip
    @Test
    void testAddVisitedDestination() {
        testTrip.addPlannedDestination(testDestination1);
        testTrip.addVisitedDestination(testDestination3);
        assertTrue(testTrip.getDestinationsPlanned().contains(testDestination1));
        assertFalse(testTrip.getDestinationsPlanned().contains(testDestination3));
        assertTrue(testTrip.getDestinationsVisited().contains(testDestination3));
    }

    // adds one wishlist destination to the trip
    @Test
    void testAddWishlistDestination() {
        testTrip.addPlannedDestination(testDestination1);
        testTrip.addVisitedDestination(testDestination3);
        testTrip.addWishlist(testDestination2);
        assertTrue(testTrip.getDestinationsPlanned().contains(testDestination1));
        assertFalse(testTrip.getDestinationsPlanned().contains(testDestination3));
        assertTrue(testTrip.getDestinationsVisited().contains(testDestination3));
        assertTrue(testTrip.getDestinationsWishList().contains(testDestination2));
        assertFalse(testTrip.getDestinationsWishList().contains(testDestination3));
    }

    // removes one wishlist destination from the trip
    @Test
    void testRemoveWishlistDestination() {
        testTrip.addPlannedDestination(testDestination1);
        testTrip.addVisitedDestination(testDestination3);
        testTrip.addWishlist(testDestination2);
        testTrip.removeWishlist(testDestination2);
        assertTrue(testTrip.getDestinationsPlanned().contains(testDestination1));
        assertFalse(testTrip.getDestinationsPlanned().contains(testDestination3));
        assertTrue(testTrip.getDestinationsVisited().contains(testDestination3));
        assertFalse(testTrip.getDestinationsWishList().contains(testDestination2));
        assertFalse(testTrip.getDestinationsWishList().contains(testDestination3));
    }

    // removes one wishlist destination from the trip
    @Test
    void testRemovePlannedDestination() {
        testTrip.addPlannedDestination(testDestination1);
        testTrip.addVisitedDestination(testDestination3);
        testTrip.addWishlist(testDestination2);
        testTrip.removePlannedDestination(testDestination1);
        testTrip.removeWishlist(testDestination2);
        assertFalse(testTrip.getDestinationsPlanned().contains(testDestination1));
        assertFalse(testTrip.getDestinationsPlanned().contains(testDestination3));
        assertTrue(testTrip.getDestinationsVisited().contains(testDestination3));
        assertFalse(testTrip.getDestinationsWishList().contains(testDestination2));
        assertFalse(testTrip.getDestinationsWishList().contains(testDestination3));
    }

}
