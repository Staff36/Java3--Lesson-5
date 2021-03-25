package com.Lesson5.Stages;

import com.Lesson5.Cars.Car;

public abstract class Stage {
    protected int length;
    protected String description;

    public String getDescription() {
        return description;
    }

    public abstract void go(Car c);
}
