package ui;

import model.Activity;
import model.Destination;
import model.DestinationStatus;
import model.Trip;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;  // Import the Scanner class

public class TravelPlannerApp {
    Scanner input;
    private List<Destination> destinationsList = new ArrayList<>();
    private String tripName;
    Trip newTrip;
    private Destination newDestination;
    private Activity activity;


    public static void main(String[] args) {
        new TravelPlannerApp().run();

    }

    public TravelPlannerApp() {
        input = new Scanner(System.in);  // Create a Scanner object

    }

    public void run() {
        boolean keepGoing = true;
        String command = null;
        System.out.println("Enter new trip name!");
        tripName = input.nextLine();  // Read user input
        System.out.println("Trip Name is: " + tripName);  // Output user input
        newTrip = new Trip(tripName, destinationsList);


        while (keepGoing) {
            displayTripMenu();
        }

        System.out.println("\nGoodbye!");
    }


    // EFFECTS: displays menu of options to user
    public void displayTripMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. View Destination Menu");
        System.out.println("2. Display Destinations");
        System.out.println("3. Quit");
        System.out.print("Please enter your choice (1/2/3): ");
        int menuValue = input.nextInt();  // Read user input
        switch (menuValue) {
            case 1:
                displayDestinationsMenu();
                break;
            case 2:
                displayDestinations();
                break;
            case 3:
                System.out.println("goodbye!");
                System.exit(0);
            default:
                System.out.println("invalid entry");


        }
    }

    public void displayDestinations() {
        if (this.destinationsList.size() == 0) {
            System.out.println("You have no destinations yet!");
            displayTripMenu();
        } else {
            System.out.println(destinationsList);
        }
    }

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


    public void selectDestination(int selectId) {
        Destination selectedDestination = destinationsList.stream().filter(
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
                activity = createActivity();
                activities.add(activity);
                input.nextLine();
                System.out.println("Add activities? (yes/no): ");
                inputAddActivity = input.nextLine();  // Read user input
                if (!"yes".equalsIgnoreCase(inputAddActivity)) break;
            } while (true);


        } else {
            activities = new ArrayList<Activity>();
        }
        newDestination = new Destination(cityName, countryName, travelCost, activities, destinationStatus);
        return newDestination;
    }

    public Activity createActivity() {
        System.out.println("Please Enter Activity Name: ");
        String activityName = input.nextLine();  // Read user input
        System.out.println("Please Enter Activity Cost: $");
        int activityCost = input.nextInt();
        return new Activity(activityName, activityCost);


    }

    public void selectDestinationMenu() {
        System.out.println("\nSelected Destination: ");
        System.out.println("1. Display Destination Info");
        System.out.println("2. View Activities List");
        System.out.println("3. Add new Activity");
        System.out.println("4. Change Status of Destination(Planned/Visited/Wishlist");
        System.out.println("5. Back to Main Menu");
        System.out.print("Please enter your choice (1/2/3/4/5): ");
    }

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

    public void displayPlannedList() {
        if (newTrip.getStatusDestinations(DestinationStatus.PLANNED).size() == 0) {
            System.out.println("You have no planned destinations!");
            displayDestinationsMenu();
        } else {
            System.out.println(newTrip.getStatusDestinations(DestinationStatus.PLANNED));

        }
    }

    public void displayVisitedList() {
        if (newTrip.getStatusDestinations(DestinationStatus.VISITED).size() == 0) {
            System.out.println("You have no visited destinations!");
            displayDestinationsMenu();
        } else {
            System.out.println(newTrip.getStatusDestinations(DestinationStatus.VISITED));
        }
    }

    public void displayWishlist() {
        if (newTrip.getStatusDestinations(DestinationStatus.WISHLIST).size() == 0) {
            System.out.println("You have no wishlist destinations!");
            displayDestinationsMenu();
        } else {
            System.out.println(newTrip.getStatusDestinations(DestinationStatus.WISHLIST));
        }
    }

}


