package org.teiacoltec.poo.Classes;

import org.teiacoltec.poo.conexao.Conexao;
import org.teiacoltec.poo.ClassesDAO.FilmesDAO;
import java.util.ArrayList;
import java.util.List;

public class Filmes {
   
    final private int id;
    private long duracao_s;
    private String nome;
   
    public Filmes(int id, long duracao_s ,String nome) {
        
        this.id = id;
        this.duracao_s = duracao_s;
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + '\n' + "Nome: " + this.nome + '\n' + "Duração: " + this.duracao_s + " segundos\n";
    }

    public int getId() {
        return id;
    }

     public long getDuracao_s() {
        return duracao_s;
    }

    public void setDuracao_s(long duracao_s) {
        this.duracao_s = duracao_s;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
