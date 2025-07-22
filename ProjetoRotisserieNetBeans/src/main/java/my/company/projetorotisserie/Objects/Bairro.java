package my.company.projetorotisserie.Objects;

public class Bairro {

    private int id;
    private String nome;
    private double valorEntrega;
    private String status;

    public Bairro() {
    }

    public Bairro(int id, String nome, double valorEntrega, String status) {
        this.id = id;
        this.nome = nome;
        this.valorEntrega = valorEntrega;
        this.status = status;
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
}
