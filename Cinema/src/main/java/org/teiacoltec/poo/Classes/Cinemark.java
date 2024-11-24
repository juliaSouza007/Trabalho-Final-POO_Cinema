package org.teiacoltec.poo.Classes;

import java.util.ArrayList;
import java.util.Date;

public class Cinemark extends Cinema{
    static private int proximoIDSala = 0;
    private ArrayList<String> reservas = new ArrayList<>(); // Para eventos, aniversários, etc

    static private Cinemark cinema = null;

    private Cinemark() {
        super(obtemProximoID(), "Cinemark BH Shopping",
                                "Rodovia BR-356, 3049 - Loja 047 - Belvedere, Belo Horizonte - MG, 30320-900");
        this.salas = new Sala[10];
        Cinema.addCinema(this);
    }

    public static Cinema obtemInstancia() {
        if (cinema == null) {
            cinema = new Cinemark();
        }
        return cinema;
    }

    @Override
    public Cinema obtemCinema() {
        if (cinema == null) {
            cinema = new Cinemark();
        }
        return cinema;
    }

    @Override
    public void criarSala(String nome, int capacidade) throws LimiteSalasException {
        if (Cinemark.proximoIDSala >= 10) {
            throw new LimiteSalasException();
        } else {
            this.salas[Cinemark.proximoIDSala] = (new Sala(Cinemark.proximoIDSala, nome, capacidade));
            Cinemark.proximoIDSala++;
        }
    }

    public void reservarSala(String nomeSala, Date data) {
        reservas.add("Sala: " + nomeSala + " | Data: " + data);
        System.out.println("Sala reservada: " + nomeSala + " dia " + data);
    }
}
