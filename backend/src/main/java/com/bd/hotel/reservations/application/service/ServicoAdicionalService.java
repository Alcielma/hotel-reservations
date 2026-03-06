package com.bd.hotel.reservations.application.service;

import com.bd.hotel.reservations.exception.notfound.ServicoAdicionalNotFoundException;
import com.bd.hotel.reservations.persistence.entity.ServicoAdicional;
import com.bd.hotel.reservations.persistence.repository.ServicoAdicionalRepository;
import com.bd.hotel.reservations.web.dto.request.ServicoAdicionalRequest;
import com.bd.hotel.reservations.web.dto.response.ServicoAdicionalResponse;
import com.bd.hotel.reservations.web.mapper.ServicoAdicionalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoAdicionalService {

    private final ServicoAdicionalRepository servicoAdicionalRepository;
    private final ServicoAdicionalMapper servicoAdicionalMapper;

    @Transactional
    public ServicoAdicionalResponse criar(ServicoAdicionalRequest request) {
        ServicoAdicional servico = servicoAdicionalMapper.toEntity(request);
        ServicoAdicional salvo = servicoAdicionalRepository.save(servico);
        return servicoAdicionalMapper.toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public List<ServicoAdicionalResponse> listarTodos() {
        List<ServicoAdicional> servicos = servicoAdicionalRepository.findAll();
        return servicoAdicionalMapper.toResponseList(servicos);
    }

    @Transactional(readOnly = true)
    public ServicoAdicionalResponse buscarPorId(Long id) {
        ServicoAdicional servico = buscarEntidadePorId(id);
        return servicoAdicionalMapper.toResponse(servico);
    }

    public ServicoAdicional buscarEntidadePorId(Long id) {
        return servicoAdicionalRepository.findById(id)
                .orElseThrow(() -> new ServicoAdicionalNotFoundException(id));
    }

    @Transactional
    public ServicoAdicionalResponse atualizar(Long id, ServicoAdicionalRequest request) {
        ServicoAdicional servico = buscarEntidadePorId(id);

        servico.atualizar(request.nomeServico(), request.descricao(), request.preco());

        ServicoAdicional salvo = servicoAdicionalRepository.save(servico);
        return servicoAdicionalMapper.toResponse(salvo);
    }

    @Transactional
    public void deletar(Long id) {
        ServicoAdicional servico = buscarEntidadePorId(id);
        servicoAdicionalRepository.delete(servico);
    }
}