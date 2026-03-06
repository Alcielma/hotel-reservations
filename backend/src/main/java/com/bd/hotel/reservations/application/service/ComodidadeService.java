package com.bd.hotel.reservations.application.service;

import com.bd.hotel.reservations.exception.notfound.ComodidadeNotFoundException;
import com.bd.hotel.reservations.persistence.entity.Comodidade;
import com.bd.hotel.reservations.persistence.repository.ComodidadeRepository;
import com.bd.hotel.reservations.web.dto.request.ComodidadeRequest;
import com.bd.hotel.reservations.web.dto.response.ComodidadeResponse;
import com.bd.hotel.reservations.web.mapper.ComodidadeMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComodidadeService {

    private final ComodidadeRepository repository;
    private final ComodidadeMapper mapper;

    public ComodidadeResponse criar(ComodidadeRequest request) {
        Comodidade comodidade = mapper.toEntity(request);
        Comodidade salva = repository.save(comodidade);
        return mapper.toResponse(salva);
    }

    public List<ComodidadeResponse> listarTodas() {
        List<Comodidade> comodidades = repository.findAll();
        return mapper.toResponseList(comodidades);
    }

    public ComodidadeResponse buscarPorId(Long id) {
        Comodidade comodidade = repository.findById(id)
                .orElseThrow(() -> new ComodidadeNotFoundException(id));
        return mapper.toResponse(comodidade);
    }

    public ComodidadeResponse atualizar(Long id, ComodidadeRequest request) {
        Comodidade comodidade = repository.findById(id)
                .orElseThrow(() -> new ComodidadeNotFoundException(id));

        comodidade.atualizarNome(request.getNome());

        Comodidade salva = repository.save(comodidade);
        return mapper.toResponse(salva);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ComodidadeNotFoundException(id);
        }
        repository.deleteById(id);
    }


}