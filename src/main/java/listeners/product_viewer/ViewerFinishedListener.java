package listeners.product_viewer;

import data_structures.Product;
import managers.ProductManager;
import swing_frames.ProductViewerFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewerFinishedListener implements ActionListener {

    private ProductViewerFrame viewer;
    private boolean grading;
    private ProductManager productManager;
    private Product selected;

    public ViewerFinishedListener(ProductViewerFrame viewer, ProductManager productManager, boolean grading, Product selected) {
        this.viewer = viewer;
        this.grading = grading;
        this.productManager = productManager;
        this.selected = selected;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!grading) {
            viewer.dispose();
        } else {
            selected.setGrade(viewer.getGrade());
            productManager.updateProduct(selected);
            productManager.storeProducts();
            viewer.dispose();
        }
    }
}
