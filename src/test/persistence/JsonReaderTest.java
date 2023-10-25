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

class JsonReaderTest extends JsonTest {

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
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}