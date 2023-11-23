package ui;

import model.Activity;
import model.Destination;
import model.DestinationStatus;
import model.Trip;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;  // Import the Scanner class
import java.awt.event.ActionEvent;
import java.awt.image.ImageObserver;
import java.net.URL;

// Code sourced from JsonSerializationDemo

// Runs the Travel Planner app
public class TravelPlannerApp extends JFrame {
    private static final String JSON_STORE = "./data/trip.json";
    Scanner input;
    Trip newTrip;
    private List<Destination> destinations = new ArrayList<>();
    private Destination newDestination;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static String tripName;
    private JLabel label;
    private JTextField field;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private JTextField cityNameField;
    private JTextField countryNameField;
    private JTextField travelCostField;
    private JTextField activityNameField;
    private JTextField activityCostField;
    private JComboBox<String> statusComboBox;
    private JTextArea activitiesArea;
    private JButton addActivityButton;
    private JButton createDestinationButton;

    private JPanel contentPanel;
    private static final Color LIGHT_PINK = new Color(252, 215, 237);
    private static final Color HOT_PINK = new Color(255, 56, 116);


    private List<Activity> activities;

    // EFFECTS: creates instance of the Travel Planner app
    public static void main(String[] args) {
        new TravelPlannerApp();

    }

    // EFFECTS: constructs New trip and runs Travel planner application
    @SuppressWarnings("methodlength")
    public TravelPlannerApp() {
        super("Travel Planner!");
        input = new Scanner(System.in);  // Create a Scanner object
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        tripName = "Your Trip";
        newTrip = new Trip(tripName, destinations);
        //run();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // Create a single content panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout());
        contentPanel.setBorder(new EmptyBorder(13, 13, 13, 13));
        // Add the content panel to the frame
        setContentPane(contentPanel);



        //setLayout(new FlowLayout());

        JButton loadButton = new JButton(new TravelPlannerApp.LoadTripAction());
        JButton displayDestButton = new JButton(new TravelPlannerApp.ViewDestinationAction());

        add(loadButton);
        add(displayDestButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        addMenu();
        initializeGraphics();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: runs app and displays trip main menu
    public void run() {
        boolean keepGoing = true;
        System.out.println("Welcome to " + tripName + "!");  // Output user input

        while (keepGoing) {
            displayTripMenu();
        }

        System.out.println("\nGoodbye!");
    }


    // EFFECTS: displays main menu of options to user and processes user command
    // MODIFIES: this
    @SuppressWarnings("methodlength") // Signed by Nanjou
    public void displayTripMenu() {
        displayTripMenuOptions();
        int menuValue = input.nextInt();  // Read user input
        switch (menuValue) {
            case 1:
                displayDestinationsMenu();
                break;
            case 2:
                displayDestinations();
                break;
            case 3:
                System.out.println("Loading Your Trip...");
                loadTrip();
                displayTripMenu();
                break;
            case 4:
                saveTrip();
                System.out.println("Saved! goodbye!");
                System.exit(0); // SAVING
            case 5:
                System.out.println("goodbye!");
                System.exit(0); //NOT SAVED AND QUIT
            default:
                System.out.println("invalid entry");
                displayTripMenu();
                break;
        }
    }


    // EFFECTS: displays destinations menu of options to user and processes user command
    // MODIFIES: this
    @SuppressWarnings("methodlength") // Signed by Nanjou
    public void displayDestinationsMenu() {
        displayDestinationMenuOptions();
        int destinationsMenuValue = input.nextInt();
        switch (destinationsMenuValue) {
            case 1:
                displayPlannedList();
                break;
            case 2:
                displayVisitedList();
                break;
            case 3:
                displayWishlist();
                break;
            case 4:
                this.newDestination = makeNewDestination();
                newTrip.addDestination(newDestination);
                break;
            case 5:
                System.out.println("Planned trip cost is : $" + newTrip.getPlannedExpenses());
                break;
            case 6:
                System.out.println("Actual trip cost is : $" + newTrip.getTotalTripExpense());
                break;
            case 7:
                displayDestinations();
                System.out.println("Enter destination ID");
                int selectId = input.nextInt();
                selectDestination(selectId);
                break;
            case 8:
                displayTripMenu();
                break;
            default:
                System.out.println("invalid entry");
        }
    }

    // EFFECTS: displays select destination menu of options to user and processes user command
    // MODIFIES: this
    @SuppressWarnings("methodlength") // Signed by Nanjou
    public void selectDestination(int selectId) {
        Destination selectedDestination = destinations.stream().filter(
                destination -> destination.getId() == selectId).findFirst().get();
        selectDestinationMenu();
        int selectDestinationsInput = input.nextInt();
        switch (selectDestinationsInput) {
            case 1:
                System.out.println(selectedDestination);
                break;
            case 2:
                System.out.println(selectedDestination.getActivities());
                break;
            case 3:
                Activity newActivity = createActivity();
                selectedDestination.addActivity(newActivity);
                break;
            case 4:
                input.nextLine();
                System.out.println("Please Enter Destination Status (planned (1)/visited (2)/wishlist (3)): ");
                int destinationStatusInput = input.nextInt();
                DestinationStatus destinationStatus = DestinationStatus.valueOf(destinationStatusInput);
                selectedDestination.setDestinationStatus(destinationStatus);
                break;
            default:
                displayTripMenu();
        }
    }

    // EFFECTS: displays prompts to make a new destination to user and processes user command
    // MODIFIES: this, destinations
    @SuppressWarnings("methodlength") // Signed by Nanjou
    private Destination makeNewDestination() {

        input.nextLine();
        System.out.println("Please Enter City Name: ");
        String cityName = input.nextLine();  // Read user input
        System.out.println("Please Enter Country Name: ");
        String countryName = input.nextLine();  // Read user input
        System.out.println("Please Enter Travel Cost: $");
        int travelCost = input.nextInt();  // Read user input
        input.nextLine();
        System.out.println("Please Enter Destination Status (planned (1)/visited (2)/wishlist (3)): ");
        int destinationStatusInput = input.nextInt();
        DestinationStatus destinationStatus = DestinationStatus.valueOf(destinationStatusInput);  // Read user input
        input.nextLine();
        System.out.println("Add activities? (yes/no): ");
        String inputAddActivity = input.nextLine();  // Read user input
        List<Activity> activities = new ArrayList<>();
        if ("yes".equalsIgnoreCase(inputAddActivity)) {
            do {
                Activity activity = createActivity();
                activities.add(activity);
                input.nextLine();
                System.out.println("Add activities? (yes/no): ");
                inputAddActivity = input.nextLine();  // Read user input
                if (!"yes".equalsIgnoreCase(inputAddActivity)) {
                    break;
                }
            } while (true);


        } else {
            activities = new ArrayList<>();
        }
        newDestination = new Destination(cityName, countryName, travelCost, activities, destinationStatus);
        return newDestination;
    }

    // EFFECTS: displays prompts to create a new activity to user and processes user command
    // MODIFIES: this
    public Activity createActivity() {
        System.out.println("Please Enter Activity Name: ");
        String activityName = input.nextLine();  // Read user input
        System.out.println("Please Enter Activity Cost: $");
        int activityCost = input.nextInt();
        return new Activity(activityName, activityCost);


    }

    // EFFECTS: displays selected destination menu of options to user
    public void selectDestinationMenu() {
        System.out.println("\nSelected Destination: ");
        System.out.println("1. Display Destination Info");
        System.out.println("2. View Activities List");
        System.out.println("3. Add new Activity");
        System.out.println("4. Change Status of Destination(Planned/Visited/Wishlist");
        System.out.println("5. Back to Main Menu");
        System.out.print("Please enter your choice (1/2/3/4/5): ");
    }

    // EFFECTS: displays destination menu of options to user
    public void displayDestinationMenuOptions() {
        System.out.println("\nDestinations Menu:");
        System.out.println("1. View Planned Destinations");
        System.out.println("2. View Visited Destinations");
        System.out.println("3. View Wishlist Destinations");
        System.out.println("4. Add new Destination");
        System.out.println("5. View planned trip costs");
        System.out.println("6. View total trip costs");
        System.out.println("7. Select Destination");
        System.out.println("8. Back to Main Menu");
        System.out.print("Please enter your choice (1/2/3/4/5/6/7/8): ");
    }

    // EFFECTS: displays trip menu of options to user
    public void displayTripMenuOptions() {
        System.out.println("\nMenu:");
        System.out.println("1. View Destination Menu");
        System.out.println("2. Display Destinations");
        System.out.println("3. Load Trip");
        System.out.println("4. Save and Quit");
        System.out.println("5. Quit without saving");
        System.out.print("Please enter your choice (1/2/3/4/5): ");
    }

    // EFFECTS: displays total list of destinations
    public void displayDestinations() {
        if (this.destinations.size() == 0) {
            System.out.println("You have no destinations yet!");
            displayTripMenu();
        } else {
            System.out.println(newTrip.getDestinations());
        }
    }

    // EFFECTS: displays list of planned destinations to user
    public void displayPlannedList() {
        if (newTrip.getStatusDestinations(DestinationStatus.PLANNED).size() == 0) {
            System.out.println("You have no planned destinations!");
            displayDestinationsMenu();
        } else {
            System.out.println(newTrip.getStatusDestinations(DestinationStatus.PLANNED));

        }
    }

    // EFFECTS: displays list of visited destinations to user
    public void displayVisitedList() {
        if (newTrip.getStatusDestinations(DestinationStatus.VISITED).size() == 0) {
            System.out.println("You have no visited destinations!");
            displayDestinationsMenu();
        } else {
            System.out.println(newTrip.getStatusDestinations(DestinationStatus.VISITED));
        }
    }

    // EFFECTS: displays list of wishlist destinations to user
    public void displayWishlist() {
        if (newTrip.getStatusDestinations(DestinationStatus.WISHLIST).size() == 0) {
            System.out.println("You have no wishlist destinations!");
            displayDestinationsMenu();
        } else {
            System.out.println(newTrip.getStatusDestinations(DestinationStatus.WISHLIST));
        }
    }

    // EFFECTS: saves the Trip to file
    private void saveTrip() {
        try {
            jsonWriter.open();
            jsonWriter.write(newTrip);
            jsonWriter.close();
            System.out.println("Saved " + tripName + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
            System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Trip from file
    private void loadTrip() {
        try {
            newTrip = jsonReader.read();
            this.destinations = newTrip.getDestinations();
            System.out.println("Loaded " + newTrip.getTripName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            System.exit(0);
        }
    }



    ///// START PHASE 3 /////////////////////////////////////////////////////

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window
    public void initializeGraphics() {
        setLayout(new BorderLayout());
        //setMinimumSize(new Dimension(WIDTH, HEIGHT));
        //starterMenu();

        setBackground(HOT_PINK);
        getContentPane().setBackground(HOT_PINK);

        //add image to screen
        ImageIcon cherries = new ImageIcon("data/cherry-pixel-art-png.png");

        Image scaledCherries = cherries.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        ImageIcon scaledCherryIcon = new ImageIcon(scaledCherries);

        JLabel cherryImageLabel = new JLabel(scaledCherryIcon);
        getContentPane().add(cherryImageLabel);

        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        menuBar.add(optionsMenu);

        JMenu destMenu = new JMenu("Destinations");
        menuBar.add(destMenu);

        // Create destination menu items
        JMenuItem addNewDestination = new JMenuItem(new TravelPlannerApp.AddNewDestinationAction());
        //JMenuItem viewDestinations = new JMenuItem(new TravelPlannerApp.ViewDestinationAction());

        JMenuItem loadTripItem = new JMenuItem(new TravelPlannerApp.LoadTripAction());
        JMenuItem quitNoSaveItem = new JMenuItem(new TravelPlannerApp.QuitNoSaveAction());
        JMenuItem saveTripItem = new JMenuItem(new TravelPlannerApp.SaveTripAction());
        JMenuItem homeItem = new JMenuItem(new TravelPlannerApp.SaveTripAction());



        destMenu.add(addNewDestination);
        //destMenu.add(viewDestinations);

        optionsMenu.add(loadTripItem);
        optionsMenu.add(quitNoSaveItem);
        optionsMenu.add(saveTripItem);

        setJMenuBar(menuBar);
    }

    private class LoadTripAction extends AbstractAction {
        LoadTripAction() {
            super("Load Trip");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JLabel loadLabel = new JLabel("LOADED!");
            add(loadLabel);
            loadTrip();
            pack();
        }
    }

    private class SaveTripAction extends AbstractAction {
        SaveTripAction() {
            super("Save Trip");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JLabel saveLabel = new JLabel("SAVED!");
            add(saveLabel);
            saveTrip();
            pack();
        }
    }

    private class QuitNoSaveAction extends AbstractAction {
        QuitNoSaveAction() {
            super("Quit Without Saving");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JLabel quitLabel = new JLabel("GOODBYE!");
            add(quitLabel);
            System.exit(0);
            pack();
        }
    }

    private class AddNewDestinationAction extends AbstractAction {
        AddNewDestinationAction() {
            super("Add New Destination");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            JPanel destinationScreen = createDestinationScreen();
            viewDestScreen();
            createNewDestinationScreen(); ////////////
            pack();
        }
    }

    private class ViewDestinationAction extends AbstractAction {
        ViewDestinationAction() {
            super("View Destinations");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            viewDestScreen();
            pack();
        }
    }

    private class CreateDestinationAction extends AbstractAction {
        CreateDestinationAction() {
            super("Create New Destination!");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            createDestination();
            pack();
        }
    }

    private class AddActivitiesAction extends AbstractAction {
        AddActivitiesAction() {
            super("Add Activities?");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            createNewActivityScreen();
            pack();
        }
    }

    private class CreateActivityAction extends AbstractAction {
        CreateActivityAction() {
            super("Add!");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            addActivities();
            pack();
        }
    }

    private class HomeAction extends AbstractAction {
        HomeAction() {
            super("Home");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Go home");
            pack();
        }
    }


    private void viewDestScreen() {
        // Remove the current content
        contentPanel.removeAll();

        // Create a new panel for the new screen
        JPanel viewDestPanel = new JPanel();
        viewDestPanel.setBackground(LIGHT_PINK);

        // prints out the list of city names
        List<String> dests = new ArrayList<>();
        for (Destination destinations : destinations) {
            dests.add(destinations.getCity());
            for (String city : dests) {
                JLabel cityLabel = new JLabel(city);
                viewDestPanel.add(cityLabel);

            }

        }

        // Add the new panel to the content panel
        contentPanel.add(viewDestPanel);

        // Revalidate and repaint to update the display
        contentPanel.revalidate();
        contentPanel.repaint();


    }


    @SuppressWarnings("methodlength")
    private void createNewDestinationScreen() {
        // Remove the current content
        contentPanel.removeAll();

        // Create a new panel for the new screen
        JPanel newDestPanel = new JPanel();
        newDestPanel.setBackground(LIGHT_PINK);

        // Initialize components
        cityNameField = new JTextField(10);
        countryNameField = new JTextField(10);
        travelCostField = new JTextField(10);
        // ComboBox for destination status
        statusComboBox = new JComboBox<>(new String[]{"Planned", "Visited", "Wishlist"});


        // Create labels
        JLabel cityNameLabel = new JLabel("City Name:");
        JLabel countryNameLabel = new JLabel("Country Name:");
        JLabel travelCostLabel = new JLabel("Travel Cost ($):");
        JLabel statusLabel = new JLabel("Destination Status:");

        JButton addActivitiesButton = new JButton(new TravelPlannerApp.AddActivitiesAction());

        // Add components to the frame
        newDestPanel.add(cityNameLabel);
        newDestPanel.add(cityNameField);
        newDestPanel.add(countryNameLabel);
        newDestPanel.add(countryNameField);
        newDestPanel.add(travelCostLabel);
        newDestPanel.add(travelCostField);
        newDestPanel.add(addActivitiesButton);
        newDestPanel.add(statusLabel);
        newDestPanel.add(statusComboBox); // Add the JComboBox


        // Create a button to submit the form
        JButton submitNewDestinationButton = new JButton(new TravelPlannerApp.CreateDestinationAction());
        newDestPanel.add(submitNewDestinationButton);

        // Add the new panel to the content panel
        contentPanel.add(newDestPanel);

        // Revalidate and repaint to update the display
        contentPanel.revalidate();
        contentPanel.repaint();

    }

    private void createNewActivityScreen() {
        // Remove the current content
        contentPanel.removeAll();

        // Create a new panel for the new screen
        JPanel createActivityPanel = new JPanel();
        createActivityPanel.setBackground(LIGHT_PINK);

        activityNameField = new JTextField(10);
        activityCostField = new JTextField(10);

        JLabel actNameLabel = new JLabel("Activity Name:");
        JLabel actCostLabel = new JLabel("Activity Cost:");

        JButton submitNewActivityButton = new JButton(new TravelPlannerApp.CreateActivityAction());

        createActivityPanel.add(actNameLabel);
        createActivityPanel.add(activityNameField);

        createActivityPanel.add(actCostLabel);
        createActivityPanel.add(activityCostField);

        createActivityPanel.add(submitNewActivityButton);

        // Add the new panel to the content panel
        contentPanel.add(createActivityPanel);

        // Revalidate and repaint to update the display
        contentPanel.revalidate();
        contentPanel.repaint();


    }

    private void createDestination() {
        String cityName = cityNameField.getText();
        String countryName = countryNameField.getText();
        int travelCost = Integer.parseInt(travelCostField.getText());
        DestinationStatus status = (DestinationStatus) statusComboBox.getSelectedItem();
        List<Activity> activities = new ArrayList<>();   //activitiesArea.getText().split("\n");




        // Use the collected information to create a new Destination object
        Destination newDestination = new Destination(cityName, countryName, travelCost, activities,
                status);

        // Perform actions with the new destination, e.g., store it in a list, database, etc.
        System.out.println("New Destination Created: " + newDestination);

        // Optionally, close the current window after creating the destination
        dispose();
    }

    private void addActivities() {
        String activityName = activityNameField.getText();
        int activityCost = Integer.parseInt(activityCostField.getText());
        List<Activity> activities = new ArrayList<>();

        Activity newActivity = new Activity(activityName, activityCost);
        activities.add(newActivity);

        // Optionally, close the current window after creating the destination
        createNewDestinationScreen();


    }
}


