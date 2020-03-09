package com.lisz;

import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TestLock {
    private ZooKeeper zk;

    @Before
    public void connect() {
        zk = ZkUtils.getZk();
    }

    @After
    public void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLock() {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                WatcherCallback watcherCallback = new WatcherCallback(zk);
                watcherCallback.setThreadName(Thread.currentThread().getName());
                watcherCallback.tryLock();
                System.out.println("Working ...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                watcherCallback.unLock();
            }).start();
        }

        while (true) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
