package br.com.tiagofeliz.company.config.exception.handler;

import br.com.tiagofeliz.company.config.exception.ResourceNotFoundException;
import br.com.tiagofeliz.company.config.exception.UnprocessableEntityException;
import br.com.tiagofeliz.company.config.exception.dto.ResourceNotFoundDto;
import br.com.tiagofeliz.company.config.exception.dto.UnprocessableEntityDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UnprocessableEntityHandler {

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UnprocessableEntityException.class)
    public UnprocessableEntityDto handle(UnprocessableEntityException exception){
        return UnprocessableEntityDto.asDto(exception.getMessage());
    }

}
