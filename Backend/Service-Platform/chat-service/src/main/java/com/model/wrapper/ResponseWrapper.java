package com.model.wrapper;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//Questa classe è utilizzata quando prepariamo una response per il frontend e può contenere:
//in caso positivo: una entity
//in caso negativo: un messaggio di errore
public class ResponseWrapper<T> {
	
    private T object;
    private String exceptionError;
    
}