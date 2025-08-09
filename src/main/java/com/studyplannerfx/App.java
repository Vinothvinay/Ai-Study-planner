package com.studyplannerfx;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Entry point for the Study Planner (Frontend-only).
 * No database. All data is in-memory.
 * Includes a simple dark/light theme toggle and startup animation.
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        DashboardView dashboard = new DashboardView();
        root.getChildren().add(dashboard.getRoot());

        Scene scene = new Scene(root, 1200, 800);
        // Default to light.css
        scene.getStylesheets().add(getClass().getResource("/styles/light.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/styles/base.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/styles/components.css").toExternalForm());

        // Wire theme toggle to swap stylesheets.
        dashboard.setThemeSwitcher((dark) -> {
            scene.getStylesheets().removeIf(s ->
                s.endsWith("/styles/dark.css") || s.endsWith("/styles/light.css"));
            scene.getStylesheets().add(getClass().getResource(dark ? "/styles/dark.css" : "/styles/light.css").toExternalForm());
        });

        stage.setTitle("AI Study Planner (Frontend)");
        stage.setScene(scene);
        stage.show();

        // Smooth startup animation
        ScaleTransition scale = new ScaleTransition(Duration.millis(600), root);
        scale.setFromX(0.98);
        scale.setFromY(0.98);
        scale.setToX(1.0);
        scale.setToY(1.0);

        FadeTransition fade = new FadeTransition(Duration.millis(600), root);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);

        scale.play();
        fade.play();
    }

    public static void main(String[] args) {
        launch();
    }
}