package org.teiacoltec.poo.Classes;

public class Cinesercla extends Cinema{
    // Apenas 5 salas
    private Cinesercla() {
        super(obtemProximoID(), "Cinesercla Norte",
                                "Av. Vilarinho, 1300 - Venda Nova, Belo Horizonte - MG, 31.615, Belo Horizonte - MG, 31615-250");
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
