/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package my.company.projetorotisseriejavafx.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.Objects.MarmitaVendida;
import my.company.projetorotisseriejavafx.Objects.ProdutoVendido;
/**
 * FXML Controller class
 *
 * @author kaueg
 */
public class ModalDetalhesProdutoController implements Initializable {


    @FXML
    private TextField TFProduto;
    @FXML
    private TextField TFQuantidade;
    @FXML
    private TextField TFSubtotal;
    @FXML
    private Button btnFechar;
    @FXML
    private Scene scene;
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
    
    public void load(ProdutoVendido produto) {
        TFProduto.setText(produto.getProduto().getDescricao());
        TFQuantidade.setText(String.valueOf(produto.getQuantidade()));
        TFSubtotal.setText(String.valueOf(produto.getSubtotal()));
    }

}
