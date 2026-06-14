package com.food.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import com.food.demo.model.Usuario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    @Test
    @DisplayName("Debe guardar un usuario correctamente")
    void debeGuardarUsuarioCorrectamente() {

        Usuario usuario = Usuario.builder()
                .nombre("Juan Perez")
                .correo("juan@correo.com")
                .contrasena("123456")
                .rol("cliente")
                .estado("activo")
                .build();

        Usuario guardado = repository.save(usuario);

        assertNotNull(guardado.getIdUsuario());
        assertEquals("Juan Perez", guardado.getNombre());
    }

    @Test
    @DisplayName("Debe buscar usuario por id")
    void debeBuscarUsuarioPorId() {

        Usuario usuario = Usuario.builder()
                .nombre("Maria")
                .correo("maria@correo.com")
                .contrasena("123456")
                .rol("organizador")
                .estado("activo")
                .build();

        Usuario guardado = repository.save(usuario);

        Optional<Usuario> resultado =
                repository.findById(guardado.getIdUsuario());

        assertTrue(resultado.isPresent());
        assertEquals("Maria", resultado.get().getNombre());
    }

    @Test
    @DisplayName("Debe listar todos los usuarios")
    void debeListarTodosLosUsuarios() {

        Usuario usuario1 = Usuario.builder()
                .nombre("Usuario Uno")
                .correo("uno@correo.com")
                .contrasena("123")
                .rol("cliente")
                .estado("activo")
                .build();

        Usuario usuario2 = Usuario.builder()
                .nombre("Usuario Dos")
                .correo("dos@correo.com")
                .contrasena("123")
                .rol("organizador")
                .estado("activo")
                .build();

        repository.save(usuario1);
        repository.save(usuario2);

        List<Usuario> usuarios = repository.findAll();

        assertNotNull(usuarios);
        assertTrue(usuarios.size() >= 2);
    }

    @Test
    @DisplayName("Debe buscar usuario por correo")
    void debeBuscarUsuarioPorCorreo() {

        Usuario usuario = Usuario.builder()
                .nombre("Pedro")
                .correo("pedro@correo.com")
                .contrasena("123456")
                .rol("cliente")
                .estado("activo")
                .build();

        repository.save(usuario);

        Optional<Usuario> resultado =
                repository.findByCorreo("pedro@correo.com");

        assertTrue(resultado.isPresent());
        assertEquals("Pedro", resultado.get().getNombre());
    }
}