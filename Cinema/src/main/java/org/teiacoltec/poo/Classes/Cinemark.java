package org.teiacoltec.poo.Classes;

public class Cinemark extends Cinema{
    // 10 salas

    static private Cinemark cinema = null;

    private Cinemark() {
        super(obtemProximoID(), "Cinemark BH Shopping",
                                "Rodovia BR-356, 3049 - Loja 047 - Belvedere, Belo Horizonte - MG, 30320-900");
        Cinema.addCinema(this);
    }

    public static Cinemark obtemCinema() {
        if (cinema == null) {
            cinema = new Cinemark();
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
