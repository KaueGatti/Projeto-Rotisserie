package my.company.projetorotisseriejavafx.Objects;

import javafx.beans.property.*;

public class Mensalista {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty nome = new SimpleStringProperty();
    private DoubleProperty conta = new SimpleDoubleProperty();
    private StringProperty status =  new SimpleStringProperty();

    public Mensalista() {
    }

    public Mensalista(String nome) {
        this();
        this.nome.set(nome);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public double getConta() {
        return conta.get();
    }

    public void setConta(double conta) {
        this.conta.set(conta);
    }

    public DoubleProperty contaProperty() {
        return conta;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty statusProperty() {
        return status;
    }

    @Override
    public String toString() {
        return nome.getValue();
    }
}

