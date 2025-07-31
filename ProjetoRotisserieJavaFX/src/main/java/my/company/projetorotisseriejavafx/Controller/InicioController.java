/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package my.company.projetorotisseriejavafx.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author kaueg
 */
public class InicioController implements Initializable {

    @FXML
    private Button bttNovoPedido;
    @FXML
    private AnchorPane APPrincipal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void bttNovoPedido() {
        APPrincipal.getChildren().clear();
        try {
            APPrincipal.getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/NovoPedido.fxml")));
        } catch (IOException e) {
            System.out.println("Erro bttNovoPedido " + e);
        }
    }

}
