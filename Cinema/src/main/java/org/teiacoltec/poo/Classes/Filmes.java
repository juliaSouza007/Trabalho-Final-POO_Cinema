package org.teicoltec.poo.Classes;

import org.teicoltec.poo.conexao.Conexao;
import org.teicoltec.poo.ClassesDAO.FilmesDAO;
import java.util.ArrayList;

public class Filmes {
   
    private int id;
    private int duracao_s;
    private String nome;
   
    public Filmes(int id, int duracao_s ,String nome) {
        
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
