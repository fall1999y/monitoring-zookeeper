package vjvj;

import java.util.function.BiFunction;

/**
 * Created by fall1999y on 2017. 1. 14. 오전 4:32
 * <pre>
 * <b>Description</b>
 *
 * </pre>
 */
public interface NodeManager {
    Node getNode(String data);

    boolean isTestNode(String path, String data, BiFunction<String, String, ? extends Node> f);
}
