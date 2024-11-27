package org.teiacoltec.poo.Classes;

import org.teiacoltec.poo.ClassesDAO.SalaDAO;
import org.teiacoltec.poo.conexao.FalhaConexaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class Cineart extends Cinema{
    static private int proximoIDSala = 0;
    private HashMap<String, Double> lanchesMenu = new HashMap<>();

    static private Cineart cinema = null;

    private Cineart() {
        super(obtemProximoID(), "Cineart Minas Shopping",
                                "Av. Cristiano Machado, 4.000 - União, Belo Horizonte - MG, 31160-900");
        this.salas = new Sala[6];
        Cinema.addCinema(this);

        lanchesMenu.put("Coca-Cola", 8.00);
        lanchesMenu.put("Pipoca Média", 12.00);
        lanchesMenu.put("M&M's", 5.00);
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
    public void criarSala(String nome, int capacidade) throws LimiteSalasException {
        if (Cineart.proximoIDSala >= 10) {
            throw new LimiteSalasException();
        } else {
            Sala sala = new Sala(Cineart.proximoIDSala, nome, capacidade, this.getId());
            SalaDAO.insere(sala);
            this.salas[Cineart.proximoIDSala] = sala;
            Cineart.proximoIDSala++;
        }
    }

    public void listaLanches() {
        for (String lanche : lanchesMenu.keySet()) {
            System.out.println("Lanche: " + lanche + "\nValor: " + lanchesMenu.get(lanche));
        }
    }

    public void comprarLanches(String item) {
        if (lanchesMenu.containsKey(item)) {
            System.out.println("Comprado: " + item + " por R$" + lanchesMenu.get(item));
        } else {
            System.out.println("Item não encontrado no menu.");
        }
    }
}
