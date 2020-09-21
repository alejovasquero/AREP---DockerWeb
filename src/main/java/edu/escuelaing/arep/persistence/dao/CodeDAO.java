package edu.escuelaing.arep.persistence.dao;

import edu.escuelaing.arep.entities.Code;

import java.util.List;

public interface CodeDAO {

    static String DBHost = "";

    public void registerCode(Code code);

    public List<Code> getLastCodes();

}
