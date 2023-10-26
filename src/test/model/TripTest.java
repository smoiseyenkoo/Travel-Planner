package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Trip class.

public class TripTest {
    private List<Destination> destinations;
    private Trip testTrip;
    private Destination testDestination1;
    private Destination testDestination2;
    private Destination testDestination3;
    private List<Activity> activities;

    @BeforeEach
    void runBefore() {
        destinations = new ArrayList<>();
        activities = new ArrayList<>();
        testDestination1 = new Destination("Sofia", "Bulgaria", 700, activities,
                DestinationStatus.PLANNED);
        testDestination2 = new Destination("Toronto", "Canada", 300, activities,
                DestinationStatus.PLANNED);
        testDestination3 = new Destination("North Pole", "Arctic", 10000, activities,
                DestinationStatus.PLANNED);
        testTrip = new Trip("New Trip", destinations);

    }

    @Test
    void testConstructor() {
        assertEquals("New Trip", testTrip.getTripName());
        assertEquals(destinations, testTrip.getDestinations());
        assertEquals(0, testTrip.getTotalTripExpense());

    }

    // adds one planned destination to the trip
    @Test
    void testAddPlannedDestination() {
        testTrip.addDestination(testDestination1);
        assertEquals(DestinationStatus.PLANNED, testDestination1.getDestinationStatus());
        assertTrue(testTrip.getDestinations().contains(testDestination1));
        assertFalse(testTrip.getDestinations().contains(testDestination2));
        assertEquals(700, testTrip.getPlannedExpenses());
        assertEquals(0, testTrip.getTotalTripExpense());

    }

    // adds one visited destination to the trip
    @Test
    void testAddVisitedDestination() {
        testTrip.addDestination(testDestination1);
        testTrip.addDestination(testDestination3);
        testDestination3.setDestinationStatus(DestinationStatus.VISITED);
        assertTrue(testTrip.getStatusDestinations(DestinationStatus.PLANNED).contains(testDestination1));
        assertFalse(testTrip.getStatusDestinations(DestinationStatus.VISITED).contains(testDestination1));
        assertFalse(DestinationStatus.PLANNED == testDestination3.getDestinationStatus());
        assertTrue(testTrip.getStatusDestinations(DestinationStatus.VISITED).contains(testDestination3));
        assertFalse(testTrip.getStatusDestinations(DestinationStatus.PLANNED).contains(testDestination3));
    }

    // adds one wishlist destination to the trip
    @Test
    void testAddWishlistDestination() {
        testTrip.addDestination(testDestination1);
        testTrip.addDestination(testDestination3);
        testTrip.addDestination(testDestination2);
        testDestination1.setDestinationStatus(DestinationStatus.PLANNED);
        testDestination2.setDestinationStatus(DestinationStatus.VISITED);
        testDestination3.setDestinationStatus(DestinationStatus.WISHLIST);


        assertTrue(testTrip.getStatusDestinations(DestinationStatus.PLANNED).contains(testDestination1));
        assertFalse(testTrip.getStatusDestinations(DestinationStatus.PLANNED).contains(testDestination3));
        assertTrue(testTrip.getStatusDestinations(DestinationStatus.WISHLIST).contains(testDestination3));
        assertTrue(testTrip.getStatusDestinations(DestinationStatus.VISITED).contains(testDestination2));
        assertFalse(testTrip.getStatusDestinations(DestinationStatus.VISITED).contains(testDestination3));
    }

    // removes one wishlist destination from the trip
    @Test
    void testRemoveWishlistDestination() {
        testTrip.addDestination(testDestination1);
        testTrip.addDestination(testDestination2);
        testDestination1.setDestinationStatus(DestinationStatus.PLANNED);
        testDestination2.setDestinationStatus(DestinationStatus.WISHLIST);
        testTrip.removeDestination(testDestination2);
        assertTrue(testTrip.getStatusDestinations(DestinationStatus.PLANNED).contains(testDestination1));
        assertFalse(testTrip.getStatusDestinations(DestinationStatus.PLANNED).contains(testDestination2));
        assertFalse(testTrip.getStatusDestinations(DestinationStatus.WISHLIST).contains(testDestination2));
    }

    // removes 2 planned destinations from the trip
    @Test
    void testRemovePlannedDestination() {
        testTrip.addDestination(testDestination1);
        testTrip.addDestination(testDestination3);
        testDestination1.setDestinationStatus(DestinationStatus.PLANNED);
        testDestination3.setDestinationStatus(DestinationStatus.PLANNED);
        testTrip.removeDestination(testDestination1);
        testDestination3.setDestinationStatus(DestinationStatus.VISITED);
        assertTrue(testTrip.getStatusDestinations(DestinationStatus.VISITED).contains(testDestination3));
        assertFalse(testTrip.getStatusDestinations(DestinationStatus.PLANNED).contains(testDestination3));
        assertFalse(testTrip.getStatusDestinations(DestinationStatus.PLANNED).contains(testDestination1));
        assertEquals(0, testTrip.getPlannedExpenses());
        assertEquals(10000, testTrip.getTotalTripExpense());
    }

    // adds 3 planned destinations to the trip
    @Test
    void testAddMultiplePlannedDestination() {
        testTrip.addDestination(testDestination1);
        testTrip.addDestination(testDestination2);
        testTrip.addDestination(testDestination3);
        testDestination1.setDestinationStatus(DestinationStatus.PLANNED);
        testDestination2.setDestinationStatus(DestinationStatus.PLANNED);
        testDestination3.setDestinationStatus(DestinationStatus.PLANNED);
        assertTrue(testTrip.getStatusDestinations(DestinationStatus.PLANNED).contains(testDestination1));
        assertTrue(testTrip.getStatusDestinations(DestinationStatus.PLANNED).contains(testDestination2));
        assertTrue(testTrip.getStatusDestinations(DestinationStatus.PLANNED).contains(testDestination3));
        assertEquals(3, testTrip.getStatusDestinations(DestinationStatus.PLANNED).size());
        assertEquals(11000, testTrip.getPlannedExpenses());
    }

    // removes 2 planned destinations from the trip
    @Test
    void testCheckRemovedPlannedDestinationCost() {
        testTrip.addDestination(testDestination1);
        testDestination1.setDestinationStatus(DestinationStatus.PLANNED);
        testTrip.addDestination(testDestination2);
        testDestination2.setDestinationStatus(DestinationStatus.PLANNED);
        testTrip.addDestination(testDestination3);
        testDestination3.setDestinationStatus(DestinationStatus.PLANNED);
        testTrip.removeDestination(testDestination3);
        testTrip.removeDestination(testDestination2);
        assertTrue(testTrip.getDestinations().contains(testDestination1));
        assertFalse(testTrip.getDestinations().contains(testDestination3));
        assertFalse(testTrip.getDestinations().contains(testDestination2));
        assertEquals(700, testTrip.getPlannedExpenses());
    }



}
