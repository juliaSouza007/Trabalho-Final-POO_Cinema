package org.teiacoltec.poo.Classes;

import org.teiacoltec.poo.conexao.Conexao;
import org.teiacoltec.poo.ClassesDAO.FilmesDAO;
import java.util.ArrayList;

public class Filmes {
   
    private int id;
    private long duracao_s;
    private String nome;
   
    public Filmes(int id, long duracao_s ,String nome) {
        
        this.id = id;
        this.duracao_s = duracao_s;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
