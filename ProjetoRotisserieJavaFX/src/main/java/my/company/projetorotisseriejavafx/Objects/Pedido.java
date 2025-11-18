package my.company.projetorotisseriejavafx.Objects;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pedido {

    private int id;
    private Mensalista mensalista;
    private Bairro bairro;
    private String nomeCliente;
    private String tipoPagamento;
    private String tipoPedido;
    private String observacoes;
    private String endereco;
    private double valorEntrega;
    private double valorItens;
    private double valorTotal;
    private double valorPago;
    private LocalDateTime dateTime;
    private LocalDate vencimento;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Mensalista getMensalista() {
        return mensalista;
    }

    public void setMensalista(Mensalista mensalista) {
        this.mensalista = mensalista;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public String getNomeCliente() {
        if (mensalista == null) {
            return nomeCliente;
        } else {
            return mensalista.getNome();
        }
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public double getValorEntrega() {
        return valorEntrega;
    }

    public void setValorEntrega(double valorEntrega) {
        this.valorEntrega = valorEntrega;
    }

    public double getValorItens() {
        return valorItens;
    }

    public void setValorItens(double valorItens) {
        this.valorItens = valorItens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFormattedValorEntrega() {
        return String.format("R$ %.2f", valorEntrega).replace(".", ",");
    }

    public String getFormattedValorItens() {
        return String.format("R$ %.2f", valorItens).replace(".", ",");
    }

    public String getFormattedValorTotal() {
        return String.format("R$ %.2f", getValorTotal()).replace(".", ",");
    }

    public String getFormattedValorPago() {
        return String.format("R$ %.2f", valorPago).replace(".", ",");
    }

    public String getFormattedValorAPagar() {
        Double valorAPagar = getValorTotal() - getValorPago();
        return String.format("R$ %.2f", valorAPagar).replace(".", ",");
    }

    public String getFormattedDateTime() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy | HH:mm:ss").format(dateTime);
    }

    public String getFormattedVencimento() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(vencimento);
    }
}
