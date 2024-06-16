package com.timas.projects.services.random;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@RequiredArgsConstructor
public class RandomGeneratorService {
    private final RandomService randomService;

    private static final int GENERATE_FACTOR = 10; /* 0 - 100 */

    public boolean check()
    {
        return check(GENERATE_FACTOR);
    }
    public boolean check(int generate_factor)
    {
        int factor = generate_factor<0? 0: Math.min(generate_factor, 100);

        int r = randomService.nextInt(0,100);

        return (r<=factor);
    }

    public int get(int max_value)
    {
        return randomService.nextInt(0,max_value);
    }




}
