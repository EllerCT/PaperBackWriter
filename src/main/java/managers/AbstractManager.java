package managers;

import io_pipes.IOPipe;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractManager {
    protected IOPipe pipe;
    protected Map<Object, Object> map;

    public AbstractManager() {
        this.map = new HashMap<>();
    }

    public void fetch() {
        this.map.putAll(pipe.load());
    }

    public void store() {
        this.pipe.save(map);
    }

    public void setIOPipe(IOPipe pipe) {
        this.pipe = pipe;
    }

    public Map<Object, Object> getMap() {
        return this.map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Object getFromKey(Object key) {
        return map.get(key);
    }

    public abstract void add(Object object);

    public abstract void remove(Object object);

}
