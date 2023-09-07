package com.example.tap2023;

import com.example.tap2023.vistas.Calculadora;
import com.example.tap2023.vistas.Loteria;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private Scene escena;
    private BorderPane borderPane;
    private MenuBar menuBar;
    private Menu menuParcial1, menuParcial2;
    private MenuItem mitCalculadora, mitLoteria;

    private void CrearGUI() {
        // Menú Items
        mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction(event -> new Calculadora());

        mitLoteria = new MenuItem("Lotería");
        mitLoteria.setOnAction(event -> new Loteria());

        // Menú
        menuParcial1 = new Menu("Parcial 1");
        menuParcial1.getItems().addAll(mitCalculadora, mitLoteria);

        menuParcial2 = new Menu("Parcial 2");

        // MenuBar
        menuBar = new MenuBar(menuParcial1, menuParcial2);
    }

    @Override
    public void start(Stage stage) throws IOException {
        CrearGUI();
        borderPane = new BorderPane();
        borderPane.setTop(menuBar);

        escena = new Scene(borderPane, 640, 480);
        stage.setScene(escena);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}