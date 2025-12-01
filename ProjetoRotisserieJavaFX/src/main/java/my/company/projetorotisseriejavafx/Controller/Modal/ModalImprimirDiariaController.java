package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.DAO.MotoboyDAO;
import my.company.projetorotisseriejavafx.Objects.Motoboy;
import my.company.projetorotisseriejavafx.Util.CssHelper;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
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
    private AnchorPane root;

    @FXML
    public void initialize(Motoboy motoboy) {
        initDPData();
        loadCBMotoboy(motoboy);
    }

    @FXML
    void imprimir(ActionEvent event) {
        if (DPData.getValue() != null && CBMotoboy.getValue() != null) {
            abrirModalDiaria(CBMotoboy.getValue(), DPData.getValue());
        }
    }

    public void loadCBMotoboy(Motoboy motoboy) {
        try {
            CBMotoboy.getItems().clear();

            List<Motoboy> motoboys = MotoboyDAO.read();

            if (!motoboys.isEmpty()) {
                CBMotoboy.getItems().addAll(motoboys);
                if (motoboy != null) {
                    CBMotoboy.getSelectionModel().select(motoboy);
                } else {
                    CBMotoboy.getSelectionModel().selectFirst();
                }
            }
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "motoboy");
        }
    }

    public void initDPData() {
        DPData.setEditable(false);
    }

    public void abrirModalDiaria(Motoboy motoboy, LocalDate data) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalDiaria.fxml"));

            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);

            modal.setScene(scene);

            ModalDiariaController controller = loader.getController();

            controller.initialize(motoboy, data);

            modal.initModality(Modality.APPLICATION_MODAL);
            modal.initStyle(StageStyle.UTILITY);
            fecharModal();
            modal.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fecharModal() {
        Stage modal =  (Stage) root.getScene().getWindow();
        modal.close();
    }

}
