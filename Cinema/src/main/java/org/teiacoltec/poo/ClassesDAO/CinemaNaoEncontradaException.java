package org.teiacoltec.poo.ClassesDAO;

public class CinemaNaoEncontradaException extends Exception {

    public CinemaNaoEncontradaException(int id) {
        super("Cinema de id " + id + " nao encontrado.");
    }

}
