package persistence;

import model.Activity;
import model.Destination;
import model.DestinationStatus;
import model.Trip;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Code sourced from JsonSerializationDemo

// Unit tests for the JsonReader class.
class JsonReaderTest extends JsonTest {

    private List<Destination> destinations;
    private Trip testTrip;
    private Destination testDestination1;
    private Destination testDestination2;
    private Destination testDestination3;
    private List<Activity> activities1;
    private List<Activity> activities2;

    @BeforeEach
    void runBefore() {
        destinations = new ArrayList<>();
        activities1 = new ArrayList<>();
        testDestination1 = new Destination("Sofia", "Bulgaria", 700, activities1,
                DestinationStatus.PLANNED);
        Activity testActivity2 = new Activity("sailing", 50);
        activities2 = new ArrayList<>();
        activities2.add(testActivity2);
        testDestination2 = new Destination("Toronto", "Canada", 300, activities2,
                DestinationStatus.PLANNED);

        testTrip = new Trip("New Trip", destinations);

    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Trip trip = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTrip() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTrip.json");
        try {
            Trip trip = reader.read();
            assertEquals("New Trip", trip.getTripName());
            assertEquals(0, trip.getNumDestinations());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderAddTwoDestinations() {
        JsonReader reader = new JsonReader("./data/testReaderAddTwoDestinations.json");
        try {
            Trip trip = reader.read();
            assertEquals("New Trip", trip.getTripName());
            List<Destination> destinations = trip.getDestinations();
            assertEquals(2, destinations.size());
            checkDestination("Sofia", "Bulgaria", 700, testDestination1.getActivities(),
                    DestinationStatus.PLANNED, destinations.get(0));
            checkDestination("Toronto", "Canada", 300, testDestination2.getActivities(),
                    DestinationStatus.VISITED, destinations.get(1));
            checkActivity("sailing", 50, destinations.get(1).getActivities().get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}