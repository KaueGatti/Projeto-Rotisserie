/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package my.company.projetorotisseriejavafx.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import javafx.scene.layout.Pane;
import my.company.projetorotisseriejavafx.DAO.MarmitaDAO;
import my.company.projetorotisseriejavafx.DAO.ProdutoDAO;
import my.company.projetorotisseriejavafx.Objects.Marmita;
import my.company.projetorotisseriejavafx.Objects.Produto;
/**
 * FXML Controller class
 *
 * @author kaueg
 */
public class PaneMarmitaController implements Initializable {

    @FXML
    private ComboBox comboBox;
    
    @FXML
    private Pane paneMarmita1;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadMarmitas();
    }    
    
    public void loadMarmitas() {
        comboBox.getItems().clear();
        for (Marmita marmita : MarmitaDAO.read()) {
            comboBox.getItems().add(marmita);
        }
        comboBox.getSelectionModel().selectFirst();
    }
    
}
