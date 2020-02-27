package DAO;

import DTO.ResponseDTO;
import com.google.gson.*;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.*;

public class ResponseDAO {
    public static ResponseDAO DaoInstance;
    ResponseDTO returnResponseDto;

    // open connection
    MongoClient mongoClient = new MongoClient("localhost", 27017);
    // get ref to database
    MongoDatabase db = mongoClient.getDatabase("MyDatabase");
    // get ref to collection
    MongoCollection<Document> myColection = db.getCollection("MyCollection");
    // create a new document

    public ResponseDTO getAllList(){
        MongoCursor<Document> cursor = myColection.find().iterator();
        ArrayList<HashMap<String, String>> responses = new ArrayList<>();
        HashMap<String, String> tempMap;
        try{
            while(cursor.hasNext()){
                String object = cursor.next().toJson();
                JsonObject jsonObject = new JsonParser().parse(object).getAsJsonObject();
                JsonElement id_element = jsonObject.get("_id");
                String id_str = id_element.toString().trim();
                String id_subStr = id_str.substring(id_str.indexOf(':')+1, id_str.indexOf('}'));
                id_subStr = id_subStr.replace("\"", "");
                JsonElement data_element = jsonObject.get("data");
                JsonElement date_element = jsonObject.get("date");
                String date_element_str =date_element.toString().trim();
                tempMap = new HashMap<>();
                tempMap.put("_id", id_subStr);
                tempMap.put("data", data_element.toString().substring(1, data_element.toString().length() - 1));
                tempMap.put("date", date_element_str.substring(1, date_element_str.length() - 1));
                responses.add(tempMap);
            }
        }finally {
            cursor.close();
        }
        returnResponseDto = new ResponseDTO(responses);
        return returnResponseDto;
    }

    public ResponseDTO get(String id) {
        MongoCursor<Document> cursor = myColection.find().iterator();
        ArrayList<HashMap<String, String>> responses = new ArrayList<>();
        String object;
        boolean inLoop = true;
        try {
            while (cursor.hasNext() && inLoop) {
                object = cursor.next().toJson();
                JsonObject jsonObject = new JsonParser().parse(object).getAsJsonObject();
                JsonElement id_element = jsonObject.get("_id");
                String id_str = id_element.toString().trim();
                String id_subStr = id_str.substring(id_str.indexOf(':')+1, id_str.indexOf('}'));
                id_subStr = id_subStr.replace("\"", "");
                JsonElement date_element = jsonObject.get("date");
                String date_element_str =date_element.toString().trim();
                System.out.println("DATE: " + date_element_str);

                if (id.equals(id_subStr)) {
                    JsonElement data_element = jsonObject.get("data");
                    HashMap<String, String> tempMap = new HashMap<>();
                    tempMap.put("_id", id);
                    tempMap.put("data", data_element.toString().substring(1, data_element.toString().length() - 1));
                    tempMap.put("date", date_element_str.substring(1, date_element_str.length() - 1));
                    responses.add(tempMap);
                    inLoop = false;
                }
            }
        } finally {
            cursor.close();
        }
        returnResponseDto = new ResponseDTO(responses);
        return returnResponseDto;
    }
}



