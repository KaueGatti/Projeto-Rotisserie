package my.company.projetorotisseriejavafx.Objects;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pedido {

    private int id;
    private Mensalista mensalista;
    private Bairro bairro;
    private Motoboy motoboy;
    private String nomeCliente;
    private String tipoPagamento;
    private String tipoPedido;
    private String observacoes;
    private double valorEntrega;
    private double valorTotal;
    private double valorPago;
    private String endereco;
    private LocalDateTime dateTime;
    private LocalDate vencimento;
    private String status;

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public Double getValorAPagar() {
        return valorTotal - valorPago;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    
    public String getDateTimeFormat() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy | HH:mm:ss").format(dateTime);
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

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

    public Motoboy getMotoboy() {
        return motoboy;
    }

    public void setMotoboy(Motoboy motoboy) {
        this.motoboy = motoboy;
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

    public double getValorEntrega() {
        return valorEntrega;
    }

    public void setValorEntrega(double valorEntrega) {
        this.valorEntrega = valorEntrega;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFormattedValorTotal() {
        return "R$ " + String.valueOf(valorTotal).replace(".", ",");
    }

    public String getFormattedValorEntrega() {
        return "R$ " + String.valueOf(valorEntrega).replace(".", ",");
    }

    public String getFormattedValorPago() {
        return "R$ " + String.valueOf(valorPago).replace(".", ",");
    }

    public String getFormattedValorAPagar() {
        return "R$ " + String.valueOf(getValorAPagar()).replace(".", ",");
    }

    public String getFormattedValorPedido() {
        return "R$ " + String.valueOf(getValorTotal() + getValorEntrega()).replace(".", ",");
    }

}
