package my.company.projetorotisserie.Objects;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Pedido {

    private int id;
    private Mensalista mensalista;
    private Motoboy motoboy;
    private Bairro bairro;
    private String nomeCliente;
    private String tipoPagamento;
    private String tipoPedido;
    private String observacoes;
    private double valorEntrega;
    private double valorTotal;
    private String endereco;
    private LocalDateTime horario;
    private LocalDate data;
    private String status;

    public Pedido() {
    }

    public Pedido(int id, Mensalista mensalista, Motoboy motoboy, Bairro bairro, String nomeCliente,
            String tipoPagamento, String tipoPedido, String observacoes, double valorEntrega,
            double valorTotal, String endereco, LocalDateTime horario, LocalDate data, String status) {
        this.id = id;
        this.mensalista = mensalista;
        this.motoboy = motoboy;
        this.bairro = bairro;
        this.nomeCliente = nomeCliente;
        this.tipoPagamento = tipoPagamento;
        this.tipoPedido = tipoPedido;
        this.observacoes = observacoes;
        this.valorEntrega = valorEntrega;
        this.valorTotal = valorTotal;
        this.endereco = endereco;
        this.horario = horario;
        this.data = data;
        this.status = status;
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

    public Motoboy getMotoboy() {
        return motoboy;
    }

    public void setMotoboy(Motoboy motoboy) {
        this.motoboy = motoboy;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public String getNomeCliente() {
        return nomeCliente;
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

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
