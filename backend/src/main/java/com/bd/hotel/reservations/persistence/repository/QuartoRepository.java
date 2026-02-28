package com.bd.hotel.reservations.persistence.repository;

import com.bd.hotel.reservations.persistence.entity.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {
}