package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    // removes an activity from activities
    @Test
    void testRemoveActivity() {
        testDestination.addActivity(testActivity1);
        testDestination.addActivity(testActivity2);
        testDestination.removeActivity(testActivity2);
        assertTrue(testDestination.getActivities().contains(testActivity1));
        assertFalse(testDestination.getActivities().contains(testActivity2));
        assertEquals(600, testDestination.getTravelCost());

    }

    // removes an activity from activities
    @Test
    void testAddSameActivity() {
        testDestination.addActivity(testActivity1);
        testDestination.addActivity(testActivity2);
        testDestination.addActivity(testActivity1);
        assertTrue(testDestination.getActivities().contains(testActivity1));
        assertTrue(testDestination.getActivities().contains(testActivity2));
        assertEquals(715, testDestination.getTravelCost());

    }
}