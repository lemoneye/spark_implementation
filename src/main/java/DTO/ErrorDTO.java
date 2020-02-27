package DTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class ErrorDTO {
    private String _id;
    private String responseCode;
    private String date = LocalDateTime.now().toString();
    private ArrayList<HashMap<String, String>> response = new ArrayList<>();

    public ErrorDTO() {
        this.responseCode = "ERROR";
        this.date = date;
        this.response = response;
    }

    public String get_id() {
        return _id;
    }
    public String getResponseCode() {
        return responseCode;
    }
    public String getDate() {
        return date;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
