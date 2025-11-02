/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.company.projetorotisseriejavafx.Objects;

/**
 *
 * @author kaueg
 */
public class Marmita {
    
    private int id;
    private String nome;
    private int maxMistura;
    private int maxGuarnicao;
    private double valor;
    private String status;

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

    public int getMaxMistura() {
        return maxMistura;
    }

    public void setMaxMistura(int maxMistura) {
        this.maxMistura = maxMistura;
    }

    public int getMaxGuarnicao() {
        return maxGuarnicao;
    }

    public void setMaxGuarnicao(int maxGuarnicao) {
        this.maxGuarnicao = maxGuarnicao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return nome;
    }
    
}
