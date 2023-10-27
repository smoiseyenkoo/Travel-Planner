package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Destination class.

class DestinationTest {
    private Destination testDestination;
    private List<Activity> activities;
    private Activity testActivity1;
    private Activity testActivity2;
    private Activity testActivity3;


    @BeforeEach
    void runBefore() {
        activities = new ArrayList<>();
        testDestination = new Destination("Sydney", "Australia", 500, activities,
                DestinationStatus.PLANNED);
        testActivity1 = new Activity("Surfing", 100);
        testActivity2 = new Activity("Bird Watching", 15);
        testActivity3 = new Activity("Swimming", 0);
    }


    @Test
    void testConstructor() {
        assertEquals("Sydney", testDestination.getCity());
        assertEquals("Australia", testDestination.getCountry());
        assertEquals(500, testDestination.getTravelCost());
        assertEquals(this.activities, testDestination.getActivities());
    }

    // test one activity in activities
    @Test
    void testAddActivity() {
        testDestination.addActivity(testActivity1);
        assertTrue(testDestination.getActivities().contains(testActivity1));
        assertEquals(600, testDestination.getTravelCost());
    }
    // test two activities in activities
    @Test
    void testAddTwoActivities() {
        testDestination.addActivity(testActivity1);
        testDestination.addActivity(testActivity2);
        assertTrue(testDestination.getActivities().contains(testActivity1));
        assertTrue(testDestination.getActivities().contains(testActivity2));
        assertEquals(615, testDestination.getTravelCost());

    }


    // test to add the same activity to the list
    @Test
    void testAddSameActivity() {
        testDestination.addActivity(testActivity1);
        testDestination.addActivity(testActivity2);
        testDestination.addActivity(testActivity1);
        assertTrue(testDestination.getActivities().contains(testActivity1));
        assertTrue(testDestination.getActivities().contains(testActivity2));
        assertEquals(715, testDestination.getTravelCost());

    }

    // test to set the status of the destination to WISHLIST
    @Test
    void testSetStatusWishlist() {
        testDestination.setDestinationStatus(DestinationStatus.PLANNED);
        testDestination.setDestinationStatus(DestinationStatus.valueOf(3));
        assertEquals(DestinationStatus.WISHLIST, testDestination.getDestinationStatus());
    }

    // test to set the status of the destination to PLANNED
    @Test
    void testSetStatusPLANNED() {
        testDestination.setDestinationStatus(DestinationStatus.PLANNED);
        testDestination.setDestinationStatus(DestinationStatus.valueOf(1));
        assertEquals(DestinationStatus.PLANNED, testDestination.getDestinationStatus());
    }

    // test to set the status of the destination to VISITED
    @Test
    void testSetStatusVisited() {
        testDestination.setDestinationStatus(DestinationStatus.WISHLIST);
        assertEquals(DestinationStatus.WISHLIST, testDestination.getDestinationStatus());
        testDestination.setDestinationStatus(DestinationStatus.valueOf(2));
        assertEquals(DestinationStatus.VISITED, testDestination.getDestinationStatus());
    }

    // test to set the status of the destination to DEFAULT
    @Test
    void testSetStatusDefault() {
        testDestination.setDestinationStatus(DestinationStatus.PLANNED);
        assertEquals(DestinationStatus.PLANNED, testDestination.getDestinationStatus());
        testDestination.setDestinationStatus(DestinationStatus.valueOf(11));
        assertEquals(DestinationStatus.PLANNED, testDestination.getDestinationStatus());
    }

    // test destination to String
    @Test
    void testToString() {
        assertEquals("Destination{ID= 3city='Sydney', country='Australia', travelCost=500," +
                " amount of activities=0, destinationStatus=PLANNED}", testDestination.toString());
    }

//    // test to set JSON object
//    @Test
//    void testToJson() {
//        assertEquals(0, testDestination.toJson());
//    }

    // test get ID
    @Test
    void testGetId() {
        assertEquals(1, testDestination.getId());
        assertFalse(testDestination.getId() == 2);
    }



}