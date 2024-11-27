package org.teiacoltec.poo.Classes;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class Vendas {
    final private int id;
    private Timestamp data;
    private Cinema cinema;
    private List<Filmes> filmes;
    private double valorTotal;

    public Vendas(int id, Timestamp data, List<Filmes> filmes, int quantidadeIngressos) {
        this.id = id;
        this.data = data;
        this.cinema = cinema;
        this.filmes = filmes;
        this.valorTotal = quantidadeIngressos * 20.0;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
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
