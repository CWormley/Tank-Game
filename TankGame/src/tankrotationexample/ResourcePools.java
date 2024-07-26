package tankrotationexample;

import tankrotationexample.game.Poolable;
import tankrotationexample.game.ResourcePool;

import java.util.HashMap;
import java.util.Map;


//ResourcePool is a generic class that is used to pool objects that implement the Poolable interface
//Used for Bullet objects to make loading and unloading of bullets more efficient

public class ResourcePools {
    private static Map<String, ResourcePool<? extends Poolable>> pools = new HashMap<>();

    public static void addPool(String key, ResourcePool<? extends Poolable> pool) {
        ResourcePools.pools.put(key, pool);
    }

    public static Poolable getPooledInstance(String key) {
        return ResourcePools.pools.get(key).remove();
    }

    /*
    public static void returnPooledInstance(String key, Poolable p ) {
        ResourcePools.pools.get(key).add(p);
    }

     */
}
