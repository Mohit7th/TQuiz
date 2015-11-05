/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataEntry;

import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Saurabh
 */
public class MyToast {

    public static void createToast(String popupText) {
        Stage stage = new Stage();
        AnchorPane anchor = new AnchorPane();
        anchor.setStyle("-fx-background-color:none;");
        Label lbl = new Label(popupText);
        lbl.setStyle("-fx-font-size:10pt; -fx-background-color:gray; -fx-text-fill: white;");
        anchor.getChildren().add(lbl);
        Scene scene = new Scene(anchor, 200, 100);
        stage.setScene(scene);
        stage.setTitle("");
        scene.setFill(null);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setY(390.0);
        stage.setX(500);
        stage.show();

        //After 2 seconds close the stage
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> {
            stage.close();
        });
        delay.play();

    }

}
