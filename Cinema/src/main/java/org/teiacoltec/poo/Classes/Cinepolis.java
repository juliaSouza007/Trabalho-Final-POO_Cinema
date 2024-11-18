package org.teiacoltec.poo.Classes;

public class Cinepolis extends Cinema{
    // Apenas 9 salas
    private Cinepolis() {
        super(obtemProximoID(), "Cinepólis Shopping Estação BH",
                                "Av. Cristiano Machado, 11833 - Vila Cloris, Belo Horizonte - MG, 31565-000");
        Cinema.addCinema(this);
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
