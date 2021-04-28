package cells.payload.response;

public class ApiResponse {
    private Boolean success;
    private String message;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

//public class ApiResponse<T> {
//
//    private HttpStatus status;
//    private String message;
//    private LocalDateTime time = LocalDateTime.now();
//    private T result;
//}