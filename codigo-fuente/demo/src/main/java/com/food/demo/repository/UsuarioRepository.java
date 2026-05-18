package com.food.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.food.demo.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
}