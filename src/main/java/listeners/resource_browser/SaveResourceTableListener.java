package listeners.resource_browser;

import data_structures.Resource;
import data_structures.ResourceType;
import managers.ResourceManager;
import swing_frames.ResourcesFrame;
import ui_components.table_models.ResourceTableModel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

public class SaveResourceTableListener implements ActionListener {

    private ResourcesFrame resourcesFrame;
    private ResourceManager resourceManager;

    public SaveResourceTableListener(ResourcesFrame resourcesFrame, ResourceManager resourceManager) {
        this.resourcesFrame = resourcesFrame;
        this.resourceManager = resourceManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultTableModel model = (ResourceTableModel) resourcesFrame.getModel();
        Vector<Vector> rows = model.getDataVector();
        HashMap<String, Resource> resourceMap = new HashMap<>();
        for (Vector row : rows) {
            ResourceType type = (ResourceType) row.get(0);
            String name = (String) row.get(1);
            String unitSize = (String) row.get(2);
            double unitPrice = (Double) row.get(3);

            Resource resource = new Resource(name);
            resource.setType(type);
            resource.setUnitSize(unitSize);
            resource.setPricePerUnit(unitPrice);
            resourceMap.put(Resource.generateKeyFor(resource), resource);
        }
        resourceManager.setMap(resourceMap);
        resourceManager.store();
        resourcesFrame.dispose();
    }
}
