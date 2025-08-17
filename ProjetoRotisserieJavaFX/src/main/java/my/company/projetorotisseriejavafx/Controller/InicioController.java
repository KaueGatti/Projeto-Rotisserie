/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package my.company.projetorotisseriejavafx.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author kaueg
 */
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
}
