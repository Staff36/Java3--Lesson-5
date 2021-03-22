package com.Lesson5.SupportClasses;

import lombok.Getter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;



public class ServiceLocator {
    @Getter
    public static final int CARS_COUNT = 4;
    @Getter
    private static final CountDownLatch startCountDownLatch = new CountDownLatch(CARS_COUNT);
    @Getter
    private static final CountDownLatch finishCountDownLatch = new CountDownLatch(CARS_COUNT);
    @Getter
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(CARS_COUNT);
    @Getter
    private static final Semaphore semaphore = new Semaphore(CARS_COUNT/2);
    @Getter
    private static final FinishChecker finishChecker = new FinishChecker();
    @Getter
    private static final ReentrantLock startLineLock = new ReentrantLock();


}
