package game;

import java.io.Serializable;

public class Test implements Serializable { // REMOVE THIS CLASS
    private String name = "Alex";
    private int age = 50;

    @Override
    public String toString() {
        return name;
    }
}