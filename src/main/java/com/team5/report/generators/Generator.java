package com.team5.report.generators;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

public abstract class Generator extends Application {
    protected static boolean isInitialized = false;
    protected static Stage mainStage = null;

    @Override
    public void start(Stage stage) throws Exception {
        // Disable implicit exit so that the chart can be opened again
        Platform.setImplicitExit(false);

        // Assign the main stage
        mainStage = stage;

        // Set the initialized flag
        isInitialized = true;

        // Display the chart
        displayChart(stage);
    }

    /**
     * Displays the chart with the default stage
     */
    protected void displayChart() {
        // If the main stage has not been initialized then initialize one
        if (mainStage == null) {
            mainStage = new Stage();
            isInitialized = true;
        }
        
        // Display the chart
        displayChart(mainStage);
    }

    /**
     * Displays the chart to the given stage.
     * 
     * @param stage The stage.
     */
    protected abstract void displayChart(Stage stage);

    protected void setupSaveHook(Stage stage, Scene scene, String targetPath) {
        // Save the scene in the target path on close
        stage.setOnCloseRequest(e -> {
			try {
				saveAsPng(scene, targetPath);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
            }
		});
    }

    /**
     * Saves the generated report to the given target location.
     * 
     * @param scene The report.
     * @param path The save location.
     * @throws IOException Thrown if an IOException occurs.
     */
    protected static void saveAsPng(Scene scene, String path) throws IOException, InterruptedException {
        WritableImage image = scene.snapshot(null);
        File file = new File(path);
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
    }

    /**
     * Generates the chart.
     */
    protected void generate() {
        // If javafx hasn't been run yet
        if (isInitialized == false) {
            // Launch the chart
            launchSelf();
        } else {
            // Display the chart from the javafx thread 
            Platform.runLater(() -> {
                displayChart();
            });
        }
    }

    /**
     * Launches the javafx chart.
     */
    protected abstract void launchSelf();
}