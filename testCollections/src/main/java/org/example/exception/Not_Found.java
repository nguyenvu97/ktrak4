package org.example.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Not_Found extends RuntimeException{
    String message;
}
