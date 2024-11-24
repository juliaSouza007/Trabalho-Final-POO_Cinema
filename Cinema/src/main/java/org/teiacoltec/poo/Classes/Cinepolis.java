package org.teiacoltec.poo.Classes;

public class Cinepolis extends Cinema{
    static private int proximoIDSala = 0;

    static private Cinepolis cinema = null;

    private Cinepolis() {
        super(obtemProximoID(), "Cinepólis Shopping Estação BH",
                                "Av. Cristiano Machado, 11833 - Vila Cloris, Belo Horizonte - MG, 31565-000");
        this.salas = new Sala[9];
        Cinema.addCinema(this);
    }

    public static Cinema obtemInstancia() {
        if (cinema == null) {
            cinema = new Cinepolis();
        }
        return cinema;
    }

    @Override
    public Cinema obtemCinema() {
        if (cinema == null) {
            cinema = new Cinepolis();
        }
        return cinema;
    }

    @Override
    public void criarSala(String nome, int capacidade) throws LimiteSalasException {
        if (Cinepolis.proximoIDSala >= 10) {
            throw new LimiteSalasException();
        } else {
            this.salas[Cinepolis.proximoIDSala] = (new Sala(Cinepolis.proximoIDSala, nome, capacidade));
            Cinepolis.proximoIDSala++;
        }
    }
}
