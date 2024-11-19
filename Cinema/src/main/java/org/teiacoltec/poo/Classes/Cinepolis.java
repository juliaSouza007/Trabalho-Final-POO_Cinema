package org.teiacoltec.poo.Classes;

public class Cinepolis extends Cinema{
    // Apenas 9 salas

    static private Cinepolis cinema = null;

    private Cinepolis() {
        super(obtemProximoID(), "Cinepólis Shopping Estação BH",
                                "Av. Cristiano Machado, 11833 - Vila Cloris, Belo Horizonte - MG, 31565-000");
        Cinema.addCinema(this);
    }

    public static Cinepolis obtemCinema() {
        if (cinema == null) {
            cinema = new Cinepolis();
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
