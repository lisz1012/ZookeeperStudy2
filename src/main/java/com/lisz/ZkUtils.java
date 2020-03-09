package com.lisz;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZkUtils {
    private static String address = "192.168.1.131:2181,192.168.1.132:2181,192.168.1.133:2181,192.168.1.134:2181/testLock2";
    private static DefaultWatcher watcher = new DefaultWatcher();
    private static CountDownLatch latch = new CountDownLatch(1);

    public static ZooKeeper getZk() {
        watcher.setLatch(latch);
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(address, 3000, watcher);
            latch.await();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return zk;
    }

}
