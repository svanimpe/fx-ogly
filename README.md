# Ogly

This was the second of two examples I used to teach my first JavaFX workshop. This example covers:

- properties and binding
- MVC with FXML and Scene Builder
- CSS

## fx:root

When updating this example for JDK 8, I decided to rework it and use the <code>fx:root</code> construct described <a href="https://docs.oracle.com/javafx/2/api/javafx/fxml/doc-files/introduction_to_fxml.html#custom_components">here</a>.

I very much prefer this approach as it hides the implementation of a component and allows you to easily mix and match handcoded components with ones loaded from FXML. For a different approach, see <a href="https://github.com/svanimpe/fx-buglist">BugList</a>.

This repository contains a NetBeans project. It was last tested using NetBeans 8.0.2 and JDK 8u25.
