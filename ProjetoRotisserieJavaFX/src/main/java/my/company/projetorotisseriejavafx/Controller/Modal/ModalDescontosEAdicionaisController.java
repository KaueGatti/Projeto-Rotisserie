package my.company.projetorotisseriejavafx.Controller.Modal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Controller.NovoPedidoController;
import my.company.projetorotisseriejavafx.Objects.DescontoAdicional;

import java.io.IOException;
import java.util.List;

public class ModalDescontosEAdicionaisController {

    ObservableList<DescontoAdicional> descontosEAdicionais;
    NovoPedidoController novoPedidoController;

    @FXML
    private Label LAdicional;

    @FXML
    private Label LDesconto;

    @FXML
    private Button btnAdicionar;

    @FXML
    private TableColumn<DescontoAdicional, Void> colExcluir;

    @FXML
    private TableColumn<DescontoAdicional, String> colTipo;

    @FXML
    private TableColumn<DescontoAdicional, String> colValor;

    @FXML
    private TableView<DescontoAdicional> tableDescontosEAdicionais;

    public void initialize() {
        initTableDescontosEAdicionais();
    }

    public void initialize(NovoPedidoController controller, ObservableList<DescontoAdicional> descontosEAdicionais) {
        this.descontosEAdicionais = descontosEAdicionais;
        this.novoPedidoController = controller;
        initialize();
    }

    public void initialize(List<DescontoAdicional> descontosEAdicionais) {
        this.descontosEAdicionais = FXCollections.observableArrayList(descontosEAdicionais);
        initialize();
        refreshDescontoEAdicional();
        btnAdicionar.setVisible(false);
    }

    @FXML
    void adicionar(ActionEvent event) {
        abrirModalAdicionarDescontosEAdicionais();
    }

    public void abrirModalAdicionarDescontosEAdicionais() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalAdicionarDescontosEAdicionais.fxml"));
            Stage modal = new Stage();

            modal.setScene(loader.load());

            ModalAdicionarDescontosEAdicionaisController controller = loader.getController();

            controller.initialize(this);

            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setResizable(false);
            modal.showAndWait();
            refreshDescontoEAdicional();

        } catch (IOException e) {
            System.out.println("Erro ao abrir modal adicionar descontos e adicionais4");
            e.printStackTrace();
        }
    }

    private void initTableDescontosEAdicionais() {
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("formattedValor"));
        if (novoPedidoController != null) {

            colExcluir.setCellFactory(param -> new TableCell<>() {

                private final Button btnExcluir = new Button("Excluir");

                {
                    btnExcluir.setOnAction(event -> {
                        DescontoAdicional descontoAdicional = getTableView().getItems().get(getIndex());
                        descontosEAdicionais.remove(descontoAdicional);
                        refreshDescontoEAdicional();
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btnExcluir);
                    }
                }
            });
        }

        tableDescontosEAdicionais.setItems(descontosEAdicionais);
    }

    public void refreshDescontoEAdicional() {
        double valorDesconto = descontosEAdicionais.stream()
                .filter(desconto -> desconto.getTipo().equals("Desconto"))
                .mapToDouble(DescontoAdicional::getValor).sum();

        double valorAdicional = descontosEAdicionais.stream()
                .filter(adicional -> adicional.getTipo().equals("Adicional"))
                .mapToDouble(DescontoAdicional::getValor).sum();

        String descontoFormatado = String.format("R$ %.2f", valorDesconto).replace(".", ",");
        String adicionalFormatado = String.format("R$ %.2f", valorAdicional).replace(".", ",");

        LDesconto.setText(descontoFormatado);
        LAdicional.setText(adicionalFormatado);
    }

}
