package com.ergotech;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusListener;

public class WindowDemo extends Application {

    private Pane scenePane1, scenePane2;

    @Override
    public void start(Stage stage1) throws InterruptedException {
        scenePane1 = new Pane();
        scenePane2 = new Pane();

        Scene scene1 = new Scene(scenePane1, 200, 100);
        Scene scene2 = new Scene(scenePane2, 200, 100);

        Stage stage2 = new Stage();
        stage1.setScene(scene1);
        stage2.setScene(scene2);

        stage1.show();
        stage2.show();

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
            scenePane1.getChildren().add(swingNode1);
            scenePane1.requestFocus();
            scenePane2.getChildren().add(swingNode2);
            scenePane2.requestFocus();
        });

        swingNode2.setTranslateY(50);

        SwingUtilities.invokeLater(() -> {
            swingNode1.setContent(jTextField1);
            swingNode2.setContent(jTextField2);
        });

        Platform.runLater(swingNode2::requestFocus);
        Platform.runLater(() -> {
            System.out.println("FX scene 1 focus owner: " + scenePane1.getScene().focusOwnerProperty().get().getId());
            System.out.println("FX scene 2 focus owner: " + scenePane2.getScene().focusOwnerProperty().get().getId());
        });

        Platform.runLater(swingNode1::requestFocus);
        Platform.runLater(() -> {
            System.out.println("FX scene 1 focus owner: " + scenePane1.getScene().focusOwnerProperty().get().getId());
            System.out.println("FX scene 2 focus owner: " + scenePane2.getScene().focusOwnerProperty().get().getId());
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
