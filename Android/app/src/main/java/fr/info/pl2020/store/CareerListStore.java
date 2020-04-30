package fr.info.pl2020.store;

import java.util.HashMap;
import java.util.Map;

import fr.info.pl2020.model.Career;

public class CareerListStore {
    public static final Map<Integer, Career> CAREERS = new HashMap<>();

    public static void clear() {
        CAREERS.clear();
    }

    public static void addItem(Career item) {
        CAREERS.put(item.getId(), item);
    }
}
