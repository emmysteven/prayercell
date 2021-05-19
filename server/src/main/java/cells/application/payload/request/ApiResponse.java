package cells.application.payload.request;

import lombok.*;

@Data
public class ApiResponse {

    private String data;
    private Boolean success;

    public ApiResponse(String data, Boolean success) {
        this.data = data;
        this.success = success;
    }
}
