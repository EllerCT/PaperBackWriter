package managers;

import data_structures.Resource;

public class ResourceManager extends AbstractManager {

    public void add(Object object) {
        if (object instanceof Resource) {
            String key = Resource.generateKeyFor((Resource) object);
            super.map.put(key, object);
        }
    }

    public void remove(Object object) {
        if (object instanceof Resource) {
            Resource resource = (Resource) object;
            String key = Resource.generateKeyFor(resource);
            super.map.remove(key);
        }
    }
}
