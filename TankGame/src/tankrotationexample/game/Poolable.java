package tankrotationexample.game;

public interface Poolable {
    void initObject(float x, float y);
    void initObject(float x, float y, float angel);
    void resetObject();

}