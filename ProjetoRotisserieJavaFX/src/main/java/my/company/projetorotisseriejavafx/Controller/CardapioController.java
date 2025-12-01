package my.company.projetorotisseriejavafx.Controller;

import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalAdicionarItemCardapioController;
import my.company.projetorotisseriejavafx.DAO.CardapioDAO;
import my.company.projetorotisseriejavafx.DAO.ItemCardapioDAO;
import my.company.projetorotisseriejavafx.Objects.Cardapio;
import my.company.projetorotisseriejavafx.Objects.ItemCardapio;
import my.company.projetorotisseriejavafx.Util.CssHelper;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;
import my.company.projetorotisseriejavafx.Util.IconHelper;
import my.company.projetorotisseriejavafx.Util.ItemCardapioListListener;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class CardapioController implements Initializable {

    Cardapio cardapio;

    ObservableList<ItemCardapio> itensCardapio = FXCollections.observableArrayList();

    FilteredList<ItemCardapio> principais;
    FilteredList<ItemCardapio> misturas;
    FilteredList<ItemCardapio> guarnicoes;
    FilteredList<ItemCardapio> saladas;

    private List<ComboBox<ItemCardapio>> grupoCBPrincipal;
    private List<ComboBox<ItemCardapio>> grupoCBMistura;
    private List<ComboBox<ItemCardapio>> grupoCBGuarnicao;
    private List<ComboBox<ItemCardapio>> grupoCBSalada;

    private List<ComboBox<ItemCardapio>> todosComboBoxes;

    @FXML
    private ComboBox<ItemCardapio> CBGuarnicao1;

    @FXML
    private ComboBox<ItemCardapio> CBGuarnicao2;

    @FXML
    private ComboBox<ItemCardapio> CBGuarnicao3;

    @FXML
    private ComboBox<ItemCardapio> CBGuarnicao4;

    @FXML
    private ComboBox<ItemCardapio> CBMistura1;

    @FXML
    private ComboBox<ItemCardapio> CBMistura2;

    @FXML
    private ComboBox<ItemCardapio> CBMistura3;

    @FXML
    private ComboBox<ItemCardapio> CBMistura4;

    @FXML
    private ComboBox<ItemCardapio> CBPrincipal1;

    @FXML
    private ComboBox<ItemCardapio> CBPrincipal2;

    @FXML
    private ComboBox<ItemCardapio> CBSalada1;

    @FXML
    private ComboBox<ItemCardapio> CBSalada2;

    @FXML
    private Button btnAdicionarGuarnicao;

    @FXML
    private Button btnAdicionarMistura;

    @FXML
    private Button btnAdicionarPrincipal;

    @FXML
    private Button btnAdicionarSalada;

    @FXML
    private Button btnAtualizar;

    @FXML
    private TableColumn<ItemCardapio, String> colNomePrincipal;
    @FXML
    private TableColumn<ItemCardapio, Void> colDelPrincipal;

    @FXML
    private TableColumn<ItemCardapio, String> colNomeMistura;
    @FXML
    private TableColumn<ItemCardapio, Void> colDelMistura;

    @FXML
    private TableColumn<ItemCardapio, String> colNomeGuarnicao;
    @FXML
    private TableColumn<ItemCardapio, Void> colDelGuarnicao;

    @FXML
    private TableColumn<ItemCardapio, String> colNomeSalada;
    @FXML
    private TableColumn<ItemCardapio, Void> colDelSalada;

    @FXML
    private TableView<ItemCardapio> tableGuarnicoes;

    @FXML
    private TableView<ItemCardapio> tableMisturas;

    @FXML
    private TableView<ItemCardapio> tablePrincipais;

    @FXML
    private TableView<ItemCardapio> tableSaladas;

    @FXML
    private Pane panePrincipal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initObservableLists();
        initTables();
        initComboBoxs();
    }

    @FXML
    void atualizar(ActionEvent event) {

        if (btnAtualizar.getText().equals("Atualizar")) {
            if (!validaCardapio()) return;

            btnAtualizar.setText("Salvar");

            btnAtualizar.getStyleClass().remove("button-blue");
            btnAtualizar.getStyleClass().add("button-green");

            disableTables(true);

            if (cardapio == null) {
                CBPrincipal1.getSelectionModel().select(0);
                CBPrincipal2.getSelectionModel().select(1);
                CBMistura1.getSelectionModel().select(0);
                CBMistura2.getSelectionModel().select(1);
                CBMistura3.getSelectionModel().select(2);
                CBMistura4.getSelectionModel().select(3);
                CBGuarnicao1.getSelectionModel().select(0);
                CBGuarnicao2.getSelectionModel().select(1);
                CBGuarnicao3.getSelectionModel().select(2);
                CBGuarnicao4.getSelectionModel().select(3);
                CBSalada1.getSelectionModel().select(0);
                CBSalada2.getSelectionModel().select(1);
            }

            todosComboBoxes.forEach(cb -> {
                cb.setDisable(false);
            });


            return;
        }

        if (btnAtualizar.getText().equals("Salvar")) {

            try {
                Cardapio cardapio = new Cardapio();

                cardapio.setPrincipal1(CBPrincipal1.getValue().getNome());
                cardapio.setPrincipal2(CBPrincipal2.getValue().getNome());
                cardapio.setMistura1(CBMistura1.getValue().getNome());
                cardapio.setMistura2(CBMistura2.getValue().getNome());
                cardapio.setMistura3(CBMistura3.getValue().getNome());
                cardapio.setMistura4(CBMistura4.getValue().getNome());
                cardapio.setGuarnicao1(CBGuarnicao1.getValue().getNome());
                cardapio.setGuarnicao2(CBGuarnicao2.getValue().getNome());
                cardapio.setGuarnicao3(CBGuarnicao3.getValue().getNome());
                cardapio.setGuarnicao4(CBGuarnicao4.getValue().getNome());
                cardapio.setSalada1(CBSalada1.getValue().getNome());
                cardapio.setSalada2(CBSalada2.getValue().getNome());

                CardapioDAO.create(cardapio);

                disableTables(false);

                todosComboBoxes.forEach(cb -> {
                    cb.setDisable(true);
                });

                btnAtualizar.setText("Atualizar");
                btnAtualizar.getStyleClass().remove("button-green");
                btnAtualizar.getStyleClass().add("button-blue");

            } catch (SQLException e) {
                DatabaseExceptionHandler.handleException(e, "cardápio");
            }
        }
    }

    @FXML
    void adicionarPrincipal(ActionEvent event) {
        abrirModalAdicionar("Principal");
    }

    @FXML
    void adicionarMistura(ActionEvent event) {
        abrirModalAdicionar("Mistura");
    }

    @FXML
    void adicionarGuarnicao(ActionEvent event) {
        abrirModalAdicionar("Guarnição");
    }

    @FXML
    void adicionarSalada(ActionEvent event) {
        abrirModalAdicionar("Salada");
    }

    public void abrirModalAdicionar(String categoria) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalAdicionarItemCardapio.fxml"));
            Parent root = loader.load();

            Stage modal = new Stage();
            Scene scene = new Scene(root);

            CssHelper.loadCss(scene);

            modal.setScene(scene);

            ModalAdicionarItemCardapioController controller = loader.getController();

            controller.initialize(categoria, itensCardapio);

            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setResizable(false);
            modal.showAndWait();

        } catch (IOException e) {
            System.out.println("Erro ao abrir adicionar item cardapio");
            e.printStackTrace();
        }
    }

    public void initTables() {
        initTablePrincipais();
        initTableMisturas();
        initTableGuarnicoes();
        initTableSaladas();
    }

    public void initTablePrincipais() {
        colNomePrincipal.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDelPrincipal.setCellFactory(param -> buttonDelete());

        tablePrincipais.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    tablePrincipais.getSelectionModel().clearSelection();
                }
            }
        });

        tablePrincipais.addEventFilter(ScrollEvent.SCROLL, event -> {
            event.consume();
        });

        tablePrincipais.setItems(principais);

    }

    public void initTableMisturas() {
        colNomeMistura.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDelMistura.setCellFactory(param -> buttonDelete());

        tableMisturas.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    tableMisturas.getSelectionModel().clearSelection();
                }
            }
        });

        tableMisturas.addEventFilter(ScrollEvent.SCROLL, event -> {
            event.consume();
        });

        tableMisturas.setItems(misturas);
    }

    public void initTableGuarnicoes() {
        colNomeGuarnicao.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDelGuarnicao.setCellFactory(param -> buttonDelete());

        tableGuarnicoes.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    tableGuarnicoes.getSelectionModel().clearSelection();
                }
            }
        });

        tableGuarnicoes.addEventFilter(ScrollEvent.SCROLL, event -> {
            event.consume();
        });

        tableGuarnicoes.setItems(guarnicoes);
    }

    public void initTableSaladas() {
        colNomeSalada.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDelSalada.setCellFactory(param -> buttonDelete());

        tableSaladas.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    tableSaladas.getSelectionModel().clearSelection();
                }
            }
        });

        tableMisturas.addEventFilter(ScrollEvent.SCROLL, event -> {
            event.consume();
        });

        tableSaladas.setItems(saladas);
    }

    public void initObservableLists() {
        try {
            itensCardapio = FXCollections.observableArrayList(ItemCardapioDAO.read());
            itensCardapio.addListener(new ItemCardapioListListener());

            principais = new FilteredList<>(itensCardapio, i -> i.getCategoria().equalsIgnoreCase("Principal"));
            misturas = new FilteredList<>(itensCardapio, i -> i.getCategoria().equalsIgnoreCase("Mistura"));
            guarnicoes = new FilteredList<>(itensCardapio, i -> i.getCategoria().equalsIgnoreCase("Guarnição"));
            saladas = new FilteredList<>(itensCardapio, i -> i.getCategoria().equalsIgnoreCase("Salada"));

        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "item do cardápio");
        }
    }

    public void initComboBoxs() {
        grupoCBPrincipal = Arrays.asList(CBPrincipal1, CBPrincipal2);
        grupoCBMistura = Arrays.asList(CBMistura1, CBMistura2, CBMistura3, CBMistura4);
        grupoCBGuarnicao = Arrays.asList(CBGuarnicao1, CBGuarnicao2, CBGuarnicao3, CBGuarnicao4);
        grupoCBSalada = Arrays.asList(CBSalada1, CBSalada2);

        todosComboBoxes = new ArrayList<>();
        todosComboBoxes.addAll(grupoCBPrincipal);
        todosComboBoxes.addAll(grupoCBMistura);
        todosComboBoxes.addAll(grupoCBGuarnicao);
        todosComboBoxes.addAll(grupoCBSalada);

        configurarGrupo(grupoCBPrincipal, principais);
        configurarGrupo(grupoCBMistura, misturas);
        configurarGrupo(grupoCBGuarnicao, guarnicoes);
        configurarGrupo(grupoCBSalada, saladas);

        try {
            cardapio = CardapioDAO.read();

            initCBPrincipal(cardapio);
            initCBMistura(cardapio);
            initCBGuarnicao(cardapio);
            initCBSalada(cardapio);

        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "cardapio");
        }

    }

    public void initCBPrincipal(Cardapio cardapio) {
        if (cardapio != null) {
            CBPrincipal1.getItems().forEach(item -> {
                if (item.getNome().equals(cardapio.getPrincipal1())) {
                    CBPrincipal1.setValue(item);
                }
            });
            CBPrincipal2.getItems().forEach(item -> {
                if (item.getNome().equals(cardapio.getPrincipal2())) {
                    CBPrincipal2.setValue(item);
                }
            });
        }
    }

    public void initCBMistura(Cardapio cardapio) {
        if (cardapio != null) {
            CBMistura1.getItems().forEach(item -> {
                if (item.getNome().equals(cardapio.getMistura1())) {
                    CBMistura1.setValue(item);
                }
            });
            CBMistura2.getItems().forEach(item -> {
                if (item.getNome().equals(cardapio.getMistura2())) {
                    CBMistura2.setValue(item);
                }
            });
            CBMistura3.getItems().forEach(item -> {
                if (item.getNome().equals(cardapio.getMistura3())) {
                    CBMistura3.setValue(item);
                }
            });
            CBMistura4.getItems().forEach(item -> {
                if (item.getNome().equals(cardapio.getMistura4())) {
                    CBMistura4.setValue(item);
                }
            });
        }
    }

    public void initCBGuarnicao(Cardapio cardapio) {

        if (cardapio != null) {
            CBGuarnicao1.getItems().forEach(item -> {
                if (item.getNome().equals(cardapio.getGuarnicao1())) {
                    CBGuarnicao1.setValue(item);
                }
            });
            CBGuarnicao2.getItems().forEach(item -> {
                if (item.getNome().equals(cardapio.getGuarnicao2())) {
                    CBGuarnicao2.setValue(item);
                }
            });
            CBGuarnicao3.getItems().forEach(item -> {
                if (item.getNome().equals(cardapio.getGuarnicao3())) {
                    CBGuarnicao3.setValue(item);
                }
            });
            CBGuarnicao4.getItems().forEach(item -> {
                if (item.getNome().equals(cardapio.getGuarnicao4())) {
                    CBGuarnicao4.setValue(item);
                }
            });
        }

    }

    public void initCBSalada(Cardapio cardapio) {
        if (cardapio != null) {
            CBSalada1.getItems().forEach(item -> {
                if (item.getNome().equals(cardapio.getSalada1())) {
                    CBSalada1.setValue(item);
                }
            });
            CBSalada2.getItems().forEach(item -> {
                if (item.getNome().equals(cardapio.getSalada2())) {
                    CBSalada2.setValue(item);
                }
            });
        }
    }

    public boolean validaCardapio() {
        if (principais.size() < 2) {
            JOptionPane.showMessageDialog(null, "Adicione ao menos 2 principais ao cardápio");
            return false;
        }

        if (misturas.size() < 4) {
            JOptionPane.showMessageDialog(null, "Adicione ao menos 4 misturas ao cardápio");
            return false;
        }

        if (guarnicoes.size() < 4) {
            JOptionPane.showMessageDialog(null, "Adicione ao menos 4 guarnições ao cardápio");
            return false;
        }

        if (saladas.size() < 2) {

            JOptionPane.showMessageDialog(null, "Adicione ao menos 2 saladas ao cardápio");
            return false;
        }

        return true;
    }

    private void configurarGrupo(List<ComboBox<ItemCardapio>> grupo, ObservableList<ItemCardapio> lista) {
        for (ComboBox<ItemCardapio> comboBox : grupo) {
            comboBox.setItems(lista);

            comboBox.setCellFactory(listView -> criarCelula());
            comboBox.setButtonCell(criarCelula());

            comboBox.valueProperty().addListener((observable, valorAntigo, valorNovo) -> {
                if (valorAntigo != null)
                    valorAntigo.setDisponivel(true);
                if (valorNovo != null)
                    valorNovo.setDisponivel(false);

                for (ComboBox<ItemCardapio> cb : grupo) {
                    cb.setButtonCell(criarCelula());
                    cb.setCellFactory(listView -> criarCelula());
                }
            });
        }
    }

    private void atualizarGrupo(List<ComboBox<ItemCardapio>> grupo) {
        for (ComboBox<ItemCardapio> comboBox : grupo) {
            comboBox.setCellFactory(comboBox.getCellFactory());
        }
    }

    private ListCell<ItemCardapio> criarCelula() {
        return new ListCell<>() {
            @Override
            protected void updateItem(ItemCardapio item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setDisable(false);
                    setOpacity(1);
                } else {
                    setText(item.getNome());
                    setDisable(!item.isDisponivel());
                    setOpacity(item.isDisponivel() ? 1.0 : 0.5);
                }
            }
        };
    }

    public void disableTables(boolean disable) {
        tablePrincipais.setDisable(disable);
        tableMisturas.setDisable(disable);
        tableGuarnicoes.setDisable(disable);
        tableSaladas.setDisable(disable);

        btnAdicionarPrincipal.setDisable(disable);
        btnAdicionarMistura.setDisable(disable);
        btnAdicionarGuarnicao.setDisable(disable);
        btnAdicionarSalada.setDisable(disable);
    }

    private TableCell<ItemCardapio, Void> buttonDelete() {
        return new TableCell<ItemCardapio, Void>() {
            private final Button btnExcluir = new Button("");

            {
                btnExcluir.setMaxWidth(Double.MAX_VALUE);
                btnExcluir.getStyleClass().add("BExcluir");
                btnExcluir.getStyleClass().add("icon-delete");

                btnExcluir.setOnAction(event -> {
                    ItemCardapio itemCardapio = getTableRow().getItem();
                    boolean used = false;
                    for (ComboBox<ItemCardapio> cb : todosComboBoxes) {
                        if (cb.getValue() == itemCardapio) {
                            used = true;
                            break;
                        }
                    }

                    if (used) {
                        System.out.println("Esse item está no cardápio: " + itemCardapio.getNome());
                    } else {
                        itensCardapio.remove(itemCardapio);
                    }
                });

                HBox.setHgrow(btnExcluir, Priority.ALWAYS);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox wrapper = new HBox(btnExcluir);
                    wrapper.setSpacing(0);
                    wrapper.setPadding(new Insets(0));
                    wrapper.setFillHeight(true);

                    wrapper.setMaxWidth(Double.MAX_VALUE);

                    IconHelper.applyIcon(btnExcluir);

                    setGraphic(wrapper);
                }
            }
        };
    }
}
