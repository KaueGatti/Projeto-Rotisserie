package my.company.projetorotisseriejavafx.Controller.Modal;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import my.company.projetorotisseriejavafx.Objects.MarmitaVendida;

public class ModalDetalhesMarmitaController implements Initializable {


    @FXML
    private AnchorPane root;
    @FXML
    private TextField TFMarmita;
    @FXML
    private TextArea TADetalhes;
    @FXML
    private TextArea TAObservacoes;
    @FXML
    private TextField TFSubtotal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void load(MarmitaVendida marmita) {
        TFMarmita.setText(marmita.getNome());
        TADetalhes.setText(marmita.getDetalhes());
        TAObservacoes.setText(marmita.getObservacao());
        TFSubtotal.setText(String.format("R$ %.2f",marmita.getSubtotal()).replace(".", ","));
    }

}