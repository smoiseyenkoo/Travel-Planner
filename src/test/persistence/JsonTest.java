package persistence;

import model.DestinationStatus;
import model.Destination;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Code sourced from JsonSerializationDemo
public class JsonTest {
    protected void checkDestination(String name, DestinationStatus status, Destination destination) {
        assertEquals(name, destination.getCity());
        assertEquals(status, destination.getDestinationStatus());
    }
}
