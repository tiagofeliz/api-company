package br.com.hotmart.company.config.exception.handler;

import br.com.hotmart.company.config.exception.ResourceNotFoundException;
import br.com.hotmart.company.config.exception.dto.ResourceNotFoundDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResourceNotFoundHandler {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResourceNotFoundDto handle(ResourceNotFoundException exception){
        return ResourceNotFoundDto.asDto(exception.getMessage());
    }

}
