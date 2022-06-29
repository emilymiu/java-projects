package ui;

import exceptions.FullFlightException;
import model.FlightManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import static ui.FlightAppGUI.JSON_STORE;

// represents main menu of the GUI, used code from SmartHome, ComboBoxDemo2, CheckBoxDemo,
// RadioButtonDemo, FormattedTextFieldDemo, PaintingIconEx
public class HomeMenu extends JPanel implements PropertyChangeListener {
    protected JButton bookButton;
    protected JButton viewButton;
    protected JButton saveButton;
    protected JButton loadButton;
    protected JButton nextButton1;
    protected JButton nextButton2;

    protected JRadioButton economyButton;
    protected JRadioButton businessButton;
    protected JRadioButton firstClassButton;
    protected JRadioButton firstTimeButton;
    protected JRadioButton secondTimeButton;
    protected JRadioButton thirdTimeButton;

    protected FlightManager flightManager;
    protected FlightAppGUI controller;

    private String origin;
    private String destination;
    private String departureDate;

    private JLabel originLabel;
    private JLabel destinationLabel;
    private JLabel departureDateLabel;
    private JLabel classLabel;
    private JLabel departureTimeLabel;
    private JLabel successLabel;
    private JLabel viewLabel;
    private JLabel loadedLabel;
    private JLabel saveLabel;
    private JLabel pictureLabel;
    private JLabel failLabel;

    private JFormattedTextField originField;
    private JFormattedTextField destinationField;
    private JFormattedTextField departureDateField;
    private JTextField classField;
    private JTextField departureTimeField;
    private JTextField successField;
    private JTextField viewField;
    private JTextField loadedField;
    private JTextField saveField;
    private JTextField failField;

    private boolean loaded;

    public HomeMenu(FlightAppGUI controller) {
        origin = "";
        destination = "";
        departureDate = "";
        this.controller = controller;
        loaded = false;
        placeHomeButtons();
        revalidate();
        repaint();
    }

    //EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    //EFFECTS: returns the FlightAppGUI controller for this tab
    public FlightAppGUI getController() {
        return controller;
    }

    // MODIFIES: this
    // EFFECTS: creates buttons for main menu
    public void placeHomeButtons() {
        bookButton = new JButton(ButtonNames.BOOK.getValue());
        viewButton = new JButton(ButtonNames.VIEW.getValue());
        saveButton = new JButton(ButtonNames.SAVE.getValue());
        loadButton = new JButton(ButtonNames.LOAD.getValue());

        JPanel buttonRow1 = formatButtonRow(bookButton);
        JPanel buttonRow2 = formatButtonRow(viewButton);
        JPanel buttonRow3 = formatButtonRow(saveButton);
        JPanel buttonRow4 = formatButtonRow(loadButton);

        buttonRow1.setBounds(10, 20, WIDTH, HEIGHT / 6);
        buttonRow2.setBounds(10, 40, WIDTH, HEIGHT / 6);
        buttonRow3.setBounds(10, 60, WIDTH, HEIGHT / 6);
        buttonRow4.setBounds(10, 80, WIDTH, HEIGHT / 6);

        add(buttonRow1);
        add(buttonRow2);
        add(buttonRow3);
        add(buttonRow4);
        readFirstButtons();
        updatePicture();
    }

    // MODIFIES: this
    // EFFECTS: creates image for main menu
    protected void updatePicture() {
        ImageIcon icon = new ImageIcon("data/airplane.jpg");
        int width = icon.getIconWidth() / 2;
        int height = icon.getIconHeight() / 2;
        Image scaled = scaleImage(icon.getImage(), width, height);
        ImageIcon scaledIcon = new ImageIcon(scaled);
        pictureLabel = new JLabel(scaledIcon);
        add(pictureLabel);
        setVisible(true);
    }

    // EFFECTS: scales image to appropriate size
    private Image scaleImage(Image image, int w, int h) {
        Image scaled = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return scaled;
    }

    // EFFECTS: reads button response from main menu
    public void readFirstButtons() {
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectClass();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loaded) {
                    viewLoaded();
                }
                viewBookings();
            }
        });
        readSecondOptions();
    }

    // EFFECTS: reads button response from main menu
    private void readSecondOptions() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFlightManager();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFlightManager();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: prints out booking that was loaded
    private void viewLoaded() {
        JFrame loadFrame = new JFrame();
        loadFrame.setSize(WIDTH, HEIGHT);
        loadFrame.revalidate();
        loadFrame.repaint();
        for (String s : flightManager.viewBookings()) {
            loadedLabel = new JLabel(s);
        }
        loadedField = new JTextField(5);
        loadFrame.add(loadedField);
        loadFrame.add(loadedLabel);
        loadFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: prints out booking of flights
    private void viewBookings() {
        JFrame viewFrame = new JFrame();
        viewFrame.setSize(WIDTH, HEIGHT);
        viewFrame.revalidate();
        viewFrame.repaint();
        for (String s : getController().getFlightManager().viewBookings()) {
            viewLabel = new JLabel(s);
        }
        viewField = new JTextField(5);
        viewFrame.add(viewField);
        viewFrame.add(viewLabel);
        viewFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: loads flight manager from file
    private void loadFlightManager() {
        loaded = true;
        try {
            flightManager = getController().getJsonReader().read();
            System.out.println("Loaded " + flightManager.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the flight manager to file
    private void saveFlightManager() {
        try {
            getController().getJsonWriter().open();
            getController().getJsonWriter().write(getController().getFlightManager());
            getController().getJsonWriter().close();
            JFrame saveFrame = new JFrame();
            saveFrame.setSize(WIDTH, HEIGHT);
            saveLabel = new JLabel("Saved " + getController().getFlightManager().getName() + " to " + JSON_STORE);
            saveField = new JTextField(5);
            saveFrame.add(saveField);
            saveFrame.add(saveLabel);
            saveFrame.setVisible(true);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to select economy, business, or first class
    private void selectClass() {
        JFrame classFrame = new JFrame();
        classFrame.setSize(WIDTH, HEIGHT);
        classFrame.revalidate();
        classFrame.repaint();

        classLabel = new JLabel("What seating class would you like?");
        classField = new JTextField(5);
        classFrame.add(classField);
        classFrame.add(classLabel);
        classFrame.setVisible(true);

        economyButton = new JRadioButton(ButtonNames.ECONOMY.getValue());
        economyButton.setMnemonic(KeyEvent.VK_B);
        economyButton.setActionCommand(ButtonNames.ECONOMY.getValue());
        economyButton.setSelected(true);

        businessButton = new JRadioButton(ButtonNames.BUSINESS.getValue());
        businessButton.setMnemonic(KeyEvent.VK_B);
        businessButton.setActionCommand(ButtonNames.BUSINESS.getValue());

        firstClassButton = new JRadioButton(ButtonNames.FIRST_CLASS.getValue());
        firstClassButton.setMnemonic(KeyEvent.VK_B);
        firstClassButton.setActionCommand(ButtonNames.FIRST_CLASS.getValue());

        addClassButtons(classFrame);
    }

    // EFFECTS: adds seating class buttons to the JPanel
    private void addClassButtons(JFrame classFrame) {
        ButtonGroup group = new ButtonGroup();
        group.add(economyButton);
        group.add(businessButton);
        group.add(firstClassButton);

        classFrame.add(economyButton, BorderLayout.NORTH);
        classFrame.add(businessButton, BorderLayout.WEST);
        classFrame.add(firstClassButton, BorderLayout.SOUTH);

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        readClassButtons1();
    }

    // MODIFIES: this
    // EFFECTS: reads button input after seating class is selected
    private void readClassButtons1() {
        economyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getController().getFlightManager().assignClass("e");
                selectOriginDestination();
            }
        });
        businessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getController().getFlightManager().assignClass("b");
                selectOriginDestination();
            }
        });
        firstClassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getController().getFlightManager().assignClass("First Class");
                selectOriginDestination();

            }
        });
    }

    // MODIFIES: this
    // EFFECTS: prompts user to enter origin and destination of flight
    private void selectOriginDestination() {
        JFrame placeFrame = new JFrame();
        placeFrame.revalidate();
        placeFrame.repaint();
        placeFrame.setSize(WIDTH, HEIGHT);
        originLabel = new JLabel("Where are you flying from?");
        destinationLabel = new JLabel("Where would you like to go?");

        originField = new JFormattedTextField();
        originField.setValue(origin);
        originField.setColumns(10);
        originField.addPropertyChangeListener("value", this);

        destinationField = new JFormattedTextField();
        destinationField.setValue(destination);
        destinationField.setColumns(10);
        destinationField.addPropertyChangeListener("value", this);

        originLabel.setLabelFor(originField);
        destinationLabel.setLabelFor(destinationField);

        createPlacePanel(placeFrame);
    }

    // MODIFIES: this
    // EFFECTS: creates JPanel for origin and destination selections
    public void createPlacePanel(JFrame placeFrame) {
        JPanel labelPane = new JPanel(new GridLayout(0, 1));
        labelPane.add(originLabel);
        labelPane.add(destinationLabel);

        JPanel fieldPane = new JPanel(new GridLayout(0, 1));
        fieldPane.add(originField);
        fieldPane.add(destinationField);

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        placeFrame.add(labelPane, BorderLayout.CENTER);
        placeFrame.add(fieldPane, BorderLayout.LINE_END);
        placeFrame.setVisible(true);

        nextButton1 = new JButton(ButtonNames.NEXT1.getValue());
        JPanel buttonRow1 = formatButtonRow(nextButton1);
        buttonRow1.setSize(WIDTH, HEIGHT / 6);

        placeFrame.add(buttonRow1, BorderLayout.SOUTH);
        setVisible(true);
        readNextButton1();
    }

    // MODIFIES: this
    // EFFECTS: updates origin and destination of flight when "value" is changed
    public void propertyChange(PropertyChangeEvent e) {
        Object source = e.getSource();
        if (source == originField) {
            origin = ((String) originField.getValue());
        } else if (source == destinationField) {
            destination = ((String) destinationField.getValue());
            if (!getController().getFlightManager().canFly(origin, destination)) {
                System.out.println("Cannot book your flight.\n");
            }
        } else if (source == departureDateField) {
            departureDate = ((String) departureDateField.getValue());
            getController().getFlightManager().assignDepartureDate(departureDate);
        }

    }

    // EFFECTS: reads button input after origin and destination is entered
    private void readNextButton1() {
        nextButton1.addActionListener(e -> {
            selectDepartureDate();

        });
    }

    // EFFECTS: reads button input after departure date is entered
    private void readNextButton2() {
        nextButton2.addActionListener(e -> {
            selectDepartureTime();
        });
    }

    // MODIFIES: this
    // EFFECTS: prompts user to select departure date
    private void selectDepartureDate() {
        JFrame dateFrame = new JFrame();
        dateFrame.revalidate();
        dateFrame.setSize(WIDTH, HEIGHT);
        departureDateLabel = new JLabel("When would you like to depart? " + "\tPlease enter as: Month.day");
        departureDateField = new JFormattedTextField();
        departureDateField.setValue(departureDate);
        departureDateField.setColumns(10);
        departureDateField.addPropertyChangeListener("value", this);
        departureDateLabel.setLabelFor(departureDateField);

        JPanel labelPane = new JPanel(new GridLayout(0, 1));
        labelPane.add(departureDateLabel);
        JPanel fieldPane = new JPanel(new GridLayout(0, 1));
        fieldPane.add(departureDateField);

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        dateFrame.add(labelPane, BorderLayout.CENTER);
        dateFrame.add(fieldPane, BorderLayout.LINE_END);
        dateFrame.setVisible(true);

        nextButton2 = new JButton(ButtonNames.NEXT2.getValue());
        JPanel buttonRow2 = formatButtonRow(nextButton2);
        buttonRow2.setSize(WIDTH, HEIGHT / 6);
        dateFrame.add(buttonRow2, BorderLayout.SOUTH);
        setVisible(true);
        readNextButton2();
    }

    // MODIFIES: this
    // EFFECTS: prompts user to select departure time from the choices
    private void selectDepartureTime() {
        JFrame timeFrame = new JFrame();
        timeFrame.setSize(WIDTH, HEIGHT);
        departureTimeLabel = new JLabel("\nSelect from these departure times:");
        departureTimeField = new JTextField(5);
        timeFrame.add(departureTimeField);
        timeFrame.add(departureTimeLabel);
        timeFrame.setVisible(true);

        firstTimeButton = new JRadioButton(ButtonNames.FIRST_TIME.getValue());
        firstTimeButton.setMnemonic(KeyEvent.VK_B);
        firstTimeButton.setActionCommand(ButtonNames.FIRST_TIME.getValue());
        firstTimeButton.setSelected(true);

        secondTimeButton = new JRadioButton(ButtonNames.SECOND_TIME.getValue());
        secondTimeButton.setMnemonic(KeyEvent.VK_B);
        secondTimeButton.setActionCommand(ButtonNames.SECOND_TIME.getValue());

        thirdTimeButton = new JRadioButton(ButtonNames.THIRD_TIME.getValue());
        thirdTimeButton.setMnemonic(KeyEvent.VK_B);
        thirdTimeButton.setActionCommand(ButtonNames.THIRD_TIME.getValue());

        addTimeButtons(timeFrame);
    }

    // EFFECTS: adds seating class buttons to the JPanel
    private void addTimeButtons(JFrame timeFrame) {
        ButtonGroup group = new ButtonGroup();
        group.add(firstTimeButton);
        group.add(secondTimeButton);
        group.add(thirdTimeButton);
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.add(firstTimeButton);
        radioPanel.add(secondTimeButton);
        radioPanel.add(thirdTimeButton);
        timeFrame.add(radioPanel, BorderLayout.LINE_START);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        readDepartureTimeButton();
    }

    // MODIFIES: this
    // EFFECTS: reads button input after departure time is selected
    //          catches FullFlightException from bookingSuccessful
    private void readDepartureTimeButton() {
        firstTimeButton.addActionListener(e -> {
            getController().getFlightManager().assignDepartureTime(1);
            try {
                bookingSuccessful();
            } catch (FullFlightException fullFlightException) {
                JFrame failFrame = new JFrame();
                failFrame.setSize(WIDTH, HEIGHT);
                failLabel = new JLabel("Your chosen flight is full.");
                failField = new JTextField(5);
                failFrame.add(failField);
                failFrame.add(failLabel);
                failFrame.setVisible(true);
            }
        });

        readSecondTimeButton();
    }

    // MODIFIES: this
    // EFFECTS: reads button input for second option of departure time
    //          catches FullFlightException from bookingSuccessful
    private void readSecondTimeButton() {
        secondTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getController().getFlightManager().assignDepartureTime(2);
                try {
                    bookingSuccessful();
                } catch (FullFlightException fullFlightException) {
                    JFrame failFrame = new JFrame();
                    failFrame.setSize(WIDTH, HEIGHT);
                    failLabel = new JLabel("Your chosen flight is full.");
                    failField = new JTextField(5);
                    failFrame.add(failField);
                    failFrame.add(failLabel);
                    failFrame.setVisible(true);
                }

            }
        });

        readThirdTimeButton();
    }

    // MODIFIES: this
    // EFFECTS: reads button input for third option of departure time
    //          catches FullFlightException from bookingSuccessful
    private void readThirdTimeButton() {
        thirdTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getController().getFlightManager().assignDepartureTime(3);
                try {
                    bookingSuccessful();
                } catch (FullFlightException fullFlightException) {
                    JFrame failFrame = new JFrame();
                    failFrame.setSize(WIDTH, HEIGHT);
                    failLabel = new JLabel("Your chosen flight is full.");
                    failField = new JTextField(5);
                    failFrame.add(failField);
                    failFrame.add(failLabel);
                    failFrame.setVisible(true);
                }
            }
        });

    }

    // MODIFIES: this
    // EFFECTS: displays booking confirmation message if booking successful
    //          throws FullFlightException if purchaseTicket is not true
    private void bookingSuccessful() throws FullFlightException {
        JFrame successFrame = new JFrame();
        successFrame.setSize(WIDTH, HEIGHT);
        if (getController().getFlightManager().purchaseTicket()) {
            successLabel = new JLabel("Your flight has been booked!");
        }
        successField = new JTextField(5);
        successFrame.add(successField);
        successFrame.add(successLabel);
        successFrame.setVisible(true);
    }
}
