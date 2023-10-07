package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DestinationTest {
    private Destination testDestination;
    private List<String> activities;


    @BeforeEach
    void runBefore() {
        activities = new ArrayList<>();
        testDestination = new Destination("Sydney", "Australia", 500, activities);
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
        testDestination.addActivity("Surfing");
        assertTrue(testDestination.getActivities().contains("Surfing"));
    }
    // test two activities in activities
    @Test
    void testAddTwoActivities() {
        testDestination.addActivity("Surfing");
        testDestination.addActivity("Bird Watching");
        assertTrue(testDestination.getActivities().contains("Surfing"));
        assertTrue(testDestination.getActivities().contains("Bird Watching"));
    }
}