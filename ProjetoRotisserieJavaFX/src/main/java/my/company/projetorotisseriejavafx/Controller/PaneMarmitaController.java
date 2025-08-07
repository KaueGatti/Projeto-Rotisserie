/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package my.company.projetorotisseriejavafx.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.DAO.MarmitaDAO;
import my.company.projetorotisseriejavafx.Objects.Marmita;
import my.company.projetorotisseriejavafx.Objects.MarmitaVendida;

public class PaneMarmitaController implements Initializable {

    private int misturas;
    private int guarnicoes;
    private NovoPedidoController controller;

    @FXML
    private ComboBox comboBox;

    private Marmita tipo;

    private MarmitaVendida marmita = new MarmitaVendida();

    @FXML
    private Pane paneMarmita;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadMarmitas();
        tipo = (Marmita) comboBox.getSelectionModel().getSelectedItem();
    }

    public void loadMarmitas() {
        comboBox.getItems().clear();
        for (Marmita marmita : MarmitaDAO.read()) {
            comboBox.getItems().add(marmita);
        }
        comboBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void comboBox() {
        tipo = (Marmita) comboBox.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void checkBoxMistura(ActionEvent event) {
        Object obj = event.getSource();
        if (obj instanceof CheckBox cb) {
            if (cb.isSelected()) {
                misturas++;
            } else {
                misturas--;
            }
            for (Node node : paneMarmita.getChildren()) {
                if (node instanceof CheckBox checkBox) {
                    if (checkBox.getId().contains("checkBoxMistura") && !checkBox.isSelected()) {
                        if (misturas >= tipo.getMaxMistura()) {
                            checkBox.setDisable(true);
                        } else {
                            checkBox.setDisable(false);
                        }
                    }
                }
            }
        }
    }

    @FXML
    private void checkBoxGuarnicoes(ActionEvent event) {
        Object obj = event.getSource();
        if (obj instanceof CheckBox cb) {
            if (cb.isSelected()) {
                guarnicoes++;
            } else {
                guarnicoes--;
            }
            for (Node node : paneMarmita.getChildren()) {
                if (node instanceof CheckBox checkBox) {
                    if (checkBox.getId().contains("checkBoxGuarnicao") && !checkBox.isSelected()) {
                        if (guarnicoes >= tipo.getMaxGuarnicao()) {
                            checkBox.setDisable(true);
                        } else {
                            checkBox.setDisable(false);
                        }
                    }
                }
            }
        }
    }

    @FXML
    private void bttAdicionar() {
        marmita.setMarmita(tipo);
        String detalhes = "";
        for (Node node : paneMarmita.getChildren()) {
            if (node instanceof CheckBox checkBox) {
                if (checkBox.isSelected()) {
                    detalhes += checkBox.getText();
                }
            }
        }
        marmita.setDetalhes(detalhes);
        controller.adicionarMarmita(marmita);
        
    }

    @FXML
    private void bttLimpar() {
        for (Node node : paneMarmita.getChildren()) {
            if (node instanceof CheckBox checkBox) {
                checkBox.setSelected(false);
                checkBox.setDisable(false);
            }
        }
        marmita.setObservacao("");
    }

    @FXML
    private void bttObservacao() {
        abrirModalObservacao();
    }

    public void abrirModalObservacao() {
        try {
            Stage modal = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalObservacao.fxml"));
            modal.setScene(fxmlLoader.load());

            modalObservacaoController controller = fxmlLoader.getController();

            modal.setOnCloseRequest(event -> {
                event.consume();
            });
            modal.setResizable(false);
            modal.initStyle(StageStyle.UTILITY);
            modal.setX(55);
            modal.setY(400);
            modal.showAndWait();

            marmita.setObservacao(controller.getObservacao());

        } catch (IOException e) {
            System.out.println("Erro ao abrir modal observação" + e);
        }

    }

    public void setController(NovoPedidoController controller) {
        this.controller = controller;

    }

}
