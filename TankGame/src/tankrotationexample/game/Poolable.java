package tankrotationexample.game;

// Poolable interface for the object pool
// Structures objects of the resource pool

public interface Poolable {
    void initObject(float x, float y);
    void initObject(float x, float y, float angel, int tankID);
    void resetObject();

}