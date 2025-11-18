package my.company.projetorotisseriejavafx.Objects;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pedido {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final ObjectProperty<Mensalista> mensalista = new SimpleObjectProperty<>();
    private ObjectProperty<Bairro> bairro = new SimpleObjectProperty<>();
    private final StringProperty nomeCliente = new SimpleStringProperty();
    private final StringProperty tipoPagamento = new SimpleStringProperty();
    private final StringProperty tipoPedido = new SimpleStringProperty();
    private final StringProperty observacoes = new SimpleStringProperty();
    private final StringProperty endereco = new SimpleStringProperty();
    private final DoubleProperty valorEntrega = new SimpleDoubleProperty();
    private final DoubleProperty valorItens = new SimpleDoubleProperty();
    private final DoubleProperty valorTotal = new SimpleDoubleProperty();
    private final DoubleProperty valorPago = new SimpleDoubleProperty();
    private final ObjectProperty<LocalDateTime> dateTime = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> vencimento = new SimpleObjectProperty<>();
    private final StringProperty status = new SimpleStringProperty();

    public int getId() { return id.get(); }
    public void setId(int value) { id.set(value); }

    public Mensalista getMensalista() { return mensalista.get(); }
    public void setMensalista(Mensalista value) { mensalista.set(value); }

    public Bairro getBairro() { return bairro.get(); }
    public void setBairro(Bairro value) { bairro.set(value); }

    public String getNomeCliente() { return nomeCliente.get(); }
    public void setNomeCliente(String value) { nomeCliente.set(value); }

    public String getTipoPagamento() { return tipoPagamento.get(); }
    public void setTipoPagamento(String value) { tipoPagamento.set(value); }

    public String getTipoPedido() { return tipoPedido.get(); }
    public void setTipoPedido(String value) { tipoPedido.set(value); }

    public String getObservacoes() { return observacoes.get(); }
    public void setObservacoes(String value) { observacoes.set(value); }

    public String getEndereco() { return endereco.get(); }
    public void setEndereco(String value) { endereco.set(value); }

    public double getValorEntrega() { return valorEntrega.get(); }
    public void setValorEntrega(double value) { valorEntrega.set(value); }

    public double getValorItens() { return valorItens.get(); }
    public void setValorItens(double value) { valorItens.set(value); }

    public double getValorTotal() { return valorTotal.get(); }
    public void setValorTotal(double value) { valorTotal.set(value); }

    public double getValorPago() { return valorPago.get(); }
    public void setValorPago(double value) { valorPago.set(value); }

    public LocalDateTime getDateTime() { return dateTime.get(); }
    public void setDateTime(LocalDateTime value) { dateTime.set(value); }

    public LocalDate getVencimento() { return vencimento.get(); }
    public void setVencimento(LocalDate value) { vencimento.set(value); }

    public String getStatus() { return status.get(); }
    public void setStatus(String value) { status.set(value); }

    public String getFormattedValorEntrega() {
        return String.format("R$ %.2f", valorEntrega.get()).replace(".", ",");
    }

    public String getFormattedValorItens() {
        return String.format("R$ %.2f", valorItens.get()).replace(".", ",");
    }

    public String getFormattedValorTotal() {
        return String.format("R$ %.2f", valorTotal.get()).replace(".", ",");
    }

    public String getFormattedValorPago() {
        return String.format("R$ %.2f", valorPago.get()).replace(".", ",");
    }

    public String getFormattedValorAPagar() {
        Double valorAPagar = getValorTotal() - getValorPago();
        return String.format("R$ %.2f", valorAPagar).replace(".", ",");
    }

    public String getFormattedDateTime() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy | HH:mm:ss").format(dateTime.get());
    }

    public String getFormattedVencimento() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(vencimento.get());
    }

    public IntegerProperty idProperty() { return id; }
    public ObjectProperty<Mensalista> mensalistaProperty() { return mensalista; }
    public ObjectProperty<Bairro> bairroProperty() { return bairro; }
    public StringProperty nomeClienteProperty() { return nomeCliente; }
    public StringProperty tipoPagamentoProperty() { return tipoPagamento; }
    public StringProperty tipoPedidoProperty() { return tipoPedido; }
    public StringProperty observacoesProperty() { return observacoes; }
    public StringProperty enderecoProperty() { return endereco; }
    public DoubleProperty valorEntregaProperty() { return valorEntrega; }
    public DoubleProperty valorItensProperty() { return valorItens; }
    public DoubleProperty valorTotalProperty() { return valorTotal; }
    public DoubleProperty valorPagoProperty() { return valorPago; }
    public ObjectProperty<LocalDateTime> dateTimeProperty() { return dateTime; }
    public ObjectProperty<LocalDate> vencimentoProperty() { return vencimento; }
    public StringProperty statusProperty() { return status; }
}
