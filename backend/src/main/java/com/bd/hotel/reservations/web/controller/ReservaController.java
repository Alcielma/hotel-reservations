package com.bd.hotel.reservations.web.controller;

import com.bd.hotel.reservations.application.service.ReservaService;
import com.bd.hotel.reservations.persistence.entity.Reserva;
import com.bd.hotel.reservations.web.dto.request.ReservaRequest;
import com.bd.hotel.reservations.web.dto.response.ReservasDetalhadasResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    // Endpoint original do seu colega
    @GetMapping("/detalhadas")
    public List<ReservasDetalhadasResponse> listar() {
        return reservaService.listarReservasDetalhadas();
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Reserva> criar(@Valid @RequestBody ReservaRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.salvar(dto));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> atualizar(@PathVariable Long id, @Valid @RequestBody ReservaRequest dto) {
        return ResponseEntity.ok(reservaService.atualizar(id, dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        reservaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}