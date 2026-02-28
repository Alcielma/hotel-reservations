package com.bd.hotel.reservations.web.controller;

import com.bd.hotel.reservations.application.service.ReservaService;
import com.bd.hotel.reservations.persistence.entity.Reserva;
import com.bd.hotel.reservations.web.dto.request.ReservaRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService service;

    // LISTAR TODAS
    @GetMapping
    public ResponseEntity<List<Reserva>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    // CRIAR (POST)
    @PostMapping
    public ResponseEntity<Reserva> criar(@Valid @RequestBody ReservaRequest dto) {
        Reserva novaReserva = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaReserva);
    }

    // ATUALIZAR (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> atualizar(@PathVariable Long id, @Valid @RequestBody ReservaRequest dto) {
        Reserva reservaAtualizada = service.atualizar(id, dto);
        return ResponseEntity.ok(reservaAtualizada);
    }

    // DELETAR (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}