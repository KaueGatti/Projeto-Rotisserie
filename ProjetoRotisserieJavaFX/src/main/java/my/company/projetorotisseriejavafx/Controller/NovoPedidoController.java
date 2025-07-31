package my.company.projetorotisseriejavafx.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class NovoPedidoController implements Initializable {

    @FXML
    private ToggleButton tabButtonLeft;

    @FXML
    private ToggleButton tabButtonRight;

    @FXML
    private AnchorPane APMarmitaProduto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
                Pane marmitaPane = FXMLLoader.load(getClass().getResource("/fxml/paneMarmita.fxml"));
                APMarmitaProduto.getChildren().add(marmitaPane);
            } catch (Exception e) {
                System.out.println("Erro initialize: " + e);
            }
    }

    @FXML
    private void marmitaClicked(ActionEvent event) {
        if (tabButtonLeft.isSelected()) {
            try {
                Pane marmitaPane = FXMLLoader.load(getClass().getResource("/fxml/paneMarmita.fxml"));
                APMarmitaProduto.getChildren().clear();
                APMarmitaProduto.getChildren().add(marmitaPane);
                tabButtonRight.setSelected(false);
            } catch (Exception e) {
                System.out.println("Erro marmitaClicked: " + e);
            }
        } else {
            tabButtonLeft.setSelected(true);
        }
    }

    @FXML
    private void produtoClicked(ActionEvent event) {
        if (tabButtonRight.isSelected()) {
            try {
                Pane produtoPane = FXMLLoader.load(getClass().getResource("/fxml/paneProduto.fxml"));
                APMarmitaProduto.getChildren().clear();
                APMarmitaProduto.getChildren().add(produtoPane);
                tabButtonLeft.setSelected(false);
            } catch (Exception e) {
                System.out.println("Erro produtoClicked: " + e);
            }
        } else {
            tabButtonRight.setSelected(true);
        }
    }
}
