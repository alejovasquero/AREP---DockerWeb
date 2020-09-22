package edu.escuelaing.arep.webserver.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.escuelaing.arep.entities.Code;
import edu.escuelaing.arep.persistence.dao.CodeDAO;
import edu.escuelaing.arep.persistence.dao.mongo.CodeMongoDAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import static edu.escuelaing.arep.webserver.app.SparkWebApp.getPort;
import static spark.Spark.port;
import static spark.Spark.post;

/**
 * @author Alejandro Vasquez
 */
public class LoggerWebServer {


    private static CodeDAO codeDAO = new CodeMongoDAO();
    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        port(getPort());
        post("/codes", (res, resp) -> {
                    System.out.println("TODO OK");
                    String code = res.queryParams("code");
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();
                    Code nuevo  = new Code(code, formatter.format(date));
                    codeDAO.registerCode(nuevo);
                    String ans = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                            codeDAO.getLastCodes()
                    );
                    System.out.println(codeDAO.getLastCodes());
                    System.out.println("NOOO" + ans);

                    return ans;
                }
        );
    }
}
