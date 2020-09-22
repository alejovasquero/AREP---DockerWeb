package edu.escuelaing.arep.webserver;

public class RoundBalancer {
    private int initialPort;
    private int hosts;
    private int index = -1;

    public RoundBalancer(int port, int hosts){
        this.initialPort = port;
        this.hosts = hosts;
    }

    public synchronized int getNextPort(){
        index +=1;
        int ans = initialPort + (index)%hosts;
        index%=hosts;
        return ans;
    }
}
