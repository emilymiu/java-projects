package model;

import exceptions.FullFlightException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// tests methods in Flight, FlightManager, and Bookings
public class FlightTest {
    private Flight testFlight1;
    private Flight testFlight2;
    private Flight testFlight3;
    private Flight testFLight4;
    private Bookings testBookings;
    private FlightManager testFlightManager1;
    private FlightManager testFlightManager2;

    @BeforeEach
    void runBefore() {
        testFlight1 = new Flight(498519, 300.00, "Vancouver", "Miami", "8:30", "Feb.18", 200);
        testFlight2 = new Flight(843127, 700.00, "Toronto", "Athens", "16:00", "Mar.3", 0);
        testFlight3 = new Flight(0, 0.0, "", "", "", "", 0);
        testFLight4 = new Flight(230976, 500.00, "Regina", "Regina", "19:00", "Mar.19", 200);
        testBookings = new Bookings("896321");
        testFlightManager1 = new FlightManager("My Flight Manager 1");
        testFlightManager2 = new FlightManager("My Flight Manager 2");

    }

    @Test
    void testConstructor() {
        assertEquals(498519, testFlight1.getFlightNumber());
        assertEquals(300.00, testFlight1.getPrice());
        assertEquals("Vancouver", testFlight1.getOrigin());
        assertEquals("Miami", testFlight1.getDestination());
        assertEquals("8:30", testFlight1.getDepartureTime());
        assertEquals("Feb.18", testFlight1.getDepartureDate());
        assertEquals(200, testFlight1.getFlightCapacity());
        assertEquals(300.00, testFlight1.getPrice());
        assertEquals(200, testFlight1.getNumSeatsLeft());

        testFlight3.setPrice(800.00);
        testFlight3.setOrigin("Edmonton");
        testFlight3.setDestination("Berlin");
        testFlight3.setDepartureTime("19:00");
        testFlight3.setDepartureDate("Apr.4");
        testFlight3.setFlightNumber(345678);

        assertEquals(800.00, testFlight3.getPrice());
        assertEquals("Edmonton", testFlight3.getOrigin());
        assertEquals("Berlin", testFlight3.getDestination());
        assertEquals("19:00", testFlight3.getDepartureTime());
        assertEquals("Apr.4", testFlight3.getDepartureDate());
        assertEquals(345679, testFlight3.getFlightNumber());

        assertEquals("896321", testBookings.getBookingID());
        assertEquals(0, testFlightManager1.numBookings());

    }

    @Test
    void testDisplayFlight() {
        assertEquals("Flight " + 498519 + " from " + "Vancouver" + " to " + "Miami" + " leaving on " + "Feb.18"
                + " at " + "8:30" + " for " + "$" + 300.00, testFlight1.displayFlight());
    }

    @Test
    void testAddFlight() {
        testBookings.addFlight(testFlight1);
        assertEquals(1, testBookings.displayBookings().size());
        testBookings.addFlight(testFlight2);
        assertEquals(2, testBookings.displayBookings().size());
    }

    @Test
    void testDisplayBookings() {
        testBookings.addFlight(testFlight2);
        assertEquals(1, testBookings.displayBookings().size());
        testBookings.addFlight(testFlight1);
        assertEquals(2, testBookings.displayBookings().size());
    }

    @Test
    void testCheckSeatsLeft() {
        assertTrue(testFlight1.checkSeatsLeft());
        assertFalse(testFlight2.checkSeatsLeft());
    }

    @Test
    void testCanFly(){
        assertTrue(testFlightManager1.canFly(testFlight1.getOrigin(), testFlight1.getDestination()));
        assertFalse(testFlightManager1.canFly(testFLight4.getOrigin(), testFLight4.getDestination()));
    }

    @Test
    void testAssignDepartureTime() {
        testFlightManager1.assignDepartureTime(1);
        testFlightManager2.assignDepartureTime(2);
        assertEquals("8:30", testFlightManager1.selected.getDepartureTime());
        assertEquals("16:00", testFlightManager2.selected.getDepartureTime());
        testFlightManager2.assignDepartureTime(3);
        assertEquals("19:00", testFlightManager2.selected.getDepartureTime());
    }

    @Test
    void testAssignDepartureDate() {
        testFlightManager1.assignDepartureDate("April.11");
        testFlightManager2.assignDepartureDate("Mar.3");
        assertEquals("April.11", testFlightManager1.selected.getDepartureDate());
        assertEquals("Mar.3", testFlightManager2.selected.getDepartureDate());
        testFlightManager2.assignDepartureDate("May.9");
        assertEquals("May.9", testFlightManager2.selected.getDepartureDate());
    }

    @Test
    void testAssignClass() {
        testFlightManager1.assignClass("e");
        testFlightManager2.assignClass("b");
        assertEquals(300.00, testFlightManager1.selected.getPrice());
        assertEquals(500.00, testFlightManager2.selected.getPrice());
        testFlightManager2.assignClass("f");
        assertEquals(800.00, testFlightManager2.selected.getPrice());
    }

    @Test
    void testPurchaseTicketNotFull() {
        testFlightManager1.selected.setNumSeatsLeft(1);
        try {
            testFlightManager1.purchaseTicket();
        } catch (FullFlightException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testPurchaseTicketFull() {
        testFlightManager2.selected.setNumSeatsLeft(0);
        try {
            testFlightManager2.purchaseTicket();
            fail("Exception should have been thrown");
        } catch (FullFlightException fullFlightException) {
            // expected
        }
    }

    @Test
    void testViewBookings() {
        testFlightManager1.newBooking.addFlight(testFlight1);
        assertEquals(1, testFlightManager1.viewBookings().size());
        testFlightManager1.newBooking.addFlight(testFlight2);
        assertEquals(2, testFlightManager1.viewBookings().size());
        assertEquals(0, testFlightManager2.viewBookings().size());
    }

    @Test
    void testAddBookings() {
        testBookings.addFlight(testFlight1);
        testFlightManager1.addBookings(testBookings);
        assertEquals(1, testFlightManager1.allBookings.size());
        testBookings.addFlight(testFlight2);
        testFlightManager1.addBookings(testBookings);
        assertEquals(2, testFlightManager1.allBookings.size());
        testBookings.addFlight(testFlight3);
        testFlightManager1.addBookings(testBookings);
        assertEquals(3, testFlightManager1.allBookings.size());
    }

}