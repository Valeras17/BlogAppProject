package val.gord.blogproject.error;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class BlogExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException e){
        var problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,e.getMessage());

        problemDetails.setProperty("timestamp", LocalDate.now());
        problemDetails.setProperty("resourceName",e.getResourceName());
        problemDetails.setProperty("resourceId",e.getResourceId());

        return problemDetails;
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        //New ProblemDetail Object
        var problemDetail =
                ProblemDetail
                        .forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation Failed");
        //forEach fieldError in the exception
        e.getBindingResult().getFieldErrors()   .forEach(fieldError->{

            problemDetail.setProperty("Validation Failed for property",fieldError.getField());
            //problemDetail.setProperty("objectName",fieldError.getObjectName());
            problemDetail.setProperty("message",fieldError.getDefaultMessage());
            problemDetail.setProperty("rejectedValue",fieldError.getRejectedValue());
        });

        //add details about  the exception:
        problemDetail.setProperty("timestamp", LocalDate.now());
        return problemDetail;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException e){
        var problemDetail =
                ProblemDetail
                        .forStatusAndDetail(HttpStatus.BAD_REQUEST, "Database save Failed");
        if (e.getCause()instanceof ConstraintViolationException cause){
            //TODO:ConstraintViolationException
            problemDetail.setProperty("cause","Constraint Validation");
        }
        //add details about  the exception:
        problemDetail.setProperty("timestamp", LocalDate.now());
        return problemDetail;
    }



    //BadRequestException + ResourceNotFoundException extends BlogException
    @ExceptionHandler(BlogException.class)
    public ProblemDetail handleBlogException(BlogException e){
        var problemDetail =
                ProblemDetail
                        .forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setProperty("timestamp", LocalDate.now());
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception e){
        var problemDetail =
                ProblemDetail
                        .forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setProperty("timestamp", LocalDate.now());
        return problemDetail;
    }

}
