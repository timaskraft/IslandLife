package com.timas.projects.services.random;


import java.util.Random;
public class RandomService {
    private static final Random SHARED_RANDOM = new Random();
    private final Random random;

    /**
     * Uses a default shared random.
     */
    public RandomService() {
        this(SHARED_RANDOM);
    }

    public RandomService(Random random) {
        this.random = random != null ? random : SHARED_RANDOM;
    }

    public int nextInt(int n) {
        return random.nextInt(n);
    }


    public Boolean nextBoolean() {
        return random.nextBoolean();
    }

    public Integer nextInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public boolean takeChance(int max_chance)
    {
          int chance = nextInt(0,100);

          return (chance<=max_chance);
    }
}