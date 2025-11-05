package my.company.projetorotisseriejavafx.Objects;

import java.util.List;

public class MarmitaVendida {

    private int id;
    private int idMarmita;
    private String nome;
    private String detalhes;
    private List<String> principais;
    private List<String> misturas;
    private List<String> guarnicoes;
    private String salada;
    private String observacao;
    private double subtotal;

    public int getIdMarmita() {
        return idMarmita;
    }

    public void setIdMarmita(int idMarmita) {
        this.idMarmita = idMarmita;
    }


    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSalada() {
        return salada;
    }

    public void setSalada(String salada) {
        this.salada = salada;
    }

    public List<String> getPrincipais() {
        return principais;
    }

    public void setPrincipais(List<String> principais) {
        this.principais = principais;
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

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public String getFormattedSubtotal() {
        return "R$ " + String.valueOf(subtotal).replace(".", ",");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetalhes() {
        StringBuilder detalhes = new StringBuilder("Principais:\n");
        for (String p : principais) {
            detalhes.append(p).append("\n");
        }

        detalhes.append("Misturas:\n");

        for (String m : misturas) {
            detalhes.append(m).append("\n");
        }

        detalhes.append("Guarnições:\n");

        for (String g : guarnicoes) {
            detalhes.append(g).append("\n");
        }

        detalhes.append("Salada:\n");

        detalhes.append(salada);

        this.detalhes = detalhes.toString();

        return this.detalhes;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
