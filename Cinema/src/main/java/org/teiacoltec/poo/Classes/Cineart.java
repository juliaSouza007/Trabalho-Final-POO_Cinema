package org.teiacoltec.poo.Classes;

public class Cineart extends Cinema{
    // Apenas 6 salas
    private Cineart() {
        super(obtemProximoID(), "Cineart Minas Shopping",
                                "Av. Cristiano Machado, 4.000 - União, Belo Horizonte - MG, 31160-900");
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
