package my.company.projetorotisseriejavafx.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalAdicionarItemCardapioController;
import my.company.projetorotisseriejavafx.Controller.Modal.ModalAvisoNovoPedidoController;
import my.company.projetorotisseriejavafx.DAO.CardapioDAO;
import my.company.projetorotisseriejavafx.DAO.MarmitaDAO;
import my.company.projetorotisseriejavafx.Objects.Cardapio;
import my.company.projetorotisseriejavafx.Objects.Marmita;
import my.company.projetorotisseriejavafx.Objects.Pedido;
import my.company.projetorotisseriejavafx.Util.DatabaseExceptionHandler;
import my.company.projetorotisseriejavafx.Util.Printer;

import javax.print.PrintException;

public class InicioController implements Initializable {

    @FXML
    private Button bttNovoPedido;
    @FXML
    private AnchorPane APPrincipal;
    @FXML
    private Button btnPedidos;
    @FXML
    private Button btnMarmitas;
    @FXML
    private Button btnProdutos;
    @FXML
    private Button btnBairros;
    @FXML
    private Button btnMensalistas;
    @FXML
    private Button btnMotoboys;
    @FXML
    private Button btnCardapio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadIcons();
    }

    @FXML
    public void bttNovoPedido() {

        if (!validaMarmitas()) return;

        if (!validaCardapio()) return;

        APPrincipal.getChildren().clear();
        try {
            APPrincipal.getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/NovoPedido.fxml")));
        } catch (IOException e) {
            System.out.println("Erro bttNovoPedido " + e);
        }
    }

    @FXML
    private void pedido() {
        APPrincipal.getChildren().clear();
        try {
            APPrincipal.getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/Pedidos.fxml")));
        } catch (IOException e) {
            System.out.println("Erro ao abrir janela de pedidos " + e);
            e.printStackTrace();
        }
    }

    @FXML
    private void marmitas() {
        APPrincipal.getChildren().clear();
        try {
            APPrincipal.getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/Marmitas.fxml")));
        } catch (IOException e) {
            System.out.println("Erro ao carregar tela de marmitas");
            e.printStackTrace();
        }
    }

    @FXML
    private void produtos() {
        APPrincipal.getChildren().clear();
        try {
            APPrincipal.getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/Produtos.fxml")));
        } catch (IOException e) {
            System.out.println("Erro ao carregar tela de Produtos");
            e.printStackTrace();
        }
    }

    @FXML
    private void bairros() {
        APPrincipal.getChildren().clear();
        try {
            APPrincipal.getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/Bairros.fxml")));
        } catch (IOException e) {
            System.out.println("Erro ao carregar tela de Bairros");
            e.printStackTrace();
        }
    }

    @FXML
    private void mensalistas() {
        APPrincipal.getChildren().clear();
        try {
            APPrincipal.getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/Mensalistas.fxml")));
        } catch (IOException e) {
            System.out.println("Erro ao carregar tela de Mensalistas");
            e.printStackTrace();
        }
    }

    @FXML
    private void motoboys() {
        APPrincipal.getChildren().clear();
        try {
            APPrincipal.getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/Motoboys.fxml")));
        } catch (IOException e) {
            System.out.println("Erro ao carregar tela de Motoboys");
            e.printStackTrace();
        }
    }

    @FXML
    private void cardapio() {
        APPrincipal.getChildren().clear();
        try {
            APPrincipal.getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/Cardapio.fxml")));
        } catch (IOException e) {
            System.out.println("Erro ao carregar tela de Cardapio");
            e.printStackTrace();
        }
    }

    private void loadIcons() {
        Image pngNovoPedido = new Image(getClass().getResourceAsStream("/Images/NovoPedido.png"));
        ImageView ivNovoPedido = new ImageView(pngNovoPedido);
        bttNovoPedido.setGraphic(ivNovoPedido);
        Image pngPedidos = new Image(getClass().getResourceAsStream("/Images/Pedidos.png"));
        ImageView ivPedidos = new ImageView(pngPedidos);
        btnPedidos.setGraphic(ivPedidos);
        Image pngMarmitas = new Image(getClass().getResourceAsStream("/Images/Marmitas.png"));
        ImageView ivMarmitas = new ImageView(pngMarmitas);
        btnMarmitas.setGraphic(ivMarmitas);
        Image pngProdutos = new Image(getClass().getResourceAsStream("/Images/Produtos.png"));
        ImageView ivProdutos = new ImageView(pngProdutos);
        btnProdutos.setGraphic(ivProdutos);
        Image pngBairros = new Image(getClass().getResourceAsStream("/Images/Bairros.png"));
        ImageView ivBairros = new ImageView(pngBairros);
        btnBairros.setGraphic(ivBairros);
        Image pngMensalistas = new Image(getClass().getResourceAsStream("/Images/Mensalistas.png"));
        ImageView ivMensalistas = new ImageView(pngMensalistas);
        btnMensalistas.setGraphic(ivMensalistas);
        Image pngMotoboys = new Image(getClass().getResourceAsStream("/Images/Motoboys.png"));
        ImageView ivMotoboys = new ImageView(pngMotoboys);
        btnMotoboys.setGraphic(ivMotoboys);
        Image pngCardapio = new Image(getClass().getResourceAsStream("/Images/Cardapio.png"));
        ImageView ivCardapio = new ImageView(pngCardapio);
        btnCardapio.setGraphic(ivCardapio);
    }

    public boolean validaMarmitas() {
        try {
            if (MarmitaDAO.read().isEmpty()) {
                String msg = "Você ainda não tem nenhuma marmita cadastrada!";
                if (abrirModalAvisoNovoPedido(msg, new Marmita())) {
                    marmitas();
                }
                return false;
            }
            return true;
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "marmita");
        }
        return false;
    }

    public boolean validaCardapio() {
        try {
            if (CardapioDAO.read() == null) {
                String msg = "Você ainda não definiu o cardápio do dia!";
                if (abrirModalAvisoNovoPedido(msg, new Cardapio())) {
                    cardapio();
                }
                return false;
            }
            return true;
        } catch (SQLException e) {
            DatabaseExceptionHandler.handleException(e, "marmita");
        }
        return false;
    }


    public boolean abrirModalAvisoNovoPedido(String msg, Object tipo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modal/modalAvisoNovoPedido.fxml"));
            Stage modal = new Stage();

            modal.setScene(loader.load());

            ModalAvisoNovoPedidoController controller = loader.getController();

            controller.initialize(msg, tipo);

            modal.initStyle(StageStyle.UTILITY);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setResizable(false);
            modal.showAndWait();

            return controller.isCadastrar();

        } catch (IOException e) {
            System.out.println("Erro ao abrir adicionar item cardapio");
            e.printStackTrace();
        }

        return false;
    }
}
