package com.example.tap2023.vistas;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class Loteria extends Stage {

    public static boolean juegoIniciado = false;
    public static String carta;
    public static int contador;
    private Scene escena;
    private HBox hPrincipal, hboxSel;
    private VBox vTablilla, vBaraja;
    private Button btnAnt, btnSig, btnIniciar, btnRecargar;
    private Image imgCarta;
    private ImageView imageCarta, imvCarta;
    private final Button[][] botonTablilla = new Button[4][4];
    private GridPane gridTablilla;
    private String[] cats = {
            "abisinio.jpg",
            "azul_ruso.jpg",
            "bengali.jpg",
            "bilanes.jpg",
            "birmano.jpg",
            "bobtail.jpg",
            "bombay.jpg",
            "bosque_de_noruega.jpg",
            "bosque_de_siberia.jpg",
            "britanico.jpg",
            "burmilla.jpg",
            "burnes.jpg",
            "chincilla.jpg",
            "cymric.jpg",
            "korat.jpg",
            "maine_coon.jpg",
            "manx.jpg",
            "mau.jpg",
            "mist.jpg",
            "munchkin.jpg",
            "ocigato.jpg",
            "persa.jpg",
            "pixie_bob.jpg",
            "ragdoll.jpg",
            "savannha.jpg",
            "siames.jpg",
            "singapura.jpg",
            "snowshoe.jpg",
            "somali.jpg",
            "sphynx.jpg",
            "tiffanie.jpg",
            "tonquines.jpg"
    };
    private static List<String> gatoRandom;
    private Tablilla[] tablilla = new Tablilla[5];
    private GridPane[] gridPanes = new GridPane[5];
    private int num = 0;
    private Timer timer;
    private int actual = 0;

    private void CrearGUI() {
        CrearBaraja();
        for (int i = 0; i < tablilla.length; i++) {
            tablilla[i] = new Tablilla();
            gridPanes[i] = new GridPane();
            gridPanes[i] = tablilla[i].generarTablilla(cats);
        }
        btnAnt = new Button("<-");
        btnAnt.setPrefSize(210, 100);
        btnAnt.setOnAction(event -> {
            if (num == 0)
                num = 4;
            else
                num--;
            vTablilla.getChildren().clear();
            vTablilla.getChildren().addAll(gridPanes[num], hboxSel);
        });
        btnSig = new Button("->");
        btnSig.setPrefSize(210, 100);
        btnSig.setOnAction(event -> {
            if (num == 4)
                num = 0;
            else
                num++;
            vTablilla.getChildren().clear();
            vTablilla.getChildren().addAll(gridPanes[num], hboxSel);
        });
        hboxSel = new HBox(btnAnt, btnSig);
        num = 0;
        vTablilla = new VBox(gridPanes[num], hboxSel);
        vTablilla.setSpacing(20);
        hPrincipal = new HBox(vTablilla, vBaraja);
        hPrincipal.setPadding(new Insets(20));
        hPrincipal.setSpacing(20);
    }

    private void CrearBaraja() {
        Image imgDorso = new Image(getClass().getResource("/com/example/tap2023/images/abisinio.jpg").toString());
        imvCarta = new ImageView(imgDorso);
        imvCarta.setFitWidth(200);
        imvCarta.setFitHeight(350);
        btnIniciar = new Button("Comenzar juego");
        btnIniciar.setOnAction(event -> {
            juegoIniciado = true;
            btnIniciar.setDisable(true);
            btnSig.setDisable(true);
            btnAnt.setDisable(true);
            mezclar(cats);
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    jugarMazo();
                }
            }, 0, 5000);
        });
        btnRecargar = new Button("Recargar");
        btnRecargar.setOnAction(event -> {
            this.close();
            actual = 0;
            if (timer != null)
                timer.cancel();
            new Loteria();
        });
        vBaraja = new VBox(imvCarta, btnIniciar, btnRecargar);
        vBaraja.setSpacing(20);
    }

    public Loteria() {
        CrearGUI();
        escena = new Scene(hPrincipal, 850, 650);
        escena.getStylesheets().add(getClass().getResource("/css/estilo_loteria.css").toString());
        this.setTitle("Lotería");
        this.setScene(escena);
        this.setOnCloseRequest(event -> {
            if (timer != null) {
                timer.cancel();
            }
        });
        this.show();
    }

    public void mezclar(String[] cats) {
        gatoRandom = Arrays.asList(cats);
        Collections.shuffle(gatoRandom);
    }

    public void jugarMazo() {
        if (actual < gatoRandom.size()) {
            carta = gatoRandom.get(actual);
            Image imgCarta = new Image(getClass().getResource("/com/example/tap2023/images/" + carta).toString());
            imvCarta.setImage(imgCarta);
            actual++;
        } else {
            if (contador != 16) {
                Platform.runLater(() -> {
                    actual = 0;
                    timer.cancel();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Perdedor");
                    alert.setHeaderText("Perdiste :(");
                    alert.setContentText("Has perdido");
                    alert.showAndWait();
                });
            }
            timer.cancel();
        }
    }

    class Tablilla {

        private GridPane gridTablilla;
        private ImageView imageCarta;
        private Button[][] botonTablilla = new Button[4][4];
        private List<String> arrGatos;

        public GridPane generarTablilla(String[] nombreGatos) {
            contador = 0;
            gridTablilla = new GridPane();
            arrGatos = Arrays.asList(nombreGatos);
            Collections.shuffle(arrGatos);
            int pos = 0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    try {
                        String carta = arrGatos.get(pos);
                        Image imgCartaP = new Image(getClass().getResource("/com/example/tap2023/images/" + carta).toString());
                        imageCarta = new ImageView(imgCartaP);
                        imageCarta.setFitHeight(130);
                        imageCarta.setFitWidth(90);
                        botonTablilla[i][j] = new Button();
                        botonTablilla[i][j].setPrefSize(100, 140);
                        botonTablilla[i][j].setGraphic(imageCarta);

                        int finalI = i;
                        int finalJ = j;
                        botonTablilla[i][j].setOnAction(event -> {
                            if (Loteria.juegoIniciado) {
                                if (Loteria.carta.equals(carta)) {
                                    botonTablilla[finalI][finalJ].setDisable(true);
                                    contador++;
                                    if (contador == 16) {
                                        Platform.runLater(() -> {
                                            actual = 0;
                                            timer.cancel();
                                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                            alert.setTitle("Ganador");
                                            alert.setHeaderText("Ganaste!!!");
                                            alert.setContentText("Has ganado");
                                            alert.showAndWait();
                                        });
                                    }
                                }
                            }
                        });
                        gridTablilla.add(botonTablilla[i][j], i, j);
                        pos++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return gridTablilla;
        }
    }
}
