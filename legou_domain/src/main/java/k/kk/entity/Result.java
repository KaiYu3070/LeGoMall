package k.kk.entity;

import java.io.Serializable;

/*
 * This class is used as the return type for all methods in the controller.
 * Since all methods in our controller need to return a value, we define a standard.
 * This class is used for data transmission and is the common return type for all controller methods.
 * */
public class Result implements Serializable {

    // Indicates whether the backend operation was successful or failed
    private boolean success;

    // Message to be displayed
    private String message;

    // Total number of records
    private Long total;

    // The object containing the data
    private Object data;

    public Result() {

    }

    // For create, update, delete operations
    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // For non-paginated queries, such as retrieving all records or a single object
    public Result(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // For paginated queries
    public Result(boolean success, String message, Long total, Object data) {
        this.success = success;
        this.message = message;
        this.total = total;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
