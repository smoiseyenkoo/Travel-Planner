package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ActivityTest {
    private Activity testActivity1;

    @BeforeEach
    void runBefore() {
        testActivity1 = new Activity("Surfing", 100);
    }

    @Test
    void testConstructor() {
        assertEquals("Surfing", testActivity1.getActivityName());
        assertEquals(100, testActivity1.getActivityCost());
    }
}
