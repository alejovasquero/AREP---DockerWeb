package edu.escuelaing.arep.persistence.dao.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import edu.escuelaing.arep.persistence.dao.CodeDAO;
import org.bson.Document;

public class CodeMongoDAO implements CodeDAO {

    private MongoClientURI uri;
    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection<Document> coll;


    {
        uri = new MongoClientURI(
                "mongodb+srv://arep:AAIKUvaCY0KYPCKe@arep-cluster-server.re6r2.mongodb.net/AREPWEB?retryWrites=true&w=majority");
        mongoClient = new MongoClient(uri);
        db = mongoClient.getDatabase("AREPWEB");
    }

    @Override
    public void registerCode(String code) {

    }

    @Override
    public String[] getLastCodes() {
        return new String[0];
    }
}
