/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.company.projetorotisseriejavafx.Objects;

/**
 *
 * @author kaueg
 */
public class MarmitaVendida {

    private int id;
    private Marmita marmita;
    private Pedido pedido;
    private String detalhes;
    private double valorPeso;
    private String observacao;
    private double subtotal;

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public String getDescricao() {
        return marmita.getDescricao();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Marmita getMarmita() {
        return marmita;
    }

    public void setMarmita(Marmita marmita) {
        this.marmita = marmita;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public double getValorPeso() {
        return valorPeso;
    }

    public void setValorPeso(double valorPeso) {
        this.valorPeso = valorPeso;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
