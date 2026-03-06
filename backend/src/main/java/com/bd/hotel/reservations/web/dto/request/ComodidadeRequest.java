package com.bd.hotel.reservations.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComodidadeRequest {
    
    @NotBlank(message = "O nome da comodidade é obrigatório")
    private String nome;
    
}