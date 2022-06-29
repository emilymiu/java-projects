# Fly with Em

## Travel Agency

Booking a flight for a trip involves several steps. Trying 
to compare different flights for the best deal is time-consuming.
My project is a travel agency that books flights to 
any destination you desire with a touch of a hand.
The agency chooses the **following** based on your preferences: 
- time/dates of flight
- seat type
- destination/origin of flight

Anyone looking to book a flight can use this application
to conveniently plan their trip. Just enter a few things about
your preferences and instantly get results. This project is of interest to me
because I enjoy travelling, and I understand how much work it is
to navigate through several sites in order to book a flight.
With an application like this, you can easily choose what you want.

## User Stories

- As a user, I want to be able to add a flight to a list of my bookings
- As a user, I want to be able to choose between seat types
- As a user, I want to be able to buy seats based on availability
- As a user, I want to be able to choose the origin/destination and dates/time
- As a user, I want to be able to view my bookings
- As a user, I want to be able to save my bookings to file
- As a user, when I start the application, I want to be given the option to load my bookings from file

## Phase 4: Task 2
I chose to test and design the FlightManager class in my model package that is robust. 
The method I chose is purchaseTicket. The methods,
readDepartureTimeButton, readSecondTimeButton,
and readThirdTimeButton in HomeMenu 
throw the exception.

## Phase 4: Task 3
If I had more time to work on my project, I would
- make another class to do most of the work in HomeMenu, so that
HomeMenu would only place buttons
- combine FlightAppGUI and HomeMenu, so I don't have to
call getController().getFlightManager when I want
to call a method from FlightManager


