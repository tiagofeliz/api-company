package br.com.tiagofeliz.company.config.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResourceNotFoundDto {

    private String message;

    public static ResourceNotFoundDto asDto(String message){
        return new ResourceNotFoundDto(message);
    }

}
