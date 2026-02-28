package com.bd.hotel.reservations.application.service;

import com.bd.hotel.reservations.persistence.entity.Funcionario;
import com.bd.hotel.reservations.persistence.entity.Hotel;
import com.bd.hotel.reservations.persistence.entity.User;
import com.bd.hotel.reservations.persistence.enums.CargoFuncionario;
import com.bd.hotel.reservations.persistence.repository.FuncionarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;

    @Transactional
    public void criarPerfil(User user,
                                   String nome,
                                   Hotel hotel,
                                   CargoFuncionario cargo,
                                   BigDecimal salario, String cpf) {

        Funcionario funcionario = Funcionario.builder()
                .user(user)
                .nome(nome)
                .hotel(hotel)
                .cargo(cargo)
                .salario(salario)
                .cpf(cpf)
                .build();

        funcionarioRepository.save(funcionario);
    }
}