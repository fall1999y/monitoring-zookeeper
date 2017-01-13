package vjvj;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by fall1999y on 2017. 1. 14. 오전 4:19
 * <pre>
 * <b>Description</b>
 *
 * </pre>
 */
public class NodeMonitor implements Watcher, AsyncCallback.ChildrenCallback {

    private Logger logger = LoggerFactory.getLogger(NodeMonitor.class);

    private ZooKeeper zk;
    private NodeManager manager;

    public NodeMonitor(ZooKeeper zk, NodeManager manager) {
        this.zk = zk;
        this.manager = manager;
        logger.debug("--------- ");
    }

    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children) {
        logger.debug("result {} ", path);
        for (String c: children) {
            try {
                zk.getChildren(path + "/" + c, true, this, null);
                zk.getData(path + "/" + c, this, null);
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }

//        zk.getChildren(path, true, this, null);

    }

    @Override
    public void process(WatchedEvent event) {

        String path = event.getPath();

        logger.debug("event type {}, path {}", event.getType(), path);

        String eventType = event.getType().name();
        String eventState = event.getState().name();
        System.out.println("## process: path : "+path+", eventType : "+ eventType + ", eventState: "+ eventState);


        switch (event.getType()) {
            case NodeChildrenChanged:
                zk.getChildren(path, true, this, null);
                break;
            case NodeDataChanged:
                try {
                    byte[] b = zk.getData(path, this, null);
                    String data = new String(b);
                    boolean isTestNode = manager.isTestNode(path, data, this::selectNodeData);
//                    manager.getNode(data);

                    logger.debug("node data changed {} - is test node {} : ", data, isTestNode);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private Node selectNodeData(String path, String data) {

        Node node = manager.getNode(data);
        node.setPath(path);

        return node;
    }
}
