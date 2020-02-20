package managers;

import data_structures.ModularProduct;

public class ModularProductManager extends AbstractManager {
    @Override
    public void add(Object object) {
        if (object instanceof ModularProduct) {
            ModularProduct modularProduct = (ModularProduct) object;
            super.map.put(ModularProduct.generateKeyFor(modularProduct), modularProduct);
        }
    }

    @Override
    public void remove(Object object) {
        if (object instanceof ModularProduct) {
            ModularProduct modularProduct = (ModularProduct) object;
            String key = ModularProduct.generateKeyFor(modularProduct);
            super.map.remove(key);
        }
    }
}
