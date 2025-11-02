/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package my.company.projetorotisseriejavafx.Controller.Modal;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import my.company.projetorotisseriejavafx.Objects.MarmitaVendida;
/**
 * FXML Controller class
 *
 * @author kaueg
 */
public class ModalDetalhesMarmitaController implements Initializable {


    @FXML
    private Scene scene;
    @FXML
    private TextField TFMarmita;
    @FXML
    private TextArea TADetalhes;
    @FXML
    private TextArea TAObservacoes;
    @FXML
    private TextField TFSubtotal;
    @FXML
    private Button btnFechar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void fechar(ActionEvent event) {
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }
    
    public void load(MarmitaVendida marmita) {
        TFMarmita.setText(marmita.getMarmita().getNome());
        TADetalhes.setText(marmita.getDetalhes());
        TAObservacoes.setText(marmita.getDetalhes());
        TFSubtotal.setText(String.valueOf(marmita.getSubtotal()));
    }

}