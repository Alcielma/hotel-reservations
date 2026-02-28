package com.bd.hotel.reservations.application.service;

import com.bd.hotel.reservations.persistence.entity.Reserva;
import com.bd.hotel.reservations.persistence.entity.Cliente;
import com.bd.hotel.reservations.persistence.entity.Quarto;
import com.bd.hotel.reservations.persistence.repository.ReservaRepository;
import com.bd.hotel.reservations.persistence.repository.ClienteRepository;
import com.bd.hotel.reservations.persistence.repository.QuartoRepository;  
import com.bd.hotel.reservations.web.dto.request.ReservaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private QuartoRepository quartoRepository;

    // --- MÉTODO PARA SALVAR (CREATE) ---
    @Transactional
    public Reserva salvar(ReservaRequest dto) {
        // 1. Converter DTO para Entidade
        Reserva reserva = new Reserva();
        
        // Busca o cliente pelo ID enviado no JSON
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + dto.getClienteId()));
        
        // Busca a lista de quartos pelos IDs enviados no JSON
        List<Quarto> quartosEncontrados = quartoRepository.findAllById(dto.getQuartoIds());
        if (quartosEncontrados.isEmpty()) {
            throw new RuntimeException("Nenhum quarto válido foi selecionado.");
        }

        reserva.setCliente(cliente);
        reserva.setDataCheckinPrevisto(dto.getDataCheckinPrevisto());
        reserva.setDataCheckoutPrevisto(dto.getDataCheckoutPrevisto());
        reserva.setQuartos(new HashSet<>(quartosEncontrados));

        // 2. Validar Regras de Negócio
        validarDatas(reserva.getDataCheckinPrevisto(), reserva.getDataCheckoutPrevisto());

        // 3. Persistir no Banco
        return repository.save(reserva);
    }

    // --- MÉTODO PARA ATUALIZAR (UPDATE) ---
    @Transactional
    public Reserva atualizar(Long id, ReservaRequest dto) {
        // Busca a reserva existente
        Reserva reservaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada para atualização."));

        // Valida as novas datas recebidas no DTO
        validarDatas(dto.getDataCheckinPrevisto(), dto.getDataCheckoutPrevisto());

        // Atualiza apenas os campos permitidos
        reservaExistente.setDataCheckinPrevisto(dto.getDataCheckinPrevisto());
        reservaExistente.setDataCheckoutPrevisto(dto.getDataCheckoutPrevisto());
        
        // Atualiza quartos se necessário
        List<Quarto> novosQuartos = quartoRepository.findAllById(dto.getQuartoIds());
        reservaExistente.setQuartos(new HashSet<>(novosQuartos));

        return repository.save(reservaExistente);
    }

    // --- MÉTODO PARA DELETAR (DELETE) ---
    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Reserva não encontrada para exclusão.");
        }
        repository.deleteById(id);
    }

    // --- VALIDAÇÕES ---
    private void validarDatas(LocalDate checkin, LocalDate checkout) {
        LocalDate hoje = LocalDate.now();

        if (checkin.isBefore(hoje)) {
            throw new RuntimeException("A data de check-in não pode ser uma data passada.");
        }

        if (checkout.isBefore(checkin)) {
            throw new RuntimeException("A data de check-out deve ser posterior à data de check-in.");
        }

        if (checkout.isEqual(checkin)) {
            throw new RuntimeException("A reserva deve ter pelo menos uma diária.");
        }
    }
    
    // Listagem simples para o Controller
    public List<Reserva> listarTodas() {
        return repository.findAll();
    }
}