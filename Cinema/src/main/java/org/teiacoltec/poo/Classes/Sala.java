package org.teiacoltec.poo.Classes;

import org.teiacoltec.poo.conexao.Conexao;
import org.teiacoltec.poo.ClassesDAO.SalaDAO;

import java.util.ArrayList;
import java.util.Date;

public class Sala {

    private int id;
    private String nome;
    private int capacidade;
    private ArrayList<Sessao> sessao;


    public Sala(int id, String nome, int capacidade) {
        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
        this.sessao = new ArrayList<>();
    }


    public void criarSessao() {
       
    }

    public void listarSessoes() {
        if (sessao.isEmpty()) {
            System.out.println("Não há sessões cadastradas.");
            return;
        }
        for (Sessao s : sessao) {
            System.out.println(s.toString());
        }
    }


    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {

        this.nome = nome;
    }

    public int getCapacidade() {

        return capacidade;
    }

    public void setCapacidade(int capacidade) {

        this.capacidade = capacidade;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

}
