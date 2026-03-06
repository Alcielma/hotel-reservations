package com.bd.hotel.reservations.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComodidadeResponse {
    
    private Long id;
    private String nome;
    
}