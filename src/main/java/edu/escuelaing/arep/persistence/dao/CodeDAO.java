package edu.escuelaing.arep.persistence.dao;

public interface CodeDAO {

    static String DBHost = "";

    public void registerCode(String code);

    public String[] getLastCodes();

}
