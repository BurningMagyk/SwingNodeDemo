package com.ergotech;

import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.application.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusListener;

public class OriginalDemo extends Application {

    private Pane scenePane;

    @Override
    public void start(Stage stage) throws InterruptedException {
        scenePane = new Pane();

        Scene scene = new Scene(scenePane, 200, 100);
        stage.setScene(scene);

        stage.show();

        JTextField jTextField1 = new JTextField("JTextField 1");
        jTextField1.setName("JTextField 1");
        JTextField jTextField2 = new JTextField("JTextField 2");
        jTextField2.setName("JTextField 2");

        FocusListener focusListener = new FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                System.out.println("Focus gained: " + e.getComponent().getName());
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                System.out.println("Focus lost: " + e.getComponent().getName()
                        + ", new focus owner: " + KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner());
            }
        };

        jTextField1.addFocusListener(focusListener);
        jTextField2.addFocusListener(focusListener);

        SwingNode swingNode1 = new SwingNode();
        swingNode1.setId("swingNode1");
        SwingNode swingNode2 = new SwingNode();
        swingNode2.setId("swingNode2");

        Platform.runLater(() -> {
            scenePane.getChildren().add(swingNode1);
            scenePane.requestFocus();
            scenePane.getChildren().add(swingNode2);
            scenePane.requestFocus();
        });

        swingNode2.setTranslateY(50);

        SwingUtilities.invokeLater(() -> {
            swingNode1.setContent(jTextField1);
            swingNode2.setContent(jTextField2);
        });

        Platform.runLater(swingNode2::requestFocus);
        Platform.runLater(() -> System.out.println("FX scene focus owner: " + scenePane.getScene().focusOwnerProperty().get().getId()));

        Platform.runLater(swingNode1::requestFocus);
        Platform.runLater(() -> System.out.println("FX scene focus owner: " + scenePane.getScene().focusOwnerProperty().get().getId()));
    }

    public static void main(String[] args) {
        launch();
    }
}