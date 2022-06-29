package persistence;

import model.Bookings;
import model.Flight;
import model.FlightManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// tests JsonReader class using other classes
// used code from JsonSerializationDemo
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            FlightManager flightManager = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyOffice() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyFlightManager.json");
        try {
            FlightManager flightManager = reader.read();
            assertEquals("My Flight Manager", flightManager.getName());
            assertEquals(0, flightManager.numBookings());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralOffice() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralFlightManager.json");
        try {
            FlightManager flightManager = reader.read();
            Bookings testBooking = new Bookings("450821");
            testBooking.addFlight(new Flight(973514, 800.00, "Vancouver", "Rome",
                    "8:30", "June.15", 200));
            testBooking.addFlight(new Flight(392864, 500.00, "Toronto", "Sydney",
                    "16:00", "May.23", 200));
            flightManager.addBookings(testBooking);
            assertEquals("My Flight Manager", flightManager.getName());
            List<Bookings> bookings = flightManager.getBookings();
            assertEquals(1, bookings.size());
            checkBooking("450821", bookings.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


















}
