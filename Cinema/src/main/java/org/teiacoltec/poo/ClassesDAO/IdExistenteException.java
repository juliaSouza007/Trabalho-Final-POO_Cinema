package org.teiacoltec.poo.ClassesDAO;

public class IdExistenteException extends  Exception {
    public IdExistenteException(int id) {
        super("Cinema existente.");
    }

}
