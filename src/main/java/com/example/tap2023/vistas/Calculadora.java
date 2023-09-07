package com.example.tap2023.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora extends Stage {
    Scene escena;
    private VBox vbox;
    private GridPane gridTeclado;
    private TextField txtResultado;
    private final Button[][] arTeclas = new Button[5][4];
    private final String[] arDigitos = {"C", "CE", "√", "±", "7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", ".", "=", "+"};
    private String input = "";
    private String sig = "";
    private double n1 = 0;
    private double n2 = 0;

    public Calculadora() {
        crearUI();
        escena = new Scene(vbox, 260, 375);
        this.setTitle("Calculadora");
        this.setScene(escena);
        this.setResizable(false);
        this.show();
    }

    private void crearUI() {
        vbox = new VBox();
        gridTeclado = new GridPane();
        txtResultado = new TextField("");
        txtResultado.setAlignment(Pos.CENTER_RIGHT);
        txtResultado.setEditable(false);
        txtResultado.setPrefHeight(50);

        int pos = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                arTeclas[i][j] = new Button(arDigitos[pos]);
                arTeclas[i][j].setPrefSize(65, 65);
                int finalPos = pos;
                arTeclas[i][j].setOnAction(event -> procesarBoton(arDigitos[finalPos]));
                gridTeclado.add(arTeclas[i][j], j, i);
                pos++;
            }
        }
        vbox.getChildren().addAll(txtResultado, gridTeclado);
    }

    private void procesarBoton(String txtBtn) {
        if (txtBtn.matches("[0-9.]")) {
            generarExpresion(txtBtn);
        } else if (txtBtn.equals("=")) {
            if (!input.isEmpty() && sig.isEmpty()) {
                double resultado = Double.parseDouble(input);
                mostrarResultado(resultado);
                input = Double.toString(resultado);
            } else if (!input.isEmpty()) {
                n2 = Double.parseDouble(input);
                double resultado = realizarOperacion();
                mostrarResultado(resultado);
                input = Double.toString(resultado);
                sig = "";
            }
        } else if (txtBtn.matches("[-+*/]")) {
            if (!input.isEmpty() && sig.isEmpty()) {
                sig = txtBtn;
                n1 = Double.parseDouble(input);
                input = "";
            }
        } else if (txtBtn.equals("C")) {
            input = "";
            sig = "";
            n1 = 0.0;
            n2 = 0.0;
            txtResultado.setText("");
        } else if (txtBtn.equals("CE")) {
            if (!input.isEmpty()) {
                input = input.substring(0, input.length() - 1);
                txtResultado.setText(input);
            }
        } else if (txtBtn.equals("√")) {
            if (!input.isEmpty()) {
                double num = Double.parseDouble(input);
                if (num >= 0) {
                    double resultado = Math.sqrt(num);
                    txtResultado.setText(Double.toString(resultado));
                    input = Double.toString(resultado);
                } else {
                    txtResultado.setText("Error: Raíz de número negativo");
                    input = "";
                }
            } else {
                txtResultado.setText("Error: No se ha introducido ningún número");
            }
        } else if (txtBtn.equals("±")) {
            if (!input.isEmpty()) {
                double num = Double.parseDouble(input);
                num *= -1;
                input = Double.toString(num);
                txtResultado.setText(input);
            } else {
                txtResultado.setText("Error: No se ha introducido ningún número");
            }
        }
    }

    private void generarExpresion(String txtBtn) {
        if (txtBtn.equals(".")) {
            if (!input.contains(".")) {
                input += txtBtn;
                txtResultado.setText(input);
            }
        } else {
            input += txtBtn;
            txtResultado.setText(input);
        }
    }

    private void mostrarResultado(double resultado) {
        if (Double.isNaN(resultado))
            txtResultado.setText("Error: División entre cero");
        else
            txtResultado.setText(Double.toString(resultado));
        input = Double.toString(resultado);
        sig = "";
    }

    private double realizarOperacion() {
        double resultado = 0.0;
        switch (sig) {
            case "+":
                resultado = n1 + n2;
                break;
            case "-":
                resultado = n1 - n2;
                break;
            case "*":
                resultado = n1 * n2;
                break;
            case "/":
                if (n2 != 0) {
                    resultado = n1 / n2;
                } else {
                    resultado = Double.NaN;
                }
                break;
        }
        return resultado;
    }
}
