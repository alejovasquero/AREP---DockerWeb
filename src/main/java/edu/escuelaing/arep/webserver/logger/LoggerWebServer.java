package edu.escuelaing.arep.webserver.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.escuelaing.arep.entities.Code;
import edu.escuelaing.arep.log.LogService;
import edu.escuelaing.arep.log.impl.LogServiceImpl;
import edu.escuelaing.arep.persistence.dao.CodeDAO;
import edu.escuelaing.arep.persistence.dao.mongo.CodeMongoDAO;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import static edu.escuelaing.arep.webserver.app.SparkWebApp.getPort;
import static spark.Spark.port;
import static spark.Spark.post;

public class LoggerWebServer {

    private static AtomicInteger balanceServer= new AtomicInteger(0);
    private static CodeDAO codeDAO = new CodeMongoDAO();
    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        port(getPort());
        post("/code", (res, resp) -> {
                    String code = res.queryParams("code");
                    System.out.println(code);
                    String date = new Date().toString();
                    Code nuevo  = new Code(code, date);
                    codeDAO.registerCode(nuevo);
                    String ans = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                            codeDAO.getLastCodes()
                    );
                    System.out.println(ans);
                    return ans;
                }
        );
    }
}
