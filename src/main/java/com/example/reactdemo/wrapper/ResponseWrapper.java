package com.example.reactdemo.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class ResponseWrapper<T> {

    @JsonProperty("path")
    private String path;

    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;

    @JsonProperty("result")
    private T result;

    @JsonProperty("errors")
    Object errors;

    public ResponseWrapper(String message) {
        this.message = message;
    }

    public ResponseWrapper(String status, String message, List<String> errors) {
        this.status=status;
        this.message=message;
        this.errors=errors;
    }

    public ResponseWrapper(String path, String status, String message, T result) {
        this.path=path;
        this.status=status;
        this.message=message;
        this.result=result;

    }


    public static ResponseWrapper successResponse(String message, Object result, String path) {
        return new ResponseWrapper(path, "success", message, result, null);
    }

    public static ResponseWrapper errorResponse(String message, Object errors, String path) {
        return new ResponseWrapper(path, "error", message, null, errors);
    }

}
