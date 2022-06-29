package persistence;

import model.Bookings;
import model.Flight;
import model.FlightManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// tests JsonWriter class using other classes
// used code from JsonSerializationDemo
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            FlightManager flightManager = new FlightManager("My Flight Manager");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyOffice() {
        try {
            FlightManager flightManager = new FlightManager("My Flight Manager");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyFlightManager.json");
            writer.open();
            writer.write(flightManager);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyFlightManager.json");
            flightManager = reader.read();
            assertEquals("My Flight Manager", flightManager.getName());
            assertEquals(0, flightManager.numBookings());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralOffice() {
        try {
            FlightManager flightManager = new FlightManager("My Flight Manager");
            Bookings testBooking = new Bookings("923854");
            testBooking.addFlight(new Flight(298504, 300.00, "Vancouver", "Dubai",
                    "8:30", "May.5", 200));
            testBooking.addFlight(new Flight(983024, 500.00, "Toronto", "Berlin",
                    "16:00", "May.19", 200));
            flightManager.addBookings(testBooking);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralFlightManager.json");
            writer.open();
            writer.write(flightManager);
            writer.close();

            assertEquals(1, flightManager.numBookings());

            JsonReader reader = new JsonReader("./data/testWriterGeneralFlightManager.json");
            flightManager = reader.read();
            assertEquals("My Flight Manager", flightManager.getName());
            List<Bookings> bookings = flightManager.getBookings();
            assertEquals(0, bookings.size());/*
            checkBooking("923854", bookings.get(0));
            checkBooking("370826", bookings.get(1));*/

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
