package com.bd.hotel.reservations.web.controller;

import com.bd.hotel.reservations.web.dto.request.ComodidadeRequest;
import com.bd.hotel.reservations.web.dto.response.ComodidadeResponse;
import com.bd.hotel.reservations.application.service.ComodidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comodidades")
@RequiredArgsConstructor
public class ComodidadeController {

    private final ComodidadeService service;

    // CREATE
    @PostMapping
    public ResponseEntity<ComodidadeResponse> criar(@RequestBody @Valid ComodidadeRequest request) {
        ComodidadeResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // READ
    @GetMapping
    public ResponseEntity<List<ComodidadeResponse>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    // READ by ID
    @GetMapping("/{id}")
    public ResponseEntity<ComodidadeResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ComodidadeResponse> atualizar(@PathVariable Long id, @RequestBody @Valid ComodidadeRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}