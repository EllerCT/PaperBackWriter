package listeners.product_browser;

import controllers.DesktopController;
import controllers.ProductController;
import swing_frames.ProductBrowserFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenViewProductListener implements ActionListener {

    private DesktopController desktopController;
    private ProductBrowserFrame productBrowserFrame;
    private ProductController productController;

    public OpenViewProductListener(DesktopController desktopController, ProductController productController, ProductBrowserFrame productBrowserFrame) {
        this.desktopController = desktopController;
        this.productController = productController;
        this.productBrowserFrame = productBrowserFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        desktopController.show(productController.viewProduct(productBrowserFrame), "PBW - Product Viewer");
    }
}
