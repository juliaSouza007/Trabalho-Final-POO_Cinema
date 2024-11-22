package org.teiacoltec.poo.Classes;

import org.teiacoltec.poo.conexao.FalhaConexaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Cineart extends Cinema{
    // Apenas 6 salas

    static private Cineart cinema = null;

    private Cineart() {
        super(obtemProximoID(), "Cineart Minas Shopping",
                                "Av. Cristiano Machado, 4.000 - União, Belo Horizonte - MG, 31160-900");
        Cinema.addCinema(this);
    }

    public static Cinema obtemInstancia() {
        if (cinema == null) {
            cinema = new Cineart();
        }
        return cinema;
    }

    @Override
    public Cinema obtemCinema() {
        if (cinema == null) {
            cinema = new Cineart();
        }
        return cinema;
    }

    @Override
    public void criarSala() {
        System.out.println(" ");
    }

    @Override
    public void listarSalas() {
        System.out.println(" ");
    }
}
