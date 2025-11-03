package my.company.projetorotisseriejavafx.Util;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DatabaseExceptionHandler {

    public static void handleException(Exception e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            handleIntegrityViolation((SQLIntegrityConstraintViolationException) e);
        } else if (e instanceof SQLException) {
            handleSQLException((SQLException) e);
        } else {
            handleGeneralException(e);
        }
    }

    private static void handleIntegrityViolation(SQLIntegrityConstraintViolationException e) {
        String message = e.getMessage().toLowerCase();
        String userMessage;

        if (message.contains("duplicate entry")) {
            if (message.contains("nome") || message.contains("uk_nome")) {
                userMessage = "Já existe uma marmita com este nome!";
            } else {
                userMessage = "Este registro já existe no sistema!";
            }
        } else if (message.contains("foreign key")) {
            if (message.contains("delete")) {
                userMessage = "Não é possível excluir! Existem registros relacionados.";
            } else {
                userMessage = "Referência inválida! Verifique os dados relacionados.";
            }
        } else {
            userMessage = "Erro de integridade no banco de dados!";
        }

        showError("Erro de Integridade", userMessage);
    }

    private static void handleSQLException(SQLException e) {
        String userMessage = switch (e.getErrorCode()) {
            case 1062 -> "Registro duplicado!";
            case 1451 -> "Não é possível excluir! Existem registros dependentes.";
            case 1452 -> "Referência inválida! Verifique os dados relacionados.";
            case 1048 -> "Um campo obrigatório não foi preenchido!";
            case 1406 -> "Dados muito longos para o campo!";
            case 1045 -> "Erro de autenticação no banco de dados!";
            case 1049 -> "Banco de dados não encontrado!";
            case 0 -> "Erro de conexão com o banco de dados!";
            default -> "Erro no banco de dados: " + e.getMessage();
        };

        showError("Erro no Banco de Dados", userMessage);
        e.printStackTrace();
    }

    private static void handleGeneralException(Exception e) {
        showError("Erro", "Ocorreu um erro inesperado: " + e.getMessage());
        e.printStackTrace();
    }

    private static void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showSuccess(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showWarning(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
