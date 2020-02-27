package Spark;

import java.util.Set;

import DAO.NoteDAO;
import DAO.ResponseDAO;
import DTO.ErrorDTO;
import DTO.NoteDTO;
import DTO.ResponseDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;
import spark.Spark;

public class SparkDemo {

    public static String matchUp(Request req, Response res) {
        String id = req.queryMap().get("_id").value().toString();// Get the id parameter
        ResponseDAO responseDAO = new ResponseDAO();    // Create DAO instance
        ResponseDTO responseDTO = responseDAO.get(id);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(responseDTO));
        return gson.toJson(responseDTO);
    }
    public static String getList(Request req, Response res){
        ResponseDAO responseDAO = new ResponseDAO();
        ResponseDTO responseDTO = responseDAO.getAllList();
        Gson listGSON = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("" + listGSON.toJson(responseDTO));
        return listGSON.toJson(responseDTO);
    }
    public static void main(String[] args) {
        // open the port
        Spark.port(1234);

        // store function
        Spark.post("/store", (req, res) -> {
            System.out.println("Store called");
            NoteDAO noteDAO = new NoteDAO();
            NoteDTO noteDTO = noteDAO.store(req.body());
            System.out.println("The id is: " + noteDTO.get_id());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println("RESULT:");
            System.out.println(gson.toJson(noteDTO));
            return gson.toJson(noteDTO);
        });
        // function call for get?_id=
        Spark.get("/get", SparkDemo::matchUp);
        // delete function
        Spark.post("/delete", (req, res) -> {
            System.out.println("Deleting");
            String id = req.queryMap().get("_id").value().toString();  // Get the id parameter
            NoteDAO noteDAO = new NoteDAO();
            NoteDTO noteDTO = noteDAO.deleteId(id);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println(gson.toJson(noteDTO));
            return gson.toJson(noteDTO);
        });
        // Print out all Lilst fucntion call
        Spark.get("/list", SparkDemo::getList );

        // update function
        Spark.post("/update", (req, res) -> {
            System.out.println("REQUEST BODY: " + req.body());
            System.out.println("START UPDATE");
            String id = req.queryMap().get("_id").value().toString();  // Get the id parameter

            System.out.println("REQUEST: " + req.toString());
            NoteDAO noteDAO = new NoteDAO();
            NoteDTO noteDTO = noteDAO.update(id, req.body().toString());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println(gson.toJson(noteDTO));
            return gson.toJson(noteDTO);
        });
      // for error
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      ErrorDTO errordto = new ErrorDTO();
      Spark.notFound(gson.toJson(errordto));

    }
}