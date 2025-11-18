package my.company.projetorotisseriejavafx.Util;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.function.UnaryOperator;

/**
 * Classe utilitária para trabalhar com campos monetários (R$) em JavaFX
 * <p>
 * Exemplo de uso:
 * <p>
 * // Campo que aceita apenas valores positivos
 * CurrencyFieldUtil.configureField(tfValor, false);
 * <p>
 * // Campo com R$ fixo
 * CurrencyFieldUtil.configureField(tfValor, false, true, true);
 * <p>
 * // Campo que aceita valores negativos
 * CurrencyFieldUtil.configureField(tfDesconto, true);
 * <p>
 * // Obter valor do campo
 * Double valor = CurrencyFieldUtil.getValue(tfValor);
 * <p>
 * // Definir valor no campo
 * CurrencyFieldUtil.setValue(tfValor, 1250.50);
 */
public class CurrencyFieldUtil {

    private static final Locale LOCALE_BR = new Locale("pt", "BR");
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00",
            new DecimalFormatSymbols(LOCALE_BR));

    /**
     * Configura um TextField para aceitar apenas valores monetários
     *
     * @param textField     Campo a ser configurado
     * @param allowNegative Se true, permite valores negativos
     */
    public static void configureField(TextField textField, boolean allowNegative) {
        configureField(textField, allowNegative, true, false);
    }

    /**
     * Configura um TextField para aceitar apenas valores monetários
     *
     * @param textField       Campo a ser configurado
     * @param allowNegative   Se true, permite valores negativos
     * @param setInitialValue Se true, define valor inicial como 0,00
     */
    public static void configureField(TextField textField, boolean allowNegative, boolean setInitialValue) {
        configureField(textField, allowNegative, setInitialValue, false);
    }

    /**
     * Configura um TextField para aceitar apenas valores monetários
     *
     * @param textField          Campo a ser configurado
     * @param allowNegative      Se true, permite valores negativos
     * @param setInitialValue    Se true, define valor inicial como 0,00
     * @param showCurrencySymbol Se true, exibe R$ fixo no início do campo
     */
    public static void configureField(TextField textField, boolean allowNegative, boolean setInitialValue, boolean showCurrencySymbol) {
        if (showCurrencySymbol) {
            configureCurrencyFieldWithSymbol(textField, allowNegative, setInitialValue);
        } else {
            configureSimpleCurrencyField(textField, allowNegative, setInitialValue);
        }
    }

    /**
     * Configura campo monetário simples (sem R$ fixo)
     */
    private static void configureSimpleCurrencyField(TextField textField, boolean allowNegative, boolean setInitialValue) {
        textField.setTextFormatter(createCurrencyFormatter(allowNegative));

        // Define valor inicial se solicitado
        if (setInitialValue) {
            textField.setText("0,00");
        }

        // Remove espaços em branco
        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                String text = textField.getText().trim();
                textField.setText(text);
            }
        });
    }

    /**
     * Configura campo monetário com R$ fixo
     */
    private static void configureCurrencyFieldWithSymbol(TextField textField, boolean allowNegative, boolean setInitialValue) {
        final String PREFIX = "R$ ";

        // Define valor inicial
        if (setInitialValue) {
            textField.setText(PREFIX + "0,00");
        } else {
            textField.setText(PREFIX);
        }

        // Adiciona listener para manter o R$ sempre presente
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.startsWith(PREFIX)) {
                textField.setText(PREFIX);
                textField.positionCaret(textField.getText().length());
            }
        });

        // Posiciona cursor após o R$ ao ganhar foco
        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                javafx.application.Platform.runLater(() -> {
                    if (textField.getText().equals(PREFIX)) {
                        textField.positionCaret(PREFIX.length());
                    }
                });
            }
        });

        // Impede que o usuário delete ou edite o R$
        textField.setTextFormatter(createCurrencyFormatterWithPrefix(allowNegative, PREFIX));
    }

    /**
     * Cria um TextFormatter para valores monetários
     *
     * @param allowNegative Se true, permite valores negativos
     * @return TextFormatter configurado
     */
    private static TextFormatter<String> createCurrencyFormatter(boolean allowNegative) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            // Permite campo vazio
            if (newText.isEmpty()) {
                return change;
            }

            // Define o padrão regex baseado em valores negativos
            String pattern;
            if (allowNegative) {
                // Permite: -123,45 ou 123,45 ou -123.456,78
                pattern = "^-?\\d{0,12}([.,]\\d{0,2})?$";
            } else {
                // Permite apenas: 123,45 ou 123.456,78
                pattern = "^\\d{0,12}([.,]\\d{0,2})?$";
            }

            if (newText.matches(pattern)) {
                return change;
            }

            return null; // Rejeita a mudança
        };

        return new TextFormatter<>(filter);
    }

    /**
     * Cria um TextFormatter para valores monetários com prefixo R$
     *
     * @param allowNegative Se true, permite valores negativos
     * @param prefix        Prefixo fixo (R$ )
     * @return TextFormatter configurado
     */
    private static TextFormatter<String> createCurrencyFormatterWithPrefix(boolean allowNegative, String prefix) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            // Garante que sempre começa com o prefixo
            if (!newText.startsWith(prefix)) {
                return null;
            }

            // Extrai apenas a parte numérica (depois do R$ )
            String numericPart = newText.substring(prefix.length());

            // Permite vazio após o prefixo
            if (numericPart.isEmpty()) {
                return change;
            }

            // Define o padrão regex baseado em valores negativos
            String pattern;
            if (allowNegative) {
                pattern = "^-?\\d{0,12}([.,]\\d{0,2})?$";
            } else {
                pattern = "^\\d{0,12}([.,]\\d{0,2})?$";
            }

            if (numericPart.matches(pattern)) {
                return change;
            }

            return null;
        };

        return new TextFormatter<>(filter);
    }

    /**
     * Obtém o valor numérico do TextField (com ou sem prefixo R$)
     *
     * @param textField Campo de texto
     * @return Valor em Double ou null se inválido/vazio
     */
    public static Double getValue(TextField textField) {

        if (textField == null) {
            return null;
        }

        String text = textField.getText().trim();

        // Remove o prefixo R$ se existir
        text = text.replace("R$", "").trim();

        if (text.isEmpty()) {
            return null;
        }

        try {
            if (text.contains(",")) {
                text = text.replace(",", ".");
            }
            return Double.parseDouble(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Define um valor no TextField formatado como moeda
     *
     * @param textField Campo de texto
     * @param value     Valor a ser definido
     */
    public static void setValue(TextField textField, Double value) {
        if (value == null) {
            // Verifica se o campo tem prefixo R$
            if (textField.getText().startsWith("R$ ")) {
                textField.setText("R$ ");
            } else {
                textField.setText("");
            }
            return;
        }

        String formatted = DECIMAL_FORMAT.format(value);

        // Adiciona prefixo se o campo já tiver
        if (textField.getText().startsWith("R$ ")) {
            textField.setText("R$ " + formatted);
        } else {
            textField.setText(formatted);
        }
    }

    /**
     * Valida se o campo contém um valor válido
     *
     * @param textField     Campo de texto
     * @param allowNegative Se true, permite valores negativos
     * @param allowZero     Se true, permite valor zero
     * @return true se válido, false caso contrário
     */
    public static boolean isValid(TextField textField, boolean allowNegative, boolean allowZero) {
        Double value = getValue(textField);

        if (value == null) {
            return false;
        }

        if (!allowNegative && value < 0) {
            return false;
        }

        if (!allowZero && value == 0) {
            return false;
        }

        return true;
    }

    /**
     * Valida se o campo contém um valor válido (não permite zero nem negativo)
     *
     * @param textField Campo de texto
     * @return true se válido, false caso contrário
     */
    public static boolean isValid(TextField textField) {
        return isValid(textField, false, false);
    }

    /**
     * Formata um valor Double para String no formato brasileiro
     *
     * @param value Valor a ser formatado
     * @return String formatada (ex: "1.234,56")
     */
    public static String formatValue(Double value) {
        if (value == null) {
            return "0,00";
        }
        return DECIMAL_FORMAT.format(value);
    }

    /**
     * Formata um valor Double para String com prefixo R$
     *
     * @param value Valor a ser formatado
     * @return String formatada (ex: "R$ 1.234,56")
     */
    public static String formatCurrency(Double value) {
        return "R$ " + formatValue(value);
    }

    /**
     * Converte uma String no formato brasileiro para Double
     *
     * @param text Texto a ser convertido (ex: "1.234,56")
     * @return Valor em Double ou null se inválido
     */
    public static Double parseValue(String text) {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }

        try {
            text = text.trim()
                    .replace("R$", "")
                    .replace(" ", "")
                    .replace(".", "")
                    .replace(",", ".");
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Limpa o campo e define como vazio
     *
     * @param textField Campo de texto
     */
    public static void clear(TextField textField) {
        if (textField.getText().startsWith("R$ ")) {
            textField.setText("R$ ");
        } else {
            textField.setText("");
        }
    }

    /**
     * Valida se o valor está dentro de um intervalo
     *
     * @param textField Campo de texto
     * @param min       Valor mínimo (inclusive)
     * @param max       Valor máximo (inclusive)
     * @return true se está dentro do intervalo, false caso contrário
     */
    public static boolean isInRange(TextField textField, double min, double max) {
        Double value = getValue(textField);
        if (value == null) {
            return false;
        }
        return value >= min && value <= max;
    }
}
