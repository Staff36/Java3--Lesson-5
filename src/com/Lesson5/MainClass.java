package com.Lesson5;

import com.Lesson5.Cars.Car;
import com.Lesson5.Stages.Race;
import com.Lesson5.Stages.Road;
import com.Lesson5.Stages.Tunnel;
import com.Lesson5.SupportClasses.ServiceLocator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class MainClass {
    public static final int CARS_COUNT = ServiceLocator.getCARS_COUNT();
    private static final CountDownLatch startCountDownLatch = ServiceLocator.getStartCountDownLatch();
    private static final CountDownLatch finishCountDownLatch = ServiceLocator.getFinishCountDownLatch();
    private static final ReentrantLock startLineLock = ServiceLocator.getStartLineLock();

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }

        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        try {
            startLineLock.lock();
            startCountDownLatch.await(1, TimeUnit.HOURS);
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            startLineLock.unlock();
            finishCountDownLatch.await(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}

