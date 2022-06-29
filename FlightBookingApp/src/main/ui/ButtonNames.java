package ui;

// creates constants for button names
public enum ButtonNames {
    BOOK("Book a flight"),
    VIEW("View your bookings"),
    SAVE("Save booking to file"),
    LOAD("Load booking from file"),
    ECONOMY("Economy"),
    BUSINESS("Business"),
    FIRST_CLASS("First Class"),
    NEXT1("Next"),
    NEXT2("Next"),
    FIRST_TIME("8:30"),
    SECOND_TIME("16:00"),
    THIRD_TIME("19:00");

    private final String name;

    ButtonNames(String name) {
        this.name = name;
    }

    //EFFECTS: returns name value of this button
    public String getValue() {
        return name;
    }

}
