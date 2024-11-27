package org.teiacoltec.poo;

import org.teiacoltec.poo.Classes.*;
import org.teiacoltec.poo.ClassesDAO.*;
import org.teiacoltec.poo.conexao.FalhaConexaoException;

import java.util.Date;

public class IniciaTabelas {
    static private IniciaTabelas inicia = null;

    private IniciaTabelas () {
        try {
            // Cria as tabelas
            CinemaDAO.criaTabela();
            SalaDAO.criaTabela();
            SessaoDAO.criaTabela();
            FilmesDAO.criaTabela();
            VendasDAO.criaTabela();

            // Adiciona dados nas tabelas

            // add todos os cinemas
            CinemaDAO.salvar(Cinema.obtemListaCinemas());

            SalaDAO.insere(new Sala(1, "Sala A", 120, Cineart.obtemInstancia().getId()));
            SalaDAO.insere(new Sala(2, "Sala B", 80, Cinemark.obtemInstancia().getId()));
            SalaDAO.insere(new Sala(3, "Sala C", 100, Cinepolis.obtemInstancia().getId()));
            SalaDAO.insere(new Sala(4, "Sala D", 150, Cinesercla.obtemInstancia().getId()));
            SalaDAO.insere(new Sala(5, "Sala E", 110, Cinemark.obtemInstancia().getId()));
            SalaDAO.insere(new Sala(6, "Sala F", 90, Cineart.obtemInstancia().getId()));

            FilmesDAO.insere(new Filmes(1, 8880, "Inception"));
            FilmesDAO.insere(new Filmes(2, 8160, "The Matrix"));
            FilmesDAO.insere(new Filmes(3, 10140, "Interstellar"));
            FilmesDAO.insere(new Filmes(4, 12345, "Avatar"));
            FilmesDAO.insere(new Filmes(5, 54321, "The Dark Knight"));
            FilmesDAO.insere(new Filmes(6, 67890, "The Revenant"));

            SessaoDAO.salvarSessao(new Sessao(1, new Sala(1, "Sala A", 120, Cineart.obtemInstancia().getId()), new Filmes(1, 8880, "Inception"), new Date(2024, 11, 25, 18, 0, 0)));
            SessaoDAO.salvarSessao(new Sessao(2, new Sala(2, "Sala B", 80, Cinemark.obtemInstancia().getId()), new Filmes(2, 8160, "The Matrix"), new Date(2024, 11, 25, 20, 30, 0)));
            SessaoDAO.salvarSessao(new Sessao(3, new Sala(3, "Sala C", 100, Cinepolis.obtemInstancia().getId()), new Filmes(3, 10140, "Interstellar"), new Date(2024, 11, 26, 16, 0, 0)));
            SessaoDAO.salvarSessao(new Sessao(4, new Sala(4,"Sala D", 150, Cinesercla.obtemInstancia().getId()), new Filmes(4, 12345, "Avatar"), new Date(2024, 11, 27, 14, 0, 0)));
            SessaoDAO.salvarSessao(new Sessao(5, new Sala(5, "Sala E", 110, Cinemark.obtemInstancia().getId()), new Filmes(5, 54321, "The Dark Knight"), new Date(2024, 11, 27, 18, 30, 0)));
            SessaoDAO.salvarSessao(new Sessao(6, new Sala(6, "Sala F", 90, Cineart.obtemInstancia().getId()), new Filmes(6, 67890, "The Revenant"), new Date(2024, 11, 28, 20, 0, 0)));

        } catch (FalhaConexaoException e) {
            throw new RuntimeException(e);
        }
    }

    // Roda ap
    public static IniciaTabelas obtemInstancia() {
        if (inicia == null) {
            inicia = new IniciaTabelas();
        }
        return null;
    }
}
