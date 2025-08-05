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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
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

}
