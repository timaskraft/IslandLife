package com.timas.projects.lifecycle.tasks;

import lombok.Getter;


abstract public class LifeTask implements Runnable{
    long count_tick = 0L;
}
