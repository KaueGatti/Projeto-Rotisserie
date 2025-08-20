/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package my.company.projetorotisseriejavafx.Controller.Modal;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kaueg
 */
public class ModalDescontosController implements Initializable {

    private double desconto;
    private double adicional;

    @FXML
    private Button btnSalvar;
    @FXML
    private TextField TFDesconto;
    @FXML
    private TextField TFAdicional;
    @FXML
    private Button btnCancelar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TFAdicional.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*(\\.|,)?\\d*")) {
                return change;
            }
            return null;
        }));
        TFDesconto.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*(\\.|,)?\\d*")) {
                return change;
            }
            return null;
        }));
    }

    private void close() {
        Stage stage = (Stage) btnSalvar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void Salvar(ActionEvent event) {
        if (!TFDesconto.getText().equals("")) {
            desconto = Double.parseDouble(TFDesconto.getText());
        } else {
            desconto = -1;
        }

        if (!TFAdicional.getText().equals("")) {
            adicional = Double.parseDouble(TFAdicional.getText());
        } else {
            adicional = -1;
        }

        close();
    }

    @FXML
    private void Cancelar(ActionEvent event) {
        desconto = -1;
        adicional = -1;
        close();
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public double getAdicional() {
        return adicional;
    }

    public void setAdicional(double adicional) {
        this.adicional = adicional;
    }
    
    public void loadDescontos(double valorDesconto, double valorAdicional) {
        TFDesconto.setText(String.valueOf(valorDesconto));
        TFAdicional.setText(String.valueOf(valorAdicional));
    }

}
