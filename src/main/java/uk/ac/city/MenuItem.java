package uk.ac.city;

public class MenuItem {
    private final String name;
    private final boolean available;

    public MenuItem(String name, boolean available) {
        this.name = name;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }
}
