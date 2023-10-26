package ui;

import model.Activity;
import model.Destination;
import model.DestinationStatus;
import model.Trip;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;  // Import the Scanner class

// Code sourced from JsonSerializationDemo

// Runs the Travel Planner app
public class TravelPlannerApp {
    private static final String JSON_STORE = "./data/trip.json";
    Scanner input;
     //////////////////////////////////////////////////////
    Trip newTrip;
    private List<Destination> destinations = new ArrayList<>();
    private Destination newDestination;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static String tripName;

    // EFFECTS: creates instance of the Travel Planner app
    public static void main(String[] args) {
        new TravelPlannerApp();

    }

    // EFFECTS: constructs New trip and runs Travel planner application
    public TravelPlannerApp() {
        input = new Scanner(System.in);  // Create a Scanner object
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        tripName = "Your Trip";
        newTrip = new Trip(tripName, destinations);
        run();

    }

    // MODIFIES: this
    // EFFECTS: runs app and displays trip main menu
    public void run() {
        boolean keepGoing = true;
        System.out.println("Welcome to " + tripName + "!");  // Output user input

        // String command = null;
//        System.out.println("Enter new trip name!");
//        tripName = input.nextLine();  // Read user input
//        System.out.println("Trip Name is: " + tripName);  // Output user input



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

}


