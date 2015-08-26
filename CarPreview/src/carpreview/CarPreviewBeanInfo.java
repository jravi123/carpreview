package carpreview;

import java.beans.*;

/**
 *
 * @author Jan Stola
 */
public class CarPreviewBeanInfo extends SimpleBeanInfo {
    private PropertyDescriptor[] pds;

    private static PropertyDescriptor[] createPropertyDescriptors() throws IntrospectionException {
        PropertyDescriptor[] properties = new PropertyDescriptor[7];
        int index = 0;
        properties[index] = new PropertyDescriptor("color", CarPreview.class);
        properties[index].setBound(true);
        properties[index++].setPreferred(true);
        properties[index] = new PropertyDescriptor("modernness", CarPreview.class);
        properties[index].setBound(true);
        properties[index++].setPreferred(true);
        properties[index] = new PropertyDescriptor("make", CarPreview.class);
        properties[index].setBound(true);
        properties[index++].setPreferred(true);
        properties[index] = new PropertyDescriptor("tireSize", CarPreview.class);
        properties[index].setBound(true);
        properties[index++].setPreferred(true);
        properties[index] = new PropertyDescriptor("tireType", CarPreview.class);
        properties[index].setBound(true);
        properties[index++].setPreferred(true);
        properties[index] = new PropertyDescriptor("spoiler", CarPreview.class);
        properties[index].setBound(true);
        properties[index++].setPreferred(true);
        properties[index] = new PropertyDescriptor("sunRoof", CarPreview.class);
        properties[index].setBound(true);
        properties[index++].setPreferred(true);
        return properties;
    }

    public synchronized PropertyDescriptor[] getPropertyDescriptors() {
        if (pds == null) {
            try {
                pds = createPropertyDescriptors();
            } catch (IntrospectionException e) {
                pds = super.getPropertyDescriptors();
            }
        }
        return pds;
    }

    public BeanDescriptor getBeanDescriptor() {
        BeanDescriptor beanDescriptor = new BeanDescriptor(CarPreview.class, null);
        beanDescriptor.setValue("isContainer", Boolean.FALSE);
        return beanDescriptor;
    }
    
    private static java.awt.Image iconColor16 = null;

    public java.awt.Image getIcon(int iconKind) {
        switch (iconKind) {
            case ICON_COLOR_16x16:
                if (iconColor16 == null) iconColor16 = loadImage("/race/resources/car_icon.gif");
                return iconColor16;
            default: return null;
        }
    }

}
