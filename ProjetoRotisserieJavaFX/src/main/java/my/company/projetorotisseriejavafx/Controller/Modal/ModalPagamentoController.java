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
import javafx.scene.control.ComboBox;

import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author kaueg
 */
public class ModalPagamentoController implements Initializable {

    private String pagamento;

    @FXML
    private Button btnImprimir;
    @FXML
    private Button btnCancelar;
    @FXML
    private ComboBox<String> comboBoxPagamento;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxPagamento.getItems().clear();
        comboBoxPagamento.getItems().setAll("Dinheiro", "Débito", "Crédito", "Pix");
        comboBoxPagamento.getSelectionModel().selectFirst();
    }    
    
    private void close() {
        Stage stage = (Stage) btnImprimir.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void Imprimir(ActionEvent event) {
        pagamento = comboBoxPagamento.getSelectionModel().getSelectedItem();
        close();
    }

    @FXML
    private void Cancelar(ActionEvent event) {
        close();
    }

    public String getPagamento() {
        return pagamento;
    }
    
    

}
