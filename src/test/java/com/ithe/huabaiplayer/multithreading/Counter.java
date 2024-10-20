package com.ithe.huabaiplayer.multithreading;

public class Counter {
//    private final StampedLock stampedLock = new StampedLock();

        private int count = 0;
//    AtomicInteger count = new AtomicInteger(0);

    public void add(int n) {
        synchronized (this){
            count += n;
        }
//        long stamp = stampedLock.writeLock(); // 获取写锁
//        try {
//            count += n;
//        } finally {
//            stampedLock.unlockWrite(stamp); // 释放写锁
//        }
//        count.addAndGet(n);
    }



    public int get() {

//        return count.get();
        return count;
    }

    public void clear() {
//        count.set(0);
        count = 0;
    }
}