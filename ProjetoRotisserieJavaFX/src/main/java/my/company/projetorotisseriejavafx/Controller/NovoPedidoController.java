package my.company.projetorotisseriejavafx.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;

import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import my.company.projetorotisseriejavafx.DAO.BairroDAO;
import my.company.projetorotisseriejavafx.DAO.MensalistaDAO;
import my.company.projetorotisseriejavafx.Objects.Bairro;
import my.company.projetorotisseriejavafx.Objects.Mensalista;

public class NovoPedidoController implements Initializable {

    @FXML
    private ToggleButton tabButtonLeft;

    @FXML
    private ToggleButton tabButtonRight;

    @FXML
    private AnchorPane APMarmitaProduto;

    @FXML
    private ComboBox comboBoxBairro;

    @FXML
    private ComboBox comboBoxMensalista;

    @FXML
    private CheckBox checkBoxMensalista;
    
    @FXML
    private RadioButton RBEntrega;
    
    @FXML
    private Pane paneEndereco;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadBairro();
        loadMensalista();
        comboBoxMensalista.setDisable(true);
        RBEntrega.setSelected(true);
        try {
            Pane marmitaPane = FXMLLoader.load(getClass().getResource("/fxml/paneMarmita.fxml"));
            APMarmitaProduto.getChildren().add(marmitaPane);
        } catch (IOException e) {
            System.out.println("Erro ao carregar PaneMarmita: " + e);
        }
    }

    @FXML
    private void checkBoxMensalista(ActionEvent event) {
        if (checkBoxMensalista.isSelected()) {
            comboBoxMensalista.setDisable(false);
        } else {
            comboBoxMensalista.setDisable(true);
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
    
    @FXML
    private void RBGTipo(ActionEvent event) {
        if (RBEntrega.isSelected()) {
            paneEndereco.setDisable(false);
        } else {
            paneEndereco.setDisable(true);
        }
    }

    private void loadBairro() {
        comboBoxBairro.getItems().clear();
        for (Bairro bairro : BairroDAO.read()) {
            comboBoxBairro.getItems().add(bairro);
        }
        comboBoxBairro.getSelectionModel().selectFirst();
    }

    private void loadMensalista() {
        comboBoxMensalista.getItems().clear();
        for (Mensalista mensalista : MensalistaDAO.read()) {
            comboBoxMensalista.getItems().add(mensalista);
        }
        comboBoxMensalista.getSelectionModel().selectFirst();
    }
}
