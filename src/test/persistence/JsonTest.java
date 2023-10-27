package persistence;

import model.Activity;
import model.DestinationStatus;
import model.Destination;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Code sourced from JsonSerializationDemo
// Unit tests for the Json class.
public class JsonTest {
    protected void checkDestination(String city, String country, int travelCost, List<Activity> activities,
                                    DestinationStatus destinationStatus, Destination destination) {
        assertEquals(city, destination.getCity());
        assertEquals(country, destination.getCountry());
        assertEquals(travelCost, destination.getTravelCost());
        assertEquals(activities, destination.getActivities());
        assertEquals(destinationStatus, destination.getDestinationStatus());
    }

    protected void checkActivity(String name, int cost, Activity activity) {
        assertEquals(name, activity.getActivityName());
        assertEquals(cost, activity.getActivityCost());
    }
}
