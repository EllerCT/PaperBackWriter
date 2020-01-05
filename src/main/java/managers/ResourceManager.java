package managers;

import data_structures.Resource;
import io_pipes.ResourceIOPipe;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private ResourceIOPipe resourceIOPipe;
    private HashMap<String, Resource> resourceMap;

    public ResourceManager() {
        this.resourceMap = new HashMap<>();
    }

    public void fetchResources() {
        resourceMap.clear();
        resourceMap.putAll(resourceIOPipe.loadResources());
    }

    public void storeResources() {
        resourceIOPipe.saveResources(resourceMap);
    }

    public void setResourceIOPipe(ResourceIOPipe pipe) {
        this.resourceIOPipe = pipe;
    }

    public Map<String, Resource> getResourceMap() {
        return this.resourceMap;
    }

    public void newResource(Resource newResource) {
        String key = Resource.generateKeyFor(newResource);
        if (!resourceMap.containsKey(key)) {
            resourceMap.put(key, newResource);
        }
    }

    public void removeResource(Resource toBeRemoved) {
        String key = Resource.generateKeyFor(toBeRemoved);
        resourceMap.remove(key);
    }

    public void updateResource(Resource toBeUpdated) {
        String key = Resource.generateKeyFor(toBeUpdated);
        if (resourceMap.containsKey(key)) {
            resourceMap.put(key, toBeUpdated);
        }
    }
}