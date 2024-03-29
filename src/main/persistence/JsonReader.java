package persistence;

import model.Activity;
import model.Destination;
import model.DestinationStatus;
import model.Trip;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;
// Code sourced from JsonSerializationDemo

// Represents a reader that reads trip from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads trip from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Trip read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTrip(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Trip from JSON object and returns it
    private Trip parseTrip(JSONObject jsonObject) {
        String name = jsonObject.getString("tripName");
        List<Destination> destinations = new ArrayList<>();
        Trip trip = new Trip(name, destinations);
        addDestinations(trip, jsonObject);
        return trip;
    }


    // MODIFIES: this
    // EFFECTS: parses activities from JSON object and creates a List of activities
    private List<Activity> createListActivity(JSONArray jsonArray) {
        List<Activity> activities =  new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject nextActivity = (JSONObject) json;
            activities.add(createActivity(nextActivity));
        }
        return activities;
    }

    // MODIFIES: this
    // EFFECTS: parses an activity from JSON object and creates an activity
    private Activity createActivity(JSONObject jsonObject) {
        String name = jsonObject.getString("activityName");
        int cost = jsonObject.getInt("activityCost");
        Activity activity = new Activity(name, cost);
        return activity;
    }

    // MODIFIES: trip
    // EFFECTS: parses destinations from JSON object and adds them to Trip
    private void addDestinations(Trip trip, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("destinations");
        for (Object json : jsonArray) {
            JSONObject nextDestination = (JSONObject) json;
            addDestination(trip, nextDestination);
        }
    }

    // MODIFIES: trip
    // EFFECTS: parses Destination from JSON object and adds it to Trip
    private void addDestination(Trip trip, JSONObject jsonObject) {
        String city = jsonObject.getString("city");
        String country = jsonObject.getString("country");
        int travelCost = jsonObject.getInt("travelCost");

        List<Activity> activities = createListActivity(jsonObject.getJSONArray("activities"));
        DestinationStatus destinationStatus = DestinationStatus.valueOf(jsonObject.getString("destinationStatus"));
        Destination destination = new Destination(city, country, travelCost, activities, destinationStatus);
        trip.addDestination(destination);
    }
}


