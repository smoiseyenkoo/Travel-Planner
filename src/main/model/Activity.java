package model;

public class Activity {
    private String name;
    private int cost;

    // REQUIRES: cost >= 0
    // MODIFIES: this
    // EFFECTS: creates a new activity with a name and a cost
    public Activity(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public int getActivityCost() {
        return this.cost;
    }

    public String getActivityName() {
        return this.name;
    }
}
