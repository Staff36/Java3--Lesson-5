package com.Lesson5.Cars;

import com.Lesson5.SupportClasses.ServiceLocator;
import com.Lesson5.SupportClasses.FinishChecker;
import com.Lesson5.Stages.Race;
import lombok.Getter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Car implements Runnable {
    private static int CARS_COUNT = 0;
    private final Race race;
    @Getter
    private final int speed;
    @Getter
    private final String name;
    private final CountDownLatch startCountDownLatch;
    private final CyclicBarrier cyclicBarrier;
    private final CountDownLatch finishCountDownLatch;
    private final ReentrantLock lock = new ReentrantLock();
    private final FinishChecker finishChecker;
    private final ReentrantLock startLineLock;

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        this.cyclicBarrier = ServiceLocator.getCyclicBarrier();
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.startCountDownLatch = ServiceLocator.getStartCountDownLatch();
        this.finishCountDownLatch = ServiceLocator.getFinishCountDownLatch();
        this.finishChecker = ServiceLocator.getFinishChecker();
        this.startLineLock = ServiceLocator.getStartLineLock();
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            cyclicBarrier.await(1, TimeUnit.HOURS);
            System.out.println(this.name + " готов");
            startCountDownLatch.countDown();
            while (startLineLock.isLocked());
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }
            lock.lock();
            if (!finishChecker.isWasFinished()){
                finishChecker.setWasFinished(true);
                System.out.println(this.name + " ПОБЕДИЛ!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            finishCountDownLatch.countDown();
        }


    }
}
