package org.teiacoltec.poo.Classes;

import org.teiacoltec.poo.ClassesDAO.SessaoDAO;
import org.teiacoltec.poo.Classes.Sala;
import org.teiacoltec.poo.Classes.Filmes;

import java.util.Date;

public class Sessao {
    private int id;
    private Sala salaAssociada;
    private Filmes filmeExibido;
    private Date dataSessao;
    private SessaoDAO sessaoDAO;

    public Sessao(int id, Sala salaAssociada, Filmes filmeExibido, Date dataSessao) {
        this.id = id;
        this.salaAssociada = salaAssociada;
        this.filmeExibido = filmeExibido;
        this.dataSessao = dataSessao;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + '\n' +
               "Sala: " + this.salaAssociada.getNome() + '\n' +
               "Filme: " + this.filmeExibido.getNome() + '\n' +
               "Data: " + this.dataSessao;
    }

    //getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sala getSalaAssociada() {
        return salaAssociada;
    }

    public Filmes getFilmeExibido() {
        return filmeExibido;
    }

    public Date getDataSessao() {
        return dataSessao;
    }
}
