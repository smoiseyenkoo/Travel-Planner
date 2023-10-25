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

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
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
        String name = jsonObject.getString("name");
        List<Destination> destinations = new ArrayList<>();
        Trip trip = new Trip(name, destinations);
        addDestinations(trip, jsonObject);
        return trip;
    }

    // MODIFIES: ds
    // EFFECTS: parses thingies from JSON object and adds them to Trip
    private void addDestinations(Trip trip, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("destinations");
        for (Object json : jsonArray) {
            JSONObject nextDestination = (JSONObject) json;
            addDestination(trip, nextDestination);
        }
    }

    // MODIFIES: ds
    // EFFECTS: parses thingies from JSON object and adds them to Trip
    private List<Activity> addActivities(JSONArray jsonArray) {
        List<Activity> activities =  new ArrayList<>();
        //JSONArray jsonArray = jsonObject.getJSONArray("activities");
        for (Object json : jsonArray) {
            JSONObject nextActivity = (JSONObject) json;
            activities.add(addActivity(nextActivity));
        }
        return activities;
    }

    // MODIFIES: ds
    // EFFECTS: parses Destination from JSON object and adds it to Trip
    private Activity addActivity(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int cost = jsonObject.getInt("cost");
        Activity activity = new Activity(name, cost);
        return activity;
    }


    // MODIFIES: ds
    // EFFECTS: parses Destination from JSON object and adds it to Trip
    private void addDestination(Trip trip, JSONObject jsonObject) {
        String city = jsonObject.getString("city");
        String country = jsonObject.getString("country");
        int travelCost = jsonObject.getInt("travelCost");
        List<Activity> activities = addActivities(jsonObject.getJSONArray("activities"));
        DestinationStatus destinationStatus = DestinationStatus.valueOf(jsonObject.getString("status"));
        Destination destination = new Destination(city, country, travelCost, activities, destinationStatus);
        trip.addDestination(destination);
    }
}


