package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.DAO.MotoboyDAO;
import my.company.projetorotisseriejavafx.Objects.Motoboy;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ModalImprimirDiariaController {

    @FXML
    private ComboBox<Motoboy> CBMotoboy;

    @FXML
    private DatePicker DPData;

    @FXML
    private Label LInfo;

    @FXML
    private Button btnImprimir;

    @FXML
    private Scene scene;

    @FXML
    public void initialize() {
        initDPData();
        loadCBMotoboy();
    }

    @FXML
    void imprimir(ActionEvent event) {
        abrirModalDiaria();
    }

    public void loadCBMotoboy() {
        try {
            CBMotoboy.getItems().clear();

            List<Motoboy> motoboys = MotoboyDAO.read();

            if (!motoboys.isEmpty()) {
                CBMotoboy.getItems().addAll(motoboys);
            }
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "motoboy");
        }
    }

    public void initDPData() {
        DPData.setEditable(false);
    }

    public void abrirModalDiaria(Motoboy motoboy, Date data) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalDiaria.fxml"));

            Stage modal = new Stage();
            modal.setScene(loader.load());

            modal.initModality(Modality.APPLICATION_MODAL);
            modal.initStyle(StageStyle.UTILITY);
            fecharModal();
            modal.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fecharModal() {
        Stage modal = (Stage) scene.getWindow();
        modal.close();
    }

}
