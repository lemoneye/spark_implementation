package DTO;
import java.time.LocalDateTime;
import java.util.*;

public class ResponseDTO {
    private String date = LocalDateTime.now().toString();
    private String responseCode = "OK";
    private String _id;
    private ArrayList<HashMap<String, String>> response;

    public ResponseDTO(ArrayList<HashMap<String, String>> documents) {
        this.response = documents;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public void setDocuments(ArrayList<HashMap<String, String>> documents) { this.response = documents; }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }
    public ArrayList<HashMap<String, String>> getDocuments() {
        return response;
    }
    public String getResponseCode() {
        return responseCode;
    }
}