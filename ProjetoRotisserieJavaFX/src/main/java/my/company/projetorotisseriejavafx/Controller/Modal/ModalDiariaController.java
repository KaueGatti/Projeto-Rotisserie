package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import my.company.projetorotisseriejavafx.DAO.DiariaDAO;
import my.company.projetorotisseriejavafx.Objects.Diaria;
import my.company.projetorotisseriejavafx.Objects.Motoboy;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

import java.sql.SQLException;
import java.time.LocalDate;

public class ModalDiariaController {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField TFData;

    @FXML
    private TextField TFEntregas;

    @FXML
    private TextField TFMotoboy;

    @FXML
    private TextField TFValorDiaria;

    @FXML
    private TextField TFValorEntregas;

    @FXML
    private TextField TFValorTotalDiaria;

    public void initialize(Motoboy motoboy, LocalDate data) {
        loadCampos(motoboy, data);
    }

    public void fecharModal() {
        Stage modal =  (Stage) root.getScene().getWindow();
        modal.close();
    }

    public void loadCampos(Motoboy motoboy, LocalDate data) {
        try {
            Diaria diaria = DiariaDAO.read(data);

            TFMotoboy.setText(motoboy.getNome());
            TFData.setText(data.toString());

            TFEntregas.setText(String.valueOf(diaria.getEntregas()));
            TFValorEntregas.setText(String.valueOf(diaria.getValorEntregas()));
            TFValorDiaria.setText(String.valueOf(motoboy.getValorDiaria()));
            TFValorTotalDiaria.setText(String.valueOf(diaria.getValorEntregas() + motoboy.getValorDiaria()));

        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "diária");
        }
    }

}
