package my.company.projetorotisseriejavafx.Objects;

import java.time.LocalDate;

public class Diaria {

    private String nomeMotoboy;
    private LocalDate data;
    private int entregas;
    private double valorEntregas;
    private double valorDiaria;
    private double valorTotalDiaria;

    public double getValorTotalDiaria() {
        return valorTotalDiaria;
    }

    public void setValorTotalDiaria(double valorTotalDiaria) {
        this.valorTotalDiaria = valorTotalDiaria;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public double getValorEntregas() {
        return valorEntregas;
    }

    public void setValorEntregas(double valorEntregas) {
        this.valorEntregas = valorEntregas;
    }

    public int getEntregas() {
        return entregas;
    }

    public void setEntregas(int entregas) {
        this.entregas = entregas;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getNomeMotoboy() {
        return nomeMotoboy;
    }

    public void setNomeMotoboy(String nomeMotoboy) {
        this.nomeMotoboy = nomeMotoboy;
    }
}