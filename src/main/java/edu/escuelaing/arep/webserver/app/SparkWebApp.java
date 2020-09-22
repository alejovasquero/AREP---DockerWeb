package edu.escuelaing.arep.webserver.app;
import edu.escuelaing.arep.webserver.RoundBalancer;
import spark.Request;
import spark.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static spark.Spark.*;

/**
 * @author Alejandro Vasquez
 */
public class SparkWebApp {

    private static int LOGGING_INDEX= 0;
    private static final String LOGGING_HOST = "http://172.18.0.1:";
    private static final String page = "/codes";
    private static final int STARTING_PORTT = 36000;

    private static RoundBalancer balancer = new RoundBalancer(STARTING_PORTT, 3);

    public static void main(String[] args) {
        port(getPort());
        staticFileLocation("/public");
        post("/codes", (req, res) -> makePost(req));
    }

    /**
     * Cambia el puerto de respuesta, dependiendo del entorno de despliegue
     * @return Puerto a trabajar
     */
    public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }

    /**
     * Realiza un post a uno de los servicios de log
     * @param res Respuesta con los datos de los códigos a guardar
     * @return Reespuesta por parte de los servicios de logs
     */
    private static String makePost(Request res){
        int targetPort = balancer.getNextPort();
        System.out.println(targetPort);
        String code = res.queryParams("code");
        String ans = "[]";
        try {
            ans = sendMessage(targetPort, code);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ans;
    }

    /**
     * Envía un mensaje sobre la red a uno de los servicios de log
     * @param port Puerto al que enviar el mensaje en la máquina
     * @param code Código a enviar
     * @return Respuesta con el historial filtrado de códigos
     * @throws IOException
     */
    private static String sendMessage(int port, String code) throws IOException {
        System.out.println("HERE  "+ port);
        URL url = new URL(LOGGING_HOST + port +page);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        String inputString = "code="+code;
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = inputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
            return response.toString();
        }
    }
}
