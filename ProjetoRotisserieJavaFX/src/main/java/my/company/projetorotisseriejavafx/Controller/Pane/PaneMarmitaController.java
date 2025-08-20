/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package my.company.projetorotisseriejavafx.Controller.Pane;

import java.io.IOException;
import java.net.URL;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Controller.NovoPedidoController;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalObservacaoController;
import my.company.projetorotisseriejavafx.DAO.MarmitaDAO;
import my.company.projetorotisseriejavafx.Objects.Marmita;
import my.company.projetorotisseriejavafx.Objects.MarmitaVendida;

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
    private ComboBox comboBox;
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
    private CheckBox checkBoxSalada;
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
    }

    public void loadMarmitas() {
        comboBox.getItems().clear();
        for (Marmita marmita : MarmitaDAO.read()) {
            comboBox.getItems().add(marmita);
        }
        comboBox.getSelectionModel().selectFirst();
        maxMisturas = ((Marmita) comboBox.getSelectionModel().getSelectedItem()).getMaxMistura();
        maxGuarnicoes = ((Marmita) comboBox.getSelectionModel().getSelectedItem()).getMaxGuarnicao();

        comboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            maxMisturas = ((Marmita) newVal).getMaxMistura();
            maxGuarnicoes = ((Marmita) newVal).getMaxGuarnicao();
            labelMistura.setText("Misturas: " + maxMisturas);
            labelGuarnicao.setText("Guarnições: " + maxGuarnicoes);
        });

        labelMistura.setText("Misturas: " + maxMisturas);
        labelGuarnicao.setText("Guarnições: " + maxGuarnicoes);
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

            marmita.setMarmita((Marmita) comboBox.getSelectionModel().getSelectedItem());

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
                            marmita.setSalada(checkBox.getText());
                        }
                    }
                }
            }

            marmita.setPrincipais(principais);
            marmita.setMisturas(misturas);
            marmita.setGuarnições(guarnicoes);

            String detalhes = "Principais: ";

            for (int i = 0; i < principais.size(); i++) {
                if (i != (principais.size() - 1)) {
                    detalhes += principais.get(i) + ", ";
                } else {
                    detalhes += principais.get(i);
                }
            }

            detalhes += "\n\nMisturas: ";

            for (int i = 0; i < misturas.size(); i++) {
                if (i != (misturas.size() - 1)) {
                    detalhes += misturas.get(i) + ", ";
                } else {
                    detalhes += misturas.get(i);
                }
            }

            detalhes += "\n\nGuarnições: ";

            for (int i = 0; i < guarnicoes.size(); i++) {
                if (i != (guarnicoes.size() - 1)) {
                    detalhes += guarnicoes.get(i) + ", ";
                } else {
                    detalhes += guarnicoes.get(i);
                }
            }

            if (marmita.getSalada() != null) {
                detalhes += "\n\nSalada: " + marmita.getSalada();
            } else {
                detalhes += "\n\nSalada: Não";
            }

            marmita.setDetalhes(detalhes);
            marmita.setObservacao(observacao);

            double valorAdicionais = 2 * (misturasAdicionais + guarnicoesAdicionais);

            marmita.setSubtotal(valorAdicionais + marmita.getMarmita().getValor());

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
        abrirModalObservacao();
    }

    public void abrirModalObservacao() {
        try {
            Stage modal = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalObservacao.fxml"));
            modal.setScene(fxmlLoader.load());

            ModalObservacaoController controller = fxmlLoader.getController();

            controller.loadObservacao(observacao);

            modal.setOnCloseRequest(event -> {
                event.consume();
            });
            modal.setResizable(false);
            modal.initStyle(StageStyle.UTILITY);
            modal.setX(55);
            modal.setY(400);
            modal.showAndWait();

            if (controller.getObservacao() != null) {
                observacao = controller.getObservacao();
            }

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
}
