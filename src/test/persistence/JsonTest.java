package persistence;

import model.Activity;
import model.DestinationStatus;
import model.Destination;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Code sourced from JsonSerializationDemo
public class JsonTest {
    protected void checkDestination(String name, DestinationStatus status, Destination destination) {
        assertEquals(name, destination.getCity());
        assertEquals(status, destination.getDestinationStatus());
    }

    protected void checkActivity(String name, int cost, Activity activity) {
        assertEquals(name, activity.getActivityName());
        assertEquals(cost, activity.getActivityCost());
    }
}
