package com.timas.projects.lifecycle.events;

import com.timas.projects.repository.WorldModifier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@RequiredArgsConstructor
@Getter
public class EventCheckEnd extends Event implements EventOfTheWorld<Boolean>{
    final WorldModifier worldModifier;

    @Override
    public Boolean event() {

        return false;

    }
}