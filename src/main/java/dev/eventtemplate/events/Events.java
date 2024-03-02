package dev.eventtemplate.events;

import dev.eventtemplate.*;
import dev.eventtemplate.items.*;
import dev.eventtemplate.teams.*;

import java.util.*;

public enum Events {
    EVENT0(0, "event0", "Event 1", new MapBuilder().add(Groups.TEAM, Kits.DEFAULT).build(), new Event0());
    final private int id;
    final private String name;
    final private String displayName;
    final private Map<Groups, Kits> kitsInUse;
    final private Event e;
    Events(int id, String name, String displayName, Map<Groups, Kits> kitsInUse, Event e) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.kitsInUse = kitsInUse;
        this.e = e;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Map<Groups, Kits> getKitsInUse() {
        return kitsInUse;
    }
    public Event getEvent() {
        return e;
    }
}
