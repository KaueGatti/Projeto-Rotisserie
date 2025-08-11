/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.company.projetorotisseriejavafx.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author kaueg
 */
public class modalObservacaoController implements Initializable {
    
    String observacao;

    @FXML
    private Button bttAdicionar;

    @FXML
    private Button bttCancelar;
    
    @FXML
    private TextArea TAobservacao;
    @FXML
    private AnchorPane root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    private void close() {
        Stage stage = (Stage) bttAdicionar.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void Adicionar() {
        observacao = TAobservacao.getText();
        close();
    }
    
    @FXML
    private void Cancelar() {
        observacao = null;
        close();
    }
    
    public String getObservacao() {
        return observacao;
    }
    
    public void loadObservacao(String obs) {
        TAobservacao.setText(obs);
    }

}
