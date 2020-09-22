package edu.escuelaing.arep;

import edu.escuelaing.arep.webserver.RoundBalancer;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RoundBalancerTest {

    @Test
    public void firstPortTest(){
        RoundBalancer balancer = new RoundBalancer(355, 100);
        assertEquals(balancer.getNextPort(), 355);

        balancer = new RoundBalancer(4543543, 10545430);
        assertEquals(balancer.getNextPort(), 4543543);

        balancer = new RoundBalancer(666, 105445430);
        assertEquals(balancer.getNextPort(), 666);
    }

    @Test
    public void threadsAccessTest() {
        int initial = 555;
        int hosts = 4;
        RoundBalancer balancer = new RoundBalancer(initial, hosts);
        ArrayList<Runnable> threads = new ArrayList<Runnable>();
        for (int i = 0; i < 15; i++) {
            threads.add(new Runnable() {
                @Override
                public void run() {
                    int k = 0;
                    for(int j=0; j < 100; j++){
                        k = balancer.getNextPort();
                        assertTrue(k < initial +hosts );
                    }
                }
            });
        }
        for(Runnable r: threads){
            Thread a = new Thread(r);
            a.start();
        }
    }

    @Test
    public void robinTest(){
        RoundBalancer balancer = new RoundBalancer(400, 600);
        for (int i=0; i<600; i++){
            balancer.getNextPort();
        }
        assertEquals(balancer.getNextPort(), 400);
    }
}
