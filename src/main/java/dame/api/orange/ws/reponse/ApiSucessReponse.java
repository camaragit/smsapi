package dame.api.orange.ws.reponse;
import java.io.Serializable;
import java.util.Map;

public class ApiSucessReponse implements Serializable{

    private Integer status =200;
    private Integer code =0;
    private String message;
    private Map<String,Object> values;

    public ApiSucessReponse( String message, Map<String,Object> values) {

        this.message = message;
        this.values = values;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }
}
