package model;

import org.json.JSONObject;
import persistence.Writable;

// represents an Activity
public class Activity implements Writable {
    private final String name;
    private final int cost;

    // REQUIRES: cost >= 0
    // MODIFIES: this
    // EFFECTS: creates a new activity with a name and a cost
    public Activity(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    // EFFECTS: returns cost of the activity in dollars
    public int getActivityCost() {
        return this.cost;
    }

    // EFFECTS: returns name of the activity
    public String getActivityName() {
        return this.name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("cost", cost);
        return json;
    }
}
