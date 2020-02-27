package DAO;

import DTO.NoteDTO;
import com.google.gson.*;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import org.bson.types.ObjectId;
import java.time.LocalDateTime;
import java.util.*;
import static com.mongodb.client.model.Filters.eq;

public class NoteDAO {
    private NoteDTO returnNoteDto;
    Map<String, String> myMap;

    // open connection
    static MongoClient mongoClient = new MongoClient("localhost", 27017);
    // get ref to database
    static MongoDatabase db = mongoClient.getDatabase("MyDatabase");
    // get ref to collection
    static MongoCollection<Document> myColection = db.getCollection("MyCollection");
    // create a new document

    public NoteDTO deleteId(String id){
        myColection.deleteOne(eq("_id",new ObjectId(id)));
        return new NoteDTO(id);
    }

    public NoteDTO store(String bodyText) {
        Document doc = new Document()
                .append("data", bodyText)
                .append("date", LocalDateTime.now().toString());
        // insert document into collection
        myColection.insertOne(doc);     // Store into database
        MongoCursor<Document> cursor = myColection.find().iterator();

        String object = new String();
        try {
            while (cursor.hasNext()) {
                object = cursor.next().toJson();
            }
        } finally {
            cursor.close();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject jsonObject = new JsonParser().parse(object).getAsJsonObject();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        myMap = gson.fromJson(jsonObject.get("_id"), type);
        returnNoteDto = new NoteDTO(myMap.get("$oid"));
        return returnNoteDto;
    }
    public NoteDTO update(String id, String bodytext) {
        myColection.updateOne(eq("_id", new ObjectId(id)), new Document("$set", new Document("data", bodytext)));
        myColection.updateOne(eq("_id", new ObjectId(id)), new Document("$set", new Document("date", LocalDateTime.now().toString())));
        return new NoteDTO(id);
    }
}