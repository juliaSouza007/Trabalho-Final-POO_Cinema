package org.teiacoltec.poo.Classes;

public abstract class Cinema {
    final private int id;
    private String nome;
    private String local;

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
    public void criarSala() {

    }

    // Lista as salas do cinema e suas informações
    public void listarSalas() {

    }

    // Lista todos os cinemas criados
    public void listaCinemas() {

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