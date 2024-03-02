package dev.eventtemplate;

import dev.eventtemplate.items.*;
import dev.eventtemplate.teams.*;

import java.util.*;

public class MapBuilder {
    private final Map<Groups, Kits> map;
    public MapBuilder() {
        map = new HashMap<>();
    }
    public MapBuilder add(Groups g, Kits k) {
        map.put(g, k);
        return this;
    }
    public Map<Groups, Kits> build() {
        return map;
    }

}
