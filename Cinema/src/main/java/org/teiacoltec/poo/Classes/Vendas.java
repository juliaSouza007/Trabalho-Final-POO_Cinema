package org.teiacoltec.poo.Classes;

import java.time.LocalDateTime;
import java.util.List;

public class Vendas {
    private int id;
    private LocalDateTime data;
    private Cinema cinema;
    private List<Filmes> filmes;
    private double valorTotal;

    public Vendas(int id, LocalDateTime data, Cinema cinema, List<Filmes> filmes, double valorTotal) {
        this.id = id;
        this.data = data;
        this.cinema = cinema;
        this.filmes = filmes;
        this.valorTotal = valorTotal;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public List<Filmes> getFilmes() {
        return filmes;
    }

    public void setFilmes(List<Filmes> filmes) {
        this.filmes = filmes;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "<< Venda {" +
                "id =" + id +
                ", data =" + data +
                ", cinema =" + cinema.getNome() +
                ", valorTotal =" + valorTotal +
                "} >>";
    }
}
