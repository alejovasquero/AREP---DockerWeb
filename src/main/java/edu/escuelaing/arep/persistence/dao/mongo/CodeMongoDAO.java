package edu.escuelaing.arep.persistence.dao.mongo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import edu.escuelaing.arep.entities.Code;
import edu.escuelaing.arep.persistence.dao.CodeDAO;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;

public class CodeMongoDAO implements CodeDAO {

    private MongoClientURI uri;
    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection<Document> coll;

    private static ObjectMapper JSON_MAPPER = new ObjectMapper();

    {
        uri = new MongoClientURI("mongodb://172.18.0.1:27017");
        mongoClient = new MongoClient(uri);
        db = mongoClient.getDatabase("CODES");
    }

    @Override
    public void registerCode(Code code) {
        coll = db.getCollection("CodesCollection");
        Document insert = new Document("date", code.getDate()).append("content", code.getContent());
        coll.insertOne(insert);
    }

    @Override
    public List<Code> getLastCodes() {
        MongoCollection<Document> coll = db.getCollection("CodesCollection");
        MongoCursor<Document> cursor = coll.find().projection(excludeId()).sort(
                orderBy(descending("date"))
        ).limit(10).iterator();
        ArrayList<Code> ans= new ArrayList<Code>();
        try {
            while (cursor.hasNext()) {
                Code m = null;
                m = JSON_MAPPER.readValue(cursor.next().toJson(), Code.class);
                ans.add(m);
            }
        } catch (JsonProcessingException e)  {
            e.printStackTrace();
        }
        cursor.close();
        return ans;
    }
}
