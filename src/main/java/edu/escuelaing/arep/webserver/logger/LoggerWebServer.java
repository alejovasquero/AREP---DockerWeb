package edu.escuelaing.arep.webserver.logger;

import edu.escuelaing.arep.log.LogService;
import edu.escuelaing.arep.log.impl.LogServiceImpl;

import java.util.concurrent.atomic.AtomicInteger;

import static edu.escuelaing.arep.webserver.app.SparkWebApp.getPort;
import static spark.Spark.port;
import static spark.Spark.post;

public class LoggerWebServer {

    private static AtomicInteger balanceServer= new AtomicInteger(0);
    private static LogService[] services = new LogService[3];


    public static void main(String[] args) {
        port(getPort());
        initServices();
        post("/code", (res, resp) -> {
                    return null;
                }
        );
    }

    private static void initServices() {
        services[0] = new LogServiceImpl();
        services[1] = new LogServiceImpl();
        services[2] = new LogServiceImpl();
    }

    public static String registerCodeAndReturn(String code){
        String ans = null;

        return ans;
    }
}
