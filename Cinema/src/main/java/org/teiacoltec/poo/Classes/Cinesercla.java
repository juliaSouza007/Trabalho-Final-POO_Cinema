package org.teiacoltec.poo.Classes;

public class Cinesercla extends Cinema{
    static private int proximoIDSala = 0;
    static private int proximoIDSala3D = 0;
    private Sala[] salas3D;

    static private Cinesercla cinema = null;

    private Cinesercla() {
        super(obtemProximoID(), "Cinesercla Norte",
                                "Av. Vilarinho, 1300 - Venda Nova, Belo Horizonte - MG, 31.615, Belo Horizonte - MG, 31615-250");
        this.salas = new Sala[5];
        this.salas3D = new Sala[5];
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
    public void criarSala(String nome, int capacidade) throws LimiteSalasException {
        if (Cinesercla.proximoIDSala >= 10) {
            throw new LimiteSalasException();
        } else {
            this.salas[Cinesercla.proximoIDSala] = (new Sala(Cinesercla.proximoIDSala, nome, capacidade));
            Cinesercla.proximoIDSala++;
        }
    }

    public void criarSala3D(String nome, int capacidade) throws LimiteSalasException {
        if (Cinesercla.proximoIDSala3D >= 10) {
            throw new LimiteSalasException();
        } else {
            this.salas[Cinesercla.proximoIDSala3D] = (new Sala(Cinesercla.proximoIDSala3D, nome, capacidade));
            Cinesercla.proximoIDSala3D++;
        }
    }

    public void listarSalas3D() {
        boolean vazio = true;

        for (int i = 0; i < salas3D.length; i++) {
            if (salas3D[i] != null) {
                System.out.println(salas3D[i].toString());
                vazio = false;
            }
        }
        if (vazio) {
            System.out.println("Não há salas.");
        }
    }
}
