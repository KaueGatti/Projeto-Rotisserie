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
    private List<String> saladas;
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

    public List<String> getSalada() {
        return saladas;
    }

    public void setSalada(List<String> saladas) {
        this.saladas = saladas;
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
        StringBuilder detalhes = new StringBuilder("Principais: ");

        for (int i = 0; i < principais.size(); i++) {
            detalhes.append(principais.get(i));

            if (i != principais.size() - 1) {
                detalhes.append(", ");
            }
        }



        detalhes.append("\nMisturas: ");

        for (int i = 0; i < misturas.size(); i++) {
            detalhes.append(misturas.get(i));

            if (i != misturas.size() - 1) {
                detalhes.append(", ");
            }
        }

        detalhes.append("\nGuarnicoes: ");

        for (int i = 0; i < guarnicoes.size(); i++) {
            detalhes.append(guarnicoes.get(i));

            if (i != guarnicoes.size() - 1) {
                detalhes.append(", ");
            }
        }

        detalhes.append("\nSaladas: ");

        for (int i = 0; i < saladas.size(); i++) {
            detalhes.append(saladas.get(i));

            if (i != saladas.size() - 1) {
                detalhes.append(", ");
            }
        }

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
