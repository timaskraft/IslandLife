package com.timas.projects.services.entity;

import java.util.Collection;

public class MoveService {

    public <T> boolean move(T who, Collection<? extends T> from, Collection<T> to) throws CloneNotSupportedException {

        if (from.contains(who) && !to.contains(who)) {
            from.remove(who);
            to.add(who);
            return true;
        }

        return false;
    }
}
