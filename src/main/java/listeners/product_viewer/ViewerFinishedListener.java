package listeners.product_viewer;

import data_structures.ModularProduct;
import managers.ModularProductManager;
import swing_frames.ProductViewerFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewerFinishedListener implements ActionListener {

    private ProductViewerFrame viewer;
    private boolean grading;
    private ModularProductManager productManager;
    private ModularProduct selected;

    public ViewerFinishedListener(ProductViewerFrame viewer, ModularProductManager productManager, boolean grading, ModularProduct selected) {
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
            productManager.add(selected);
            productManager.store();
            viewer.dispose();
        }
    }
}
