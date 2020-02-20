package utilities;

import data_structures.Material;
import data_structures.ModularProduct;
import data_structures.Product;
import data_structures.ResourceType;
import io_pipes.ModularProductIOPipe;
import io_pipes.ProductIOPipe;
import io_systems.IOSystem;
import io_systems.LocalFileIOSystem;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * To be removed after use.
 */
public class Updater {

    public void updateProductFile() {
        try {
            IOSystem productIOS = new LocalFileIOSystem();
            URI uriOfProduct = (new File("Products.csv")).toURI();
            productIOS.setLocation(uriOfProduct);

            ProductIOPipe oldIOPipe = new ProductIOPipe(productIOS);
            ModularProductIOPipe newIOPipe = new ModularProductIOPipe(productIOS);

            HashMap<String, Product> oldMap = new HashMap<>(oldIOPipe.load());
            HashMap<String, ModularProduct> newMap = translateOldProductsToNew(oldMap);

            newIOPipe.save(newMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, ModularProduct> translateOldProductsToNew(HashMap<String, Product> oldMap) {
        HashMap<String, ModularProduct> newMap = new HashMap<>();
        for (Product product : oldMap.values()) {
            ModularProduct mp = new ModularProduct();
            mp.setId(product.getId());
            mp.setTotalCost(product.getTotalCost());
            mp.setType(product.getType());
            mp.setName(product.getName());
            mp.setDescription(product.getDescription());
            ArrayList<Material> matList = new ArrayList<>();
            Material paper = new Material(ResourceType.PAPER.toString(), product.getPaperType(), Integer.parseInt(product.getPaperAmount()));
            paper.setCost(Double.parseDouble(product.getPaperCost()));
            matList.add(paper);
            Material board = new Material(ResourceType.BOARD.toString(), product.getBoardType(), Integer.parseInt(product.getBoardAmount()));
            board.setCost(Double.parseDouble(product.getBoardCost()));
            matList.add(board);
            Material decoratedPaper = new Material(ResourceType.DECORATED_PAPER.toString(), product.getDecoratedPaperType(), Integer.parseInt(product.getDecoratedPaperAmount()));
            decoratedPaper.setCost(Double.parseDouble(product.getDecoratedPaperCost()));
            matList.add(decoratedPaper);
            Material endBand = new Material(ResourceType.END_BAND.toString(), product.getEndBandType(), Integer.parseInt(product.getEndBandAmount()));
            endBand.setCost(Double.parseDouble(product.getEndBandCost()));
            matList.add(endBand);
            Material glue = new Material(ResourceType.GLUE.toString(), product.getGlueType(), Integer.parseInt(product.getGlueAmount()));
            glue.setCost(Double.parseDouble(product.getGlueCost()));
            matList.add(glue);
            Material spirit = new Material(ResourceType.MINERAL_SPIRIT.toString(), product.getSpiritType(), Integer.parseInt(product.getSpiritAmount()));
            spirit.setCost(Double.parseDouble(product.getSpiritCost()));
            matList.add(spirit);
            Material spine = new Material(ResourceType.SPINE.toString(), product.getSpineType(), Integer.parseInt(product.getSpineAmount()));
            spine.setCost(Double.parseDouble(product.getSpineCost()));
            matList.add(spirit);
            Material thread = new Material(ResourceType.THREAD.toString(), product.getThreadType(), Integer.parseInt(product.getThreadAmount()));
            thread.setCost(Double.parseDouble(product.getThreadCost()));
            matList.add(thread);
            Material other = new Material(ResourceType.OTHER.toString(), product.getOther(), Integer.parseInt(product.getOtherAmount()));
            try {
                other.setCost(Double.parseDouble(product.getOtherCost()));
            } catch (NumberFormatException e) {
                other.setCost(0.0);
            }
            matList.add(other);
            mp.setMaterials(matList);
            newMap.put(ModularProduct.generateKeyFor(mp), mp);
        }
        return newMap;
    }
}
