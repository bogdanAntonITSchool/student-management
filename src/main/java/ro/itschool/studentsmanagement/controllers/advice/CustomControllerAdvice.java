package ro.itschool.studentsmanagement.controllers.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ro.itschool.studentsmanagement.exceptions.StudentNotFoundException;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), org.springframework.http.HttpStatus.NOT_FOUND);
    }
}
