package edu.escuelaing.arep.persistence.dao;

import edu.escuelaing.arep.entities.Code;

import java.util.List;

public interface CodeDAO {

    static String DBHost = "";

    /**
     * Registra un código
     * @param code Código a registrar
     */
    public void registerCode(Code code);

    /**
     * Returna los últimos 10 códigos ordenados por fecha de manera descendiente
     * @return Códigos ordenados y filtrados
     */
    public List<Code> getLastCodes();

}
