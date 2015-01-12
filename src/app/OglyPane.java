package app;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;

/*
 * This panel will be partially loaded from FXML. Note that I prefer to use the fx:root construct.
 * There are other ways to use FXML but I prefer this one as it provides better encapsulation. When
 * using fx:root, you define a subclass of the root node in your FXML document (here a BorderPane).
 */
public class OglyPane extends BorderPane {

    /*
     * Nodes that have an fx:id in the FXML document can be accessed from code. The name of the
     * attribute has to match the fx:id. The types have to match as well ofcourse. The @FXML
     * annotation gives the FXMLLoader access to an otherwise private attribute.
     */
    
    @FXML
    private AnchorPane oglyPane;
    
    @FXML
    private Slider headRadiusSlider;
    
    @FXML
    private Slider eyeRadiusSlider;
    
    @FXML
    private Slider pupilRadiusSlider;
    
    @FXML
    private Slider eyeSpacingSlider;
    
    @FXML
    private Slider eyeHeightSlider;
    
    /*
     * We'll keep a reference to the model, for easy access.
     */
    private final OglyModel m;
    
    /*
     * These circles are used to build Ogly.
     */
    private Circle head;
    private Circle leftEye;
    private Circle rightEye;
    private Circle leftPupil;
    private Circle rightPupil;
    
    public OglyPane(OglyModel m) {
        
        this.m = m;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Ogly.fxml"));
        
        // Tell the loader that this object is the BorderPane we've designed in FXML.
        loader.setRoot(this);
        
        // Tell the loader that this is the object whose attributes and methods are referenced in FXML.
        loader.setController(this);
        
        // Load the FXML document. When this succeeds, the @FXML attributes will be ready to use.
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(OglyPane.class.getName()).log(Level.SEVERE, "Unable to load Ogly.fxml", ex);
        }
        
        // The value of each slider is bidirectionally bound to the corresponding property in the model.
        // This keeps them in sync at all times:
        // - When the slider is moved, the model is updated with the new value.
        // - When the model is changed without using the slider, the slider is updated with the new value.
        
        headRadiusSlider.valueProperty().bindBidirectional(m.headRadiusProperty());
        eyeRadiusSlider.valueProperty().bindBidirectional(m.eyeRadiusProperty());
        pupilRadiusSlider.valueProperty().bindBidirectional(m.pupilRadiusProperty());
        eyeSpacingSlider.valueProperty().bindBidirectional(m.eyeSpacingProperty());
        eyeHeightSlider.valueProperty().bindBidirectional(m.eyeHeightProperty());
        
        buildOgly();
    }
    
    /*
     * Create the circles used to build Ogly. Because CSS is used to style these circles, they are
     * assigned an id or style class where appropriate. The center point and radius for each circle
     * are set using bindings.
     */
    private void buildOgly() {
        
        head = new Circle();
        head.setId("head");
        head.radiusProperty().bind(m.headRadiusProperty());
        
        // The center of the head is bound to the center of the pane.
        // This will cause the head to move when the stage is resized.
        head.centerXProperty().bind(oglyPane.widthProperty().divide(2));
        head.centerYProperty().bind(oglyPane.heightProperty().divide(2));
         
        leftEye = new Circle();
        leftEye.getStyleClass().add("eye");
        leftEye.radiusProperty().bind(m.eyeRadiusProperty());
        
        // The eyes are positioned using the center of the head, the eye spacing and eye height.
        leftEye.centerXProperty().bind(head.centerXProperty().subtract(m.eyeSpacingProperty().divide(2)));
        leftEye.centerYProperty().bind(head.centerYProperty().subtract(m.eyeHeightProperty()));
        
        rightEye = new Circle();
        rightEye.getStyleClass().add("eye");
        rightEye.radiusProperty().bind(m.eyeRadiusProperty());
        rightEye.centerXProperty().bind(head.centerXProperty().add(m.eyeSpacingProperty().divide(2)));
        rightEye.centerYProperty().bind(head.centerYProperty().subtract(m.eyeHeightProperty()));
          
        leftPupil = new Circle();
        leftPupil.getStyleClass().add("pupil");
        leftPupil.radiusProperty().bind(m.pupilRadiusProperty());
        
        // We can't bind the center of the pupils because we need to be able to set them directly.
        leftPupil.setCenterX(leftEye.getCenterX());
        leftPupil.setCenterY(leftEye.getCenterY());
         
        rightPupil = new Circle();
        rightPupil.getStyleClass().add("pupil");
        rightPupil.radiusProperty().bind(m.pupilRadiusProperty());
        rightPupil.setCenterX(rightEye.getCenterX());
        rightPupil.setCenterY(rightEye.getCenterY());
        
        // Because the centers of the pupils are not bound to the centers of the eyes,
        // we add listeners that update them manually whenever the eyes change position.
        // This is needed to make the pupils move with the eyes when the stage is resized.
        
        leftEye.centerXProperty().addListener((object, oldValue, newValue) -> {
            leftPupil.setCenterX(newValue.doubleValue());
        });
        
        leftEye.centerYProperty().addListener((object, oldValue, newValue) -> {
            leftPupil.setCenterY(newValue.doubleValue());
        });
        
        rightEye.centerXProperty().addListener((object, oldValue, newValue) -> {
            rightPupil.setCenterX(newValue.doubleValue());
        });
        
        rightEye.centerYProperty().addListener((object, oldValue, newValue) -> {
            rightPupil.setCenterY(newValue.doubleValue());
        });
        
        // Finally, add all the circles to the pane in the right order.
        oglyPane.getChildren().addAll(head, leftEye, rightEye, leftPupil, rightPupil);
    }

    /*
     * These final two methods are event handlers mentioned in the FXML file.
     */
    
    /*
     * This method makes Ogly's pupils track the mouse.
     * Read "docs/Do The Math.pdf" if you want to understand how this works.
     */
    @FXML private void mouseMoved(MouseEvent e) {
        double ep = m.getEyeRadius() - m.getPupilRadius();
        
        double ea = e.getX() - leftEye.getCenterX();
        double ma = e.getY() - leftEye.getCenterY();
        double em = Math.sqrt(ea * ea + ma * ma);

        if (em < m.getEyeRadius() - m.getPupilRadius()) {
            leftPupil.setCenterX(e.getX());
            leftPupil.setCenterY(e.getY());
        } else {
            leftPupil.setCenterX(leftEye.getCenterX() + ea * ep / em);
            leftPupil.setCenterY(leftEye.getCenterY() + ma * ep / em);
        }

        ea = e.getX() - rightEye.getCenterX();
        ma = e.getY() - rightEye.getCenterY();
        em = Math.sqrt(ea * ea + ma * ma);

        if (em < m.getEyeRadius() - m.getPupilRadius()) {
            rightPupil.setCenterX(e.getX());
            rightPupil.setCenterY(e.getY());
        } else {
            rightPupil.setCenterX(rightEye.getCenterX() + ea * ep / em);
            rightPupil.setCenterY(rightEye.getCenterY() + ma * ep / em);
        }
    }
    
    /*
     * This method puts Ogly's pupils back in the centers of his eyes.
     */
    @FXML private void mouseExited(MouseEvent e) {
        leftPupil.setCenterX(leftEye.getCenterX());
        leftPupil.setCenterY(leftEye.getCenterY());
        rightPupil.setCenterX(rightEye.getCenterX());
        rightPupil.setCenterY(rightEye.getCenterY());
    }
}
