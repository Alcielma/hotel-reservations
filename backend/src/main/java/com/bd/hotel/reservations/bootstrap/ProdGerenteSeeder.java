package com.bd.hotel.reservations.bootstrap;

import com.bd.hotel.reservations.application.service.FuncionarioService;
import com.bd.hotel.reservations.application.service.UserService;
import com.bd.hotel.reservations.exception.business.EmailAlreadyRegisteredException;
import com.bd.hotel.reservations.exception.notfound.HotelNotFoundException;
import com.bd.hotel.reservations.persistence.entity.Hotel;
import com.bd.hotel.reservations.persistence.entity.User;
import com.bd.hotel.reservations.persistence.enums.CargoFuncionario;
import com.bd.hotel.reservations.persistence.enums.Role;
import com.bd.hotel.reservations.persistence.repository.FuncionarioRepository;
import com.bd.hotel.reservations.persistence.repository.HotelRepository;
import com.bd.hotel.reservations.persistence.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
@Profile("prod")
@RequiredArgsConstructor
public class ProdGerenteSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final HotelRepository hotelRepository;

    private final UserService userService;
    private final FuncionarioService funcionarioService;

    @Value("${app.bootstrap-gerente.enabled:false}")
    private boolean enabled;

    @Value("${app.bootstrap-gerente.email:gerente@prod.dev}")
    private String email;

    @Value("${app.bootstrap-gerente.password:password}")
    private String password;

    @Value("${app.bootstrap-gerente.nome:Gerente}")
    private String nome;

    @Value("${app.bootstrap-gerente.cpf:00000000}")
    private String cpf;

    // hotel obrigatório para Funcionario
    @Value("${app.bootstrap-gerente.hotel-id:0}")
    private Long hotelId;

    // opcional
    @Value("${app.bootstrap-gerente.salario:0}")
    private BigDecimal salario;

    @Override
    @Transactional
    public void run(@NonNull ApplicationArguments args) {
        if (!enabled) return;

        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new IllegalStateException("Bootstrap gerente enabled, but email/password not provided");
        }

        if (hotelId == null || hotelId <= 0) {
            throw new IllegalStateException("Bootstrap gerente enabled, but app.bootstrap-gerente.hotel-id not provided");
        }

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyRegisteredException(email);
        }

        if (funcionarioRepository.existsByHotelIdAndCargo(hotelId, CargoFuncionario.GERENTE)) {
            return;
        }

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException(hotelId));

        // Escolha recomendada: Role.FUNCIONARIO (e cargo define GERENTE)
        User user = userService.criarUsuario(email.trim().toLowerCase(), password, Role.FUNCIONARIO);

        // se por algum motivo já existe perfil (não deveria), aborta
        if (funcionarioRepository.existsByUserId(user.getId())) return;

        funcionarioService.criarPerfil(
                user,
                nome,
                hotel,
                CargoFuncionario.GERENTE,
                salario,
                cpf
        );
    }
}
