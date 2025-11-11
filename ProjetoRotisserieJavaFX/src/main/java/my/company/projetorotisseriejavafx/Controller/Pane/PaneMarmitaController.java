/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package my.company.projetorotisseriejavafx.Controller.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;

import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Controller.NovoPedidoController;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalObservacaoController;
import my.company.projetorotisseriejavafx.DAO.CardapioDAO;
import my.company.projetorotisseriejavafx.DAO.MarmitaDAO;
import my.company.projetorotisseriejavafx.Objects.Cardapio;
import my.company.projetorotisseriejavafx.Objects.Marmita;
import my.company.projetorotisseriejavafx.Objects.MarmitaVendida;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;

public class PaneMarmitaController implements Initializable {

    private NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    private int maxMisturas;
    private int maxGuarnicoes;
    private int misturas;
    private int guarnicoes;

    private int misturasAdicionais;
    private int guarnicoesAdicionais;

    private NovoPedidoController controller;
    private String observacao;

    @FXML
    private ComboBox<Marmita> comboBoxMarmita;
    @FXML
    private Pane paneMarmita;
    @FXML
    private Button bttAdicionar;
    @FXML
    private Button btnLimpar;
    @FXML
    private CheckBox checkBoxMistura1;
    @FXML
    private CheckBox checkBoxMistura2;
    @FXML
    private CheckBox checkBoxMistura3;
    @FXML
    private CheckBox checkBoxMistura4;
    @FXML
    private CheckBox checkBoxGuarnicao1;
    @FXML
    private CheckBox checkBoxGuarnicao2;
    @FXML
    private CheckBox checkBoxGuarnicao3;
    @FXML
    private CheckBox checkBoxGuarnicao4;
    @FXML
    private CheckBox checkBoxSalada1;
    @FXML
    private CheckBox checkBoxSalada2;
    @FXML
    private Label labelInfoMistura;
    @FXML
    private Label labelInfoGuarnicao;
    private Spinner<Integer> spinnerMistura;
    @FXML
    private Button btnAddGuarnicao;
    @FXML
    private Button btnSubGuarnicao;
    @FXML
    private Button btnAddMistura;
    @FXML
    private Button btnSubMistura;
    @FXML
    private Label labelMistura;
    @FXML
    private Label labelGuarnicao;
    @FXML
    private Label labelInfoMisturaAdc;
    @FXML
    private Label labelInfoGuarnicaoAdc;
    @FXML
    private Label labelInfoMarmita;
    @FXML
    private CheckBox checkBoxPrincipal1;
    @FXML
    private CheckBox checkBoxPrincipal2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadMarmitas();
        loadCardapio();
    }

    public void loadMarmitas() {
        comboBoxMarmita.getItems().clear();

        try {
            List<Marmita> marmitas = MarmitaDAO.read();

            if (marmitas.isEmpty()) {
                return;
            }

            comboBoxMarmita.getItems().addAll(marmitas);
            comboBoxMarmita.getSelectionModel().selectFirst();
            maxMisturas = comboBoxMarmita.getSelectionModel().getSelectedItem().getMaxMistura();
            maxGuarnicoes = comboBoxMarmita.getSelectionModel().getSelectedItem().getMaxGuarnicao();

            comboBoxMarmita.valueProperty().addListener((obs, oldVal, newVal) -> {
                maxMisturas = newVal.getMaxMistura();
                maxGuarnicoes = newVal.getMaxGuarnicao();
                labelMistura.setText("Misturas: " + maxMisturas);
                labelGuarnicao.setText("Guarnições: " + maxGuarnicoes);
            });

            labelMistura.setText("Misturas: " + maxMisturas);
            labelGuarnicao.setText("Guarnições: " + maxGuarnicoes);

        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "marmita");
        }
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
                        if (misturas >= (maxMisturas + misturasAdicionais)) {
                            checkBox.setDisable(true);
                        } else {
                            checkBox.setDisable(false);
                        }
                    }
                }
            }
            if (!cb.isSelected()) {
                cb.setDisable(false);
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
                        if (guarnicoes >= (maxGuarnicoes + guarnicoesAdicionais)) {
                            checkBox.setDisable(true);
                        } else {
                            checkBox.setDisable(false);
                        }
                    }
                }
            }
            if (!cb.isSelected()) {
                cb.setDisable(false);
            }
        }
    }

    @FXML
    private void bttAdicionar() {
        if (marmitaIsValid()) {

            MarmitaVendida marmita = new MarmitaVendida();

            List<String> principais = new ArrayList<>();
            List<String> misturas = new ArrayList<>();
            List<String> guarnicoes = new ArrayList<>();
            List<String> saladas = new ArrayList<>();

            marmita.setIdMarmita(comboBoxMarmita.getValue().getId());
            marmita.setNome(comboBoxMarmita.getValue().getNome());

            for (Node node : paneMarmita.getChildren()) {
                if (node instanceof CheckBox checkBox) {
                    if (checkBox.isSelected()) {
                        if (checkBox.getId().contains("Principal")) {
                            principais.add(checkBox.getText());
                        } else if (checkBox.getId().contains("Mistura")) {
                            misturas.add(checkBox.getText());
                        } else if (checkBox.getId().contains("Guarnicao")) {
                            guarnicoes.add(checkBox.getText());
                        } else {
                            saladas.add(checkBox.getText());
                        }
                    }
                }
            }

            marmita.setPrincipais(principais);
            marmita.setMisturas(misturas);
            marmita.setGuarnicoes(guarnicoes);
            marmita.setSalada(saladas);

            marmita.setObservacao(observacao);

            double valorAdicionais = 2 * (misturasAdicionais + guarnicoesAdicionais);

            marmita.setSubtotal(valorAdicionais + comboBoxMarmita.getValue().getValor());

            controller.adicionarMarmita(marmita);

            limpar();
        } else {
            labelInfoMarmita.setText("Adicione ao menos um item a marmita");
        }
    }

    @FXML
    private void limpar() {
        for (Node node : paneMarmita.getChildren()) {
            if (node instanceof CheckBox checkBox) {
                checkBox.setSelected(false);
                checkBox.setDisable(false);
            }
        }
        observacao = "";
        labelGuarnicao.setText("Guarnições: " + maxGuarnicoes);
        labelMistura.setText("Misturas: " + maxMisturas);
        misturasAdicionais = 0;
        guarnicoesAdicionais = 0;
        labelInfoMisturaAdc.setText("+R$0,00");
        labelInfoGuarnicaoAdc.setText("+R$0,00");
    }

    @FXML
    private void bttObservacao() {
        abrirModalObservacao(observacao);
    }

    public void abrirModalObservacao(String observacao) {
        try {
            Stage modal = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalObservacao.fxml"));
            modal.setScene(fxmlLoader.load());

            ModalObservacaoController controller = fxmlLoader.getController();

            controller.initialize(observacao);

            modal.setResizable(false);
            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setX(55);
            modal.setY(400);
            modal.showAndWait();

            this.observacao = controller.getObservacao();

        } catch (IOException e) {
            System.out.println("Erro ao abrir modal observação" + e);
        }

    }

    public void setController(NovoPedidoController controller) {
        this.controller = controller;
    }

    @FXML
    private void addGuarnicao(ActionEvent event) {
        if ((guarnicoesAdicionais + maxGuarnicoes) < 4) {
            guarnicoesAdicionais++;

            for (Node node : paneMarmita.getChildren()) {
                if (node instanceof CheckBox checkBox) {
                    if (checkBox.getId().contains("checkBoxGuarnicao")) {
                        if (guarnicoes >= maxGuarnicoes + guarnicoesAdicionais) {
                            checkBox.setDisable(true);
                        } else {
                            checkBox.setDisable(false);
                        }
                    }
                }
            }

            labelGuarnicao.setText("Guarnições: " + (maxGuarnicoes + guarnicoesAdicionais));
            labelInfoGuarnicaoAdc.setText("+" + (formatoMoeda.format(guarnicoesAdicionais * 2)));
        }
    }

    @FXML
    private void addMistura(ActionEvent event) {
        if ((misturasAdicionais + maxMisturas) < 4) {
            misturasAdicionais++;
            for (Node node : paneMarmita.getChildren()) {
                if (node instanceof CheckBox checkBox) {
                    if (checkBox.getId().contains("checkBoxMistura")) {
                        if (misturas >= (maxMisturas + misturasAdicionais)) {
                            checkBox.setDisable(true);
                        } else {
                            checkBox.setDisable(false);
                        }
                    }
                }
            }
            labelMistura.setText("Misturas: " + (maxMisturas + misturasAdicionais));
            labelInfoMisturaAdc.setText("+" + (formatoMoeda.format(misturasAdicionais * 2)));
        }
    }

    @FXML
    private void subGuarnicao(ActionEvent event) {
        if ((guarnicoesAdicionais + maxGuarnicoes) > maxGuarnicoes) {
            if (guarnicoes < (guarnicoesAdicionais + maxGuarnicoes)) {
                guarnicoesAdicionais--;
                if (!(guarnicoes < (guarnicoesAdicionais + maxGuarnicoes))) {
                    for (Node node : paneMarmita.getChildren()) {
                        if (node instanceof CheckBox checkBox) {
                            if (checkBox.getId().contains("checkBoxGuarnicao") && !checkBox.isSelected()) {
                                checkBox.setDisable(true);
                                labelInfoGuarnicao.setText("");
                            }
                        }
                    }
                }
                labelGuarnicao.setText("Guarnições: " + (maxGuarnicoes + guarnicoesAdicionais));
                labelInfoGuarnicaoAdc.setText("+" + (formatoMoeda.format(guarnicoesAdicionais * 2)));
            } else {
                labelInfoGuarnicao.setText("Remova um guarnição");
            }

        }
    }

    @FXML
    private void subMistura(ActionEvent event) {
        if ((misturasAdicionais + maxMisturas) > maxMisturas) {
            if (misturas < (misturasAdicionais + maxMisturas)) {
                misturasAdicionais--;
                if (!(misturas < (misturasAdicionais + maxMisturas))) {
                    for (Node node : paneMarmita.getChildren()) {
                        if (node instanceof CheckBox checkBox) {
                            if (checkBox.getId().contains("checkBoxMistura") && !checkBox.isSelected()) {
                                checkBox.setDisable(true);
                                labelInfoMistura.setText("");
                            }
                        }
                    }
                }
                labelMistura.setText("Misturas: " + (maxMisturas + misturasAdicionais));
                labelInfoMisturaAdc.setText("+" + (formatoMoeda.format(misturasAdicionais * 2)));
            } else {
                labelInfoMistura.setText("Remova uma mistura");
            }
        }
    }

    private boolean marmitaIsValid() {
        for (Node node : paneMarmita.getChildren()) {
            if (node instanceof CheckBox checkBox) {
                if (checkBox.isSelected()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void loadCardapio() {
        try {
            Cardapio cardapio = CardapioDAO.read();

            checkBoxPrincipal1.setText(cardapio.getPrincipal1());
            checkBoxPrincipal2.setText(cardapio.getPrincipal2());
            checkBoxMistura1.setText(cardapio.getMistura1());
            checkBoxMistura2.setText(cardapio.getMistura2());
            checkBoxMistura3.setText(cardapio.getMistura3());
            checkBoxMistura4.setText(cardapio.getMistura4());
            checkBoxGuarnicao1.setText(cardapio.getGuarnicao1());
            checkBoxGuarnicao2.setText(cardapio.getGuarnicao2());
            checkBoxGuarnicao3.setText(cardapio.getGuarnicao3());
            checkBoxGuarnicao4.setText(cardapio.getGuarnicao4());
            checkBoxSalada1.setText(cardapio.getSalada1());
            checkBoxSalada2.setText(cardapio.getSalada2());
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "cardapio");
        }
    }
}
