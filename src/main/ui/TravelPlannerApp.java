package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.Event;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;  // Import the Scanner class
import java.awt.event.ActionEvent;
import model.EventLog;


// Code sourced from JsonSerializationDemo, AlarmSystem,
// https://medium.com/@michael71314/java-lesson-22-inserting-images-onto-the-jframe-a0a0b6540cca

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
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private JTextField cityNameField;
    private JTextField countryNameField;
    private JTextField travelCostField;
    private JTextField activityNameField;
    private JTextField activityCostField;
    private JTextField idField;
    private JComboBox<DestinationStatus> statusComboBox;
    private JComboBox<DestinationStatus> statusMenu;
    private Destination selectedDest;
    private List<Activity> activities;

    private JPanel contentPanel;
    private static final Color LIGHT_PINK = new Color(252, 215, 237);
    private static final Color HOT_PINK = new Color(255, 56, 116);


    // EFFECTS: creates instance of the Travel Planner app
    public static void main(String[] args) {
        new TravelPlannerApp();

    }

    // EFFECTS: constructs New trip and runs Travel planner application
    public TravelPlannerApp() {
        super("Travel Planner!");
        input = new Scanner(System.in);  // Create a Scanner object
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        tripName = "Your Trip";
        newTrip = new Trip(tripName, destinations);
        //run();
        homeScreen();
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


    ///// START PHASE 3 ///////////////////////////////////////////////////////////////////////////////////////////////

    // MODIFIES: this
    // EFFECTS:  draws the main JFrame window
    public void initializeGraphics() {
        setLayout(new BorderLayout());
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

    // EFFECTS: adds the menu bar with the options and destinations tabs
    // MODIFIES: this
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        menuBar.add(optionsMenu);

        JMenu destMenu = new JMenu("Destinations");
        menuBar.add(destMenu);

        //create destinations menu item
        JMenuItem addNewDestinationItem = new JMenuItem(new TravelPlannerApp.AddNewDestinationAction());
        JMenuItem changeStatusItem = new JMenuItem(new TravelPlannerApp.ChangeStatusAction());
        JMenuItem viewAllDestsItem = new JMenuItem(new TravelPlannerApp.ViewAllDestinationAction());


        //create options menu item
        JMenuItem loadTripItem = new JMenuItem(new TravelPlannerApp.LoadTripAction());
        JMenuItem quitNoSaveItem = new JMenuItem(new TravelPlannerApp.QuitNoSaveAction());
        JMenuItem saveTripItem = new JMenuItem(new TravelPlannerApp.SaveTripAction());
        JMenuItem homeItem = new JMenuItem(new TravelPlannerApp.HomeAction());

        destMenu.add(addNewDestinationItem);
        destMenu.add(changeStatusItem);
        destMenu.add(viewAllDestsItem);

        optionsMenu.add(loadTripItem);
        optionsMenu.add(quitNoSaveItem);
        optionsMenu.add(saveTripItem);
        optionsMenu.add(homeItem);

        setJMenuBar(menuBar);
    }

    // Action class for when the user clicks the Load button, to load the trip from  Json
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

    // Action class for when the user clicks the Save and quit button, to save the trip to Json and quit the app,
    // prints event log when quitting
    private class SaveTripAction extends AbstractAction {
        SaveTripAction() {
            super("Save And Quit");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JLabel saveLabel = new JLabel("SAVED!");
            add(saveLabel);
            saveTrip();
            printLog(EventLog.getInstance());
            System.exit(0);
            pack();
        }
    }

    // Action class for when the user clicks the Quit without saving button, to quit the app, prints event log when
    // quitting
    private class QuitNoSaveAction extends AbstractAction {
        QuitNoSaveAction() {
            super("Quit Without Saving");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JLabel quitLabel = new JLabel("GOODBYE!");
            add(quitLabel);
            printLog(EventLog.getInstance());
            System.exit(0);
            pack();
        }
    }

    // Action class for when the user clicks the Add New Destination menu item, to add a new destination
    private class AddNewDestinationAction extends AbstractAction {
        AddNewDestinationAction() {
            super("Add New Destination");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            viewPlannedDestScreen();
            createNewDestinationScreen();
            pack();
        }
    }

    // Action class for when the user clicks the Change Status Of A Destination menu item, to change the status
    private class ChangeStatusAction extends AbstractAction {
        ChangeStatusAction() {
            super("Change Status Of A Destination");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            changeStatusScreen();
            pack();
        }
    }

    // Action class for when the user types in an ID number and presses the select destination button
    private class SelectIDAction extends AbstractAction {
        SelectIDAction() {
            super("Select Destination by ID");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            changeSelectedStatusScreen();
            pack();
        }
    }

    // Action class for when the user clicks the update status button, which updates the status to the selected
    // MODIFIES: this
    private class SubmitStatusAction extends AbstractAction {
        SubmitStatusAction() {
            super("Update Status");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DestinationStatus newStatus = (DestinationStatus) statusMenu.getSelectedItem();
            selectedDest.setDestinationStatus(newStatus);
            homeScreen();
            pack();
        }
    }

    // Action class for when the user clicks the View Planned Destinations button
    private class ViewPlannedDestinationAction extends AbstractAction {
        ViewPlannedDestinationAction() {
            super("View Planned Destinations");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            viewPlannedDestScreen();
            pack();
        }
    }

    // Action class for when the user clicks the View Visited Destinations button
    private class ViewVisitedDestinationAction extends AbstractAction {
        ViewVisitedDestinationAction() {
            super("View Visited Destinations");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            viewVisitedDestScreen();
            pack();
        }
    }

    // Action class for when the user clicks the View Wishlist Destinations button
    private class ViewWishlistDestinationAction extends AbstractAction {
        ViewWishlistDestinationAction() {
            super("View Wishlist Destinations");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            viewWishlistDestScreen();
            pack();
        }
    }

    // Action class for when the user clicks the View All Destinations menu item to view every destination in the trip
    private class ViewAllDestinationAction extends AbstractAction {
        ViewAllDestinationAction() {
            super("View All Destinations");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            viewAllDestScreen();
            pack();
        }
    }

    // Action class for when the user clicks the Create New Destination! menu item
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

    // Action class for when the user clicks the Add Activities? button
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

    // Action class for when the user clicks the Add! activity button, and adds the activity to activities
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

    // Action class for when the user clicks the Home screen menu item
    private class HomeAction extends AbstractAction {
        HomeAction() {
            super("Home");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            homeScreen();
            pack();
        }
    }

    // EFFECTS: sets the default size and exit operations on the screen
    private void screenDefaults() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    // EFFECTS: creates the home screen with graphics
    // MODIFIES: this
    private void homeScreen() {
        screenDefaults();
        //create main home screen content panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout());
        contentPanel.setBorder(new EmptyBorder(13, 13, 13, 13));
        setContentPane(contentPanel);

        JButton loadButton = new JButton(new TravelPlannerApp.LoadTripAction());
        JButton displayPlannedButton = new JButton(new ViewPlannedDestinationAction());
        JButton displayVisitedButton = new JButton(new ViewVisitedDestinationAction());
        JButton displayWishlistButton = new JButton(new ViewWishlistDestinationAction());


        add(loadButton);
        add(displayPlannedButton);
        add(displayVisitedButton);
        add(displayWishlistButton);

        JLabel homeDescription = new JLabel("Welcome to your Travel Planner!");
        homeDescription.setHorizontalAlignment(JLabel.CENTER); // Center the text horizontally
        add(homeDescription);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        addMenu();
        initializeGraphics();
        setVisible(true);
    }


    // EFFECTS: creates a new screen to print out the list of planned destinations
    // MODIFIES: this
    private void viewPlannedDestScreen() {
        contentPanel.removeAll();

        //create new screen
        JPanel viewDestPanel = new JPanel();
        viewDestPanel.setBackground(LIGHT_PINK);

        //prints out the list of city and country names
        List<Destination> plannedDests = new ArrayList<>();
        for (Destination destinations : newTrip.getDestinations()) {
            if (newTrip.getStatusDestinations(DestinationStatus.PLANNED).size() == 0) {
                System.out.println("No planned destinations yet!");


            } else {
                if (destinations.getDestinationStatus() == DestinationStatus.PLANNED) {
                    plannedDests.add(destinations);
                }
            }
        }

        for (Destination d : plannedDests) {
            JLabel destinationLabel = new JLabel("(" + d.getCity() + ", " + d.getCountry() + ")");
            viewDestPanel.add(destinationLabel);
        }

        contentPanel.add(viewDestPanel);

        updateDisplay();

    }

    // EFFECTS: creates a new screen to print out the list of visited destinations
    // MODIFIES: this
    private void viewVisitedDestScreen() {
        contentPanel.removeAll();

        //create new screen
        JPanel viewDestPanel = new JPanel();
        viewDestPanel.setBackground(LIGHT_PINK);

        //prints out the list of city and country names
        List<Destination> visitedDests = new ArrayList<>();
        for (Destination destinations : newTrip.getDestinations()) {
            if (newTrip.getStatusDestinations(DestinationStatus.VISITED).size() == 0) {
                System.out.println("none");
            } else {
                if (destinations.getDestinationStatus() == DestinationStatus.VISITED) {
                    visitedDests.add(destinations);
                }
            }
        }

        for (Destination d : visitedDests) {
            JLabel destinationLabel = new JLabel("(" + d.getCity() + ", " + d.getCountry() + ")");
            viewDestPanel.add(destinationLabel);
        }

        contentPanel.add(viewDestPanel);

        updateDisplay();

    }

    // EFFECTS: creates a new screen to print out the list of wishlist destinations
    // MODIFIES: this
    private void viewWishlistDestScreen() {
        contentPanel.removeAll();

        //create new screen
        JPanel viewDestPanel = new JPanel();
        viewDestPanel.setBackground(LIGHT_PINK);

        //prints out the list of city and country names
        List<Destination> wishlistDests = new ArrayList<>();
        for (Destination destinations : newTrip.getDestinations()) {
            if (newTrip.getStatusDestinations(DestinationStatus.WISHLIST).size() == 0) {
                System.out.println("none");
            } else {
                if (destinations.getDestinationStatus() == DestinationStatus.WISHLIST) {
                    wishlistDests.add(destinations);
                }
            }
        }

        for (Destination d : wishlistDests) {
            JLabel destinationLabel = new JLabel("(" + d.getCity() + ", " + d.getCountry() + ")");
            viewDestPanel.add(destinationLabel);
        }

        contentPanel.add(viewDestPanel);
        updateDisplay();

    }

    // EFFECTS: creates a new screen to print out the list of all destinations
    // MODIFIES: this
    private void viewAllDestScreen() {
        contentPanel.removeAll();

        //create new screen
        JPanel viewDestPanel = new JPanel();
        viewDestPanel.setBackground(LIGHT_PINK);

        for (Destination d : destinations) {
            JLabel destinationLabel = new JLabel("(" + d.getCity() + ", " + d.getCountry() + ")");
            viewDestPanel.add(destinationLabel);
        }

        contentPanel.add(viewDestPanel);
        updateDisplay();

    }

    // EFFECTS: updates the display to the next screen
    // MODIFIES: this
    private void updateDisplay() {
        contentPanel.revalidate();
        contentPanel.repaint();
    }


    // EFFECTS: creates the new destination screen
    // MODIFIES: this
    private void createNewDestinationScreen() {
        contentPanel.removeAll();
        createNewDestScreenComponents();
        updateDisplay();

    }

    // EFFECTS: creates the new destination screen components and adds them to the display
    // MODIFIES: this
    @SuppressWarnings("methodlength") //Signed by Michelle
    private void createNewDestScreenComponents() {
        //create labels
        JLabel cityNameLabel = new JLabel("City Name:");
        JLabel countryNameLabel = new JLabel("Country Name:");
        JLabel travelCostLabel = new JLabel("Travel Cost ($):");
        JLabel statusLabel = new JLabel("Destination Status:");

        //create new screen
        JPanel newDestPanel = new JPanel();
        newDestPanel.setBackground(LIGHT_PINK);

        //initialize components
        cityNameField = new JTextField(10);
        countryNameField = new JTextField(10);
        travelCostField = new JTextField(10);
        statusComboBox = new JComboBox<>(DestinationStatus.values());

        JButton addActivitiesButton = new JButton(new TravelPlannerApp.AddActivitiesAction());

        //add components
        newDestPanel.add(cityNameLabel);
        newDestPanel.add(cityNameField);
        newDestPanel.add(countryNameLabel);
        newDestPanel.add(countryNameField);
        newDestPanel.add(travelCostLabel);
        newDestPanel.add(travelCostField);
        newDestPanel.add(addActivitiesButton);
        newDestPanel.add(statusLabel);
        newDestPanel.add(statusComboBox);

        //button to submit new destination to trip
        JButton submitNewDestinationButton = new JButton(new TravelPlannerApp.CreateDestinationAction());
        newDestPanel.add(submitNewDestinationButton);

        if (newDestination == null) {
            List<Activity> activities = new ArrayList<>();
            newDestination = new Destination(activities);
        }

        contentPanel.add(newDestPanel);
    }

    // EFFECTS: creates the new activity screen with all the user input materials
    // MODIFIES: this
    private void createNewActivityScreen() {
        contentPanel.removeAll();

        //new screen
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

        //add new panel to the content panel
        contentPanel.add(createActivityPanel);

        updateDisplay();

    }

    // EFFECTS: creates the new destination from the user input fields and adds it to the trip
    // MODIFIES: this
    private void createDestination() {
        String cityName = cityNameField.getText();
        String countryName = countryNameField.getText();
        int travelCost = Integer.parseInt(travelCostField.getText());
        DestinationStatus status = (DestinationStatus) statusComboBox.getSelectedItem();

        newDestination = new Destination(cityName, countryName, travelCost, activities, status);
        newTrip.addDestination(newDestination);
        homeScreen();
    }

    // EFFECTS: creates the new activity from the user input fields and adds it to the destination
    // MODIFIES: this
    private void addActivities() {
        String activityName = activityNameField.getText();
        int activityCost = Integer.parseInt(activityCostField.getText());
        Activity newActivity = new Activity(activityName, activityCost);
        newDestination.addActivity(newActivity);
        createNewDestinationScreen();

    }

    // EFFECTS: creates change status screen with all the user input materials
    // MODIFIES: this
    private void changeStatusScreen() {
        contentPanel.removeAll();

        //new screen
        JPanel changeStatusPanel = new JPanel();
        changeStatusPanel.setBackground(LIGHT_PINK);

        //make the destination into a JLabels with a city and ID
        for (Destination destination : destinations) {
            JLabel destinationLabel = new JLabel(destination.getCity() + ", " + destination.getId());
            changeStatusPanel.add(destinationLabel);
        }

        idField = new JTextField(10);

        JLabel idLabel = new JLabel("Enter Destination ID: ");

        JButton selectDestButton = new JButton(new SelectIDAction());

        changeStatusPanel.add(idLabel);
        changeStatusPanel.add(idField);

        changeStatusPanel.add(selectDestButton);

        //add new panel to the content panel
        contentPanel.add(changeStatusPanel);

        updateDisplay();

    }

    // EFFECTS: creates the update status screen with all the user input materials to update the destination's status
    // MODIFIES: this
    private void changeSelectedStatusScreen() {
        // Remove the current content
        contentPanel.removeAll();
        selectedDest = null;
        statusMenu = new JComboBox<>(DestinationStatus.values());
        JLabel statusLabel = new JLabel("Destination Status:");
        JButton submitStatusButton = new JButton(new SubmitStatusAction());

        //new screen
        JPanel changeSelectedStatusPanel = new JPanel();
        changeSelectedStatusPanel.setBackground(LIGHT_PINK);

        int selectedId = Integer.parseInt(idField.getText());

        for (Destination selected : destinations) {
            if (selected.getId() == selectedId) {
                selectedDest = selected;
            }
        }

        JLabel selectedDestLabel = new JLabel(selectedDest.getCity() + ", " + selectedDest.getDestinationStatus());
        changeSelectedStatusPanel.add(selectedDestLabel);
        changeSelectedStatusPanel.add(statusLabel);
        changeSelectedStatusPanel.add(statusMenu);
        changeSelectedStatusPanel.add(submitStatusButton);

        //add new panel to the content panel
        contentPanel.add(changeSelectedStatusPanel);

        updateDisplay();

    }

    // EFFECTS: prints out every Event that was logged during the running of the program
    public void printLog(EventLog el) {
        for (model.Event next : el) {
            System.out.println(next);
        }
    }


}
