package com.timas.projects.lifecycle.events;

import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public abstract class Event {
    int chanceEvent = 100;
}
