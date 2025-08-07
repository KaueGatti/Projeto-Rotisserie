/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.company.projetorotisseriejavafx.Objects;

import java.util.Objects;

/**
 *
 * @author kaueg
 */
public class MarmitaVendida {

    private int id;
    private Marmita marmita;
    private int pedido;
    private String detalhes;
    private double valorPeso;
    private String deletar = "-";
    private String observacao;

    public String getDeletar() {
        return deletar;
    }

    public double getSubtotal() {
        return marmita.getValor();
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

    public int getPedido() {
        return pedido;
    }

    public void setPedido(int pedido) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MarmitaVendida that = (MarmitaVendida) o;
        return Double.compare(that.getSubtotal(), getSubtotal()) == 0
                && Objects.equals(getDescricao(), that.getDescricao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescricao(), getSubtotal());
    }
}
