package org.teiacoltec.poo;

import org.teiacoltec.poo.Classes.*;
import org.teiacoltec.poo.ClassesDAO.*;
import org.teiacoltec.poo.conexao.FalhaConexaoException;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws FalhaConexaoException {
        List<Cinema> cinemas = new ArrayList<>();

        cinemas.add(org.teiacoltec.poo.Classes.Cineart.obtemCinema());
        cinemas.add(org.teiacoltec.poo.Classes.Cinemark.obtemCinema());

        CinemaDAO.criaTabela();
        CinemaDAO.salvar(cinemas);

    }
}
