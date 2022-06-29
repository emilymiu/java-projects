package persistence;

import model.Bookings;

import static org.junit.jupiter.api.Assertions.assertEquals;

// tests Json classes
// used code from JsonSerializationDemo
public class JsonTest {
    protected void checkBooking(String bookingID, Bookings bookings) {
        assertEquals(bookingID, bookings.getBookingID());
    }
}
