package my.company.projetorotisserie.Objects;

public class Motoboy {

    private int id;
    private String nome;
    private double valorDiaria;
    private String status;

    public Motoboy() {
    }

    public Motoboy(int id, String nome, double valorDiaria, String status) {
        this.id = id;
        this.nome = nome;
        this.valorDiaria = valorDiaria;
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

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
