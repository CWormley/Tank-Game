package tankrotationexample.game;

import tankrotationexample.ResourceManager;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

// ResourcePool class for the object pool
public class ResourcePool <G extends Poolable>{
   private final int INIT_CAPACITY = 100;
    private final String resourceName;
    private final Class<G> resourceClass;
    private final ArrayList<G> resources;

    // Constructor for the ResourcePool class
     public ResourcePool(String resourceName, Class<G> resourceClass) {
         this.resourceName = resourceName;
         this.resourceClass = resourceClass;
         this.resources = new ArrayList<>(INIT_CAPACITY);
     }
     // Constructor for the ResourcePool class
    public ResourcePool(String resourceName, Class<G> resourceClass, int initCapacity) {
        this.resourceName = resourceName;
        this.resourceClass = resourceClass;
        this.resources = new ArrayList<>(initCapacity);
    }

    // Method to remove an object from the pool
    public G remove(){
         if(this.resources.size() == 0) {this.refillPool();}
         return this.resources.remove(this.resources.size()-1);
    }

    public void add(G obj){
         this.resources.add(obj);
    }

     private void refillPool(){
        this.fillPool(INIT_CAPACITY);
     }

     // Method to fill the pool with objects
    public ResourcePool<G> fillPool(int size) {
        BufferedImage img = ResourceManager.getSprite(this.resourceName);
        for (int i = 0; i < size; i++) {
            try {
                var g = this.resourceClass.getDeclaredConstructor(BufferedImage.class).newInstance(img);
                this.resources.add(g);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
         return this;
    }

}
