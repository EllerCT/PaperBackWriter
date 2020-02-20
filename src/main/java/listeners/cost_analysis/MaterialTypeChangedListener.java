package listeners.cost_analysis;

import data_structures.Resource;
import data_structures.ResourceType;
import ui_components.MaterialPane;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class MaterialTypeChangedListener implements ActionListener {

    private Map resourceMap;
    private JComboBox<ResourceType> typeOfMaterial;
    private JComboBox<Resource> specificMaterial;

    public MaterialTypeChangedListener(MaterialPane pane, Map resourceMap) {
        this.typeOfMaterial = pane.getTypeOfMaterial();
        this.specificMaterial = pane.getSpecificMaterial();
        this.resourceMap = resourceMap;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        specificMaterial.removeAllItems();
        Resource emptyResource = new Resource("None");
        ResourceType selectedType = (ResourceType) typeOfMaterial.getSelectedItem();
        specificMaterial.addItem(emptyResource);
        emptyResource.setType(selectedType);
        for (Object value : resourceMap.values()) {
            if (value instanceof Resource) {
                Resource resource = (Resource) value;
                if (resource.getType().equals(selectedType))
                    specificMaterial.addItem(resource);
            }
        }
    }
}
