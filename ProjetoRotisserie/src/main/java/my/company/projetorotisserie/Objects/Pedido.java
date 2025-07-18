package my.company.projetorotisserie.Objects;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private String cliente;
    private List<Marmita> marmitas = new ArrayList<>();
    private Endereco endereco = new Endereco();
    private String tipo;
    private double valorTotal;
    private double valorEntrega;
    private LocalDate data;
    private LocalTime horário;

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public List<Marmita> getMarmitas() {
        return marmitas;
    }

    public void setMarmitas(List<Marmita> marmitas) {
        this.marmitas = marmitas;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getValorEntrega() {
        return valorEntrega;
    }

    public void setValorEntrega(double valorEntrega) {
        this.valorEntrega = valorEntrega;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHorário() {
        return horário;
    }

    public void setHorário(LocalTime horário) {
        this.horário = horário;
    }
}
