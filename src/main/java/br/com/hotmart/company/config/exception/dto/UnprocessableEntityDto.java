package br.com.hotmart.company.config.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnprocessableEntityDto {

    private String message;

    public static UnprocessableEntityDto asDto(String message){
        return new UnprocessableEntityDto(message);
    }

}
