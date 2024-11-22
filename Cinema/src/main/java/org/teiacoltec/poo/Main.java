package org.teiacoltec.poo;

import org.teiacoltec.poo.Classes.*;
import org.teiacoltec.poo.ClassesDAO.*;
import org.teiacoltec.poo.conexao.FalhaConexaoException;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws FalhaConexaoException {
        Cinema cineart = Cineart.obtemInstancia();
        Cinema cinemark = Cinemark.obtemInstancia();
        Cinema cinepolis = Cinepolis.obtemInstancia();
        Cinema cinesercla = Cinesercla.obtemInstancia();

        List<Cinema> cinemasBD = CinemaDAO.carregar(Cinema.obtemListaCinemas());

        for (Cinema cinema : cinemasBD) {
            System.out.println("Nome: " + cinema.getNome());
        }
    }
}
