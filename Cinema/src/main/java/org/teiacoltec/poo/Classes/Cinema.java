package org.teiacoltec.poo.Classes;

import java.util.ArrayList;
import java.util.List;

public abstract class Cinema {
    final private int id;
    private String nome;
    private String local;
    static private List<Cinema> cinemas = new ArrayList<Cinema>();
    static private int proximoID = 0;
    protected Sala[] salas;

    public Cinema(int id, String nome, String local) {
        this.id = id;
        this.nome = nome;
        this.local = local;
    }

    // Cria sala no cinema
    public void criarSala(String nome, int capacidade) throws LimiteSalasException {}

    // Lista as salas do cinema e suas informações
    public void listarSalas() {
        boolean vazio = true;

        for (int i = 0; i < salas.length; i++) {
            if (salas[i] != null) {
                System.out.println(salas[i].toString());
                vazio = false;
            }
        }
        if (vazio) {
            System.out.println("Não há salas.");
        }
    }

    // Lista todos os cinemas criados
    static public void listaCinemas() {
        for (Cinema cinema : cinemas) {
            System.out.println("NOME: " + cinema.getNome() + "\nLOCAL: " + getLocal() + "\n");
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

    public Cinema obtemCinema() { return null; }

    static protected void addCinema(Cinema cinema) { cinemas.add(cinema); }

    static public List<Cinema> obtemListaCinemas() { return Cinema.cinemas; }

     static public void setListaCinemas(List<Cinema> cinemas) { Cinema.cinemas = cinemas; }

    static protected int obtemProximoID() {
        return proximoID++;
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
