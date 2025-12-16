package my.company.projetorotisseriejavafx.Objects;

public class Bairro {
    
    private int id;
    private String nome;
    private double valorEntrega;
    private String status;

    public Bairro(String nome) {
        this.nome = nome;
    }

    public Bairro() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorEntrega() {
        return valorEntrega;
    }

    public void setValorEntrega(double valorEntrega) {
        this.valorEntrega = valorEntrega;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFormattedValorEntrega() {
        return "R$ " + String.valueOf(valorEntrega).replace(".", ",");
    }

    @Override
    public String toString() {
        return nome;
    }
}
