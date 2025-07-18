package my.company.projetorotisserie.Objects;

import java.util.ArrayList;
import java.util.List;

public class Marmita {
    private int idPedido;
    private int cod;
    private String descricao;
    private double valor; 
    private String tamanho;
    private List<String> principal = new ArrayList<>();
    private List<String> misturas = new ArrayList<>();
    private List<String> guarnicoes = new ArrayList<>();
    private String salada;

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public List<String> getPrincipal() {
        return principal;
    }

    public void setPrincipal(List<String> principal) {
        this.principal = principal;
    }

    public String getSalada() {
        return salada;
    }

    public void setSalada(String salada) {
        this.salada = salada;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public List<String> getMisturas() {
        return misturas;
    }

    public void setMisturas(List<String> misturas) {
        this.misturas = misturas;
    }

    public List<String> getGuarnicoes() {
        return guarnicoes;
    }

    public void setGuarnicoes(List<String> guarnicoes) {
        this.guarnicoes = guarnicoes;
    }
}
