package edu.escuelaing.arep.webserver;

/**
 * @author Alejandro Vasquez
 */
public class RoundBalancer {
    private int initialPort;
    private int hosts;
    private int index = -1;

    public RoundBalancer(int port, int hosts){
        this.initialPort = port;
        this.hosts = hosts;
    }

    /**
     * Retorna el siguiente puerto a ser utilizado en el ciclo
     * @return El siguiente puerto a ser utilizado en el ciclo
     */
    public synchronized int getNextPort(){
        index +=1;
        int ans = initialPort + (index)%hosts;
        index%=hosts;
        return ans;
    }
}
