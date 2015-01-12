package app;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/*
 * A model that holds Ogly's measurements. Each measurement is implemented as a
 * JavaFX property. Such a property consists of the following:
 * - an attribute of a property type. This object has its own getter and setter
 *   and maintains a list of listeners.
 * - a getter and setter to get and set the value of the property directly.
 * - a getter to get the property itself. This is used for binding.
 */
public class OglyModel {

    /* Head radius */
    
    private final DoubleProperty headRadius = new SimpleDoubleProperty(150);

    public double getHeadRadius() {
        return headRadius.get();
    }

    public void setHeadRadius(double value) {
        headRadius.set(value);
    }

    public DoubleProperty headRadiusProperty() {
        return headRadius;
    }
    
    /* Eye radius */
    
    private final DoubleProperty eyeRadius = new SimpleDoubleProperty(30);

    public double getEyeRadius() {
        return eyeRadius.get();
    }

    public void setEyeRadius(double value) {
        eyeRadius.set(value);
    }

    public DoubleProperty eyeRadiusProperty() {
        return eyeRadius;
    }
    
    /* Pupil radius */
    
    private final DoubleProperty pupilRadius = new SimpleDoubleProperty(10);

    public double getPupilRadius() {
        return pupilRadius.get();
    }

    public void setPupilRadius(double value) {
        pupilRadius.set(value);
    }

    public DoubleProperty pupilRadiusProperty() {
        return pupilRadius;
    }
    
    /* Eye spacing */
    
    private final DoubleProperty eyeSpacing = new SimpleDoubleProperty(80);

    public double getEyeSpacing() {
        return eyeSpacing.get();
    }

    public void setEyeSpacing(double value) {
        eyeSpacing.set(value);
    }

    public DoubleProperty eyeSpacingProperty() {
        return eyeSpacing;
    }
    
    /* Eye height */
    
    private final DoubleProperty eyeHeight = new SimpleDoubleProperty(60);

    public double getEyeHeight() {
        return eyeHeight.get();
    }

    public void setEyeHeight(double value) {
        eyeHeight.set(value);
    }

    public DoubleProperty eyeHeightProperty() {
        return eyeHeight;
    }
}
