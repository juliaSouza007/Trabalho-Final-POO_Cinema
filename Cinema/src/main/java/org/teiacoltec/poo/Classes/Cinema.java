package org.teiacoltec.poo.Classes;

import java.util.ArrayList;
import java.util.List;

public abstract class Cinema {
    final private int id;
    private String nome;
    private String local;

    // Lista de cinemas existentes
    static private List<Cinema> cinemas = new ArrayList<>();
    static protected void addCinema(Cinema cinema) { cinemas.add(cinema); }

    static private int proximoID = 0;
    static protected int obtemProximoID() {
        return proximoID++;
    }

    public Cinema(int id, String nome, String local) {
        this.id = id;
        this.nome = nome;
        this.local = local;
    }

    // Cria sala no cinema
    public void criarSala() {}

    // Lista as salas do cinema e suas informações
    public void listarSalas() {}

    // Lista todos os cinemas criados
    public void listaCinemas() {
        for (Cinema cinema : cinemas) {
            System.out.println("Nome: " + cinema.getNome() + "\nLocal: " + getLocal() + "\n");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Cinema) {
            Cinema oCinema = (Cinema) o;

            if(this.getId() == oCinema.getId() &&
               this.getNome().equals(oCinema.getNome()) &&
               this.getLocal().equals(oCinema.getLocal())) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }
    public void setLocal(String local) {
        this.local = local;
    }
}