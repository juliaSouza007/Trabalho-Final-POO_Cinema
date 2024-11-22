package org.teiacoltec.poo.Classes;

public class Cinesercla extends Cinema{
    // Apenas 5 salas

    static private Cinesercla cinema = null;

    private Cinesercla() {
        super(obtemProximoID(), "Cinesercla Norte",
                                "Av. Vilarinho, 1300 - Venda Nova, Belo Horizonte - MG, 31.615, Belo Horizonte - MG, 31615-250");
        Cinema.addCinema(this);
    }

    public static Cinema obtemInstancia() {
        if (cinema == null) {
            cinema = new Cinesercla();
        }
        return cinema;
    }

    @Override
    public Cinema obtemCinema() {
        if (cinema == null) {
            cinema = new Cinesercla();
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
