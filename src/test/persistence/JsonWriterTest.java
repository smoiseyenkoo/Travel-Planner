package persistence;

import model.Activity;
import model.Destination;
import model.DestinationStatus;
import model.Trip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

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
    void testWriterInvalidFile() {
        try {
            Trip trip = new Trip("My new Trip", destinations);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTrip() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTrip.json");
            writer.open();
            writer.write(testTrip);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTrip.json");
            testTrip = reader.read();
            assertEquals("New Trip", testTrip.getTripName());
            assertEquals(0, testTrip.getNumDestinations());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterTwoDestinations() {
        try {
            testTrip.addDestination(testDestination1);
            testTrip.addDestination(testDestination2);
            JsonWriter writer = new JsonWriter("./data/testWriterTwoDestinations.json");
            writer.open();
            writer.write(testTrip);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterTwoDestinations.json");
            testTrip = reader.read();
            assertEquals("New Trip", testTrip.getTripName());
            List<Destination> destinations = testTrip.getDestinations();
            assertEquals(2, destinations.size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
