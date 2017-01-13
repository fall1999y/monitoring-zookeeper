package vjvj;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Created by fall1999y on 2017. 1. 14. 오전 4:14
 * <pre>
 * <b>Description</b>
 *
 * </pre>
 */
public class Executor implements Runnable {

    ZooKeeper zk;
    public Executor() throws IOException {
        zk = new ZooKeeper("127.0.0.1:2181", 3000, null);
        Watcher w = new NodeMonitor(zk, TestNodeManager.getInstacne());
        zk.register(w);

        zk.getChildren("/a", true, (AsyncCallback.ChildrenCallback) w, null);
    }

    public static void main(String[] args) throws IOException {

        Executor exec = new Executor();
        exec.run();

    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                while (true) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
        }

    }
}
