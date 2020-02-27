package DTO;
import com.google.gson.JsonArray;
import java.time.LocalDateTime;

public class NoteDTO {
    private String _id;
    private String responseCode;
    private String date = LocalDateTime.now().toString();

    public NoteDTO(String _id) {
        this._id = _id;
        this.responseCode = "OK";
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
