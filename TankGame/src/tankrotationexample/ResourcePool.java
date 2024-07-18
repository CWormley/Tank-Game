package tankrotationexample;

import java.util.ArrayList;

public class ResourcePool <P>{
    ArrayList<P> pool = new ArrayList<>();

    public P getInstance(){
        return this.pool.remove(this.pool.size()-1);
    }

    public void addInstance(P p){
        this.pool.add(p);
    }
}
