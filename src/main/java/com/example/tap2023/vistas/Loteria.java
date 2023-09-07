package com.example.tap2023.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class Loteria extends Stage {

    private Scene escena;
    private HBox hboxPrincipal, hboxBtnSeleccion;
    private VBox vboxTablilla, vboxMazo;
    private Button btnAnterior, btnSiguiente, btnIniciar;
    private Button[][] btnTablilla = new Button[4][4];
    private GridPane gridTablilla;

    private void CrearUI(){
        CrearTablilla();
        btnAnterior = new Button("<-");
        btnAnterior.setPrefSize(210, 100);
        btnSiguiente = new Button("->");
        btnSiguiente.setPrefSize(210, 100);
        hboxBtnSeleccion = new HBox(btnAnterior, btnSiguiente);
        vboxTablilla = new VBox(gridTablilla, hboxBtnSeleccion);
        vboxMazo = new VBox();
        hboxPrincipal = new HBox(vboxTablilla, vboxMazo);
        hboxPrincipal.setPadding(new Insets(20));
    }

    private void CrearTablilla() {
        gridTablilla = new GridPane();
        int cont = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Image imgCartaP = new Image(Objects.requireNonNull(getClass().getResource("/com/example/tap2023/images/gato.jpg")).toExternalForm());
                ImageView imv = new ImageView(imgCartaP);
                imv.setFitHeight(130);
                imv.setFitWidth(90);
                btnTablilla[i][j] = new Button();
                btnTablilla[i][j].setPrefSize(100, 140);
                btnTablilla[i][j].setGraphic(imv);
                gridTablilla.add(btnTablilla[i][j], i, j);
                cont++;
            }
        }
    }

    public Loteria() {
        CrearUI();
        escena = new Scene(hboxPrincipal, 850, 650);
        this.setTitle("Lotería");
        this.setScene(escena);
        this.show();
    }
}
