package managers;

import data_structures.Product;
import io_pipes.ProductIOPipe;

import java.util.HashMap;
import java.util.Map;

public class ProductManager {

    private ProductIOPipe productIOPipe;
    private HashMap<String, Product> productMap;

    public ProductManager() {
        this.productMap = new HashMap<>();
    }

    public void fetchProducts() {
        productMap.clear();
        productMap.putAll(productIOPipe.load());
    }

    public void storeProducts() {
        productIOPipe.save(productMap);
    }

    public void setProductIOPipe(ProductIOPipe pipe) {
        this.productIOPipe = pipe;
    }

    public Map<String, Product> getProductMap() {
        return productMap;
    }

    public void setProductMap(HashMap<String, Product> map) {
        this.productMap = map;
    }

    public void newProduct(Product newProduct) {
        String key = Product.generateKeyFor(newProduct);
        if (!productMap.containsKey(key)) {
            productMap.put(key, newProduct);
        }
    }

    public void removeProduct(Product toBeRemoved) {
        String key = Product.generateKeyFor(toBeRemoved);
        productMap.remove(key);
    }

    public void updateProduct(Product toBeUpdated) {
        String key = Product.generateKeyFor(toBeUpdated);
        if (productMap.containsKey(key)) {
            productMap.put(key, toBeUpdated);
        }
    }
}
