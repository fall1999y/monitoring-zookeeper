package vjvj;

import java.util.function.BiFunction;

/**
 * Created by fall1999y on 2017. 1. 14. 오전 4:32
 * <pre>
 * <b>Description</b>
 *
 * </pre>
 */
public class TestNodeManager implements NodeManager {

    private static TestNodeManager instance;

    private TestNodeManager() {
    }

    @Override
    public TestNode getNode(String data) {

        TestNode node = new TestNode();
//        node.setPath(path);
        node.setData(data);
        node.setTest(true);

        return node;
    }

    @Override
    public boolean isTestNode(String path, String data, BiFunction<String, String, ? extends Node> f) {

        TestNode n = (TestNode) f.apply(path, data);

        return n.isTest();
    }

    public static NodeManager getInstacne() {
        if(instance == null) {
            instance = new TestNodeManager();
        }

        return instance;
    }
}
