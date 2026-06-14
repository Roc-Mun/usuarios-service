package com.food.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.food.demo.dto.UsuarioCreateDTO;
import com.food.demo.dto.UsuarioDTO;
import com.food.demo.exception.EstadoInvalidoException;
import com.food.demo.model.Usuario;
import com.food.demo.repository.UsuarioRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    @Test
    @DisplayName("Debe crear usuario correctamente")
    void debeCrearUsuarioCorrectamente() {

        UsuarioCreateDTO dto = new UsuarioCreateDTO(
                "Juan Perez",
                "juan@correo.com",
                "123456",
                "organizador"
        );

        Usuario usuarioGuardado = Usuario.builder()
                .idUsuario(1L)
                .nombre("Juan Perez")
                .correo("juan@correo.com")
                .contrasena("123456")
                .rol("organizador")
                .estado("activo")
                .build();

        when(repository.findByCorreo("juan@correo.com"))
                .thenReturn(Optional.empty());

        when(repository.save(any(Usuario.class)))
                .thenReturn(usuarioGuardado);

        UsuarioDTO resultado = service.crearUsuario(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdUsuario());
        assertEquals("Juan Perez", resultado.getNombre());
        assertEquals("organizador", resultado.getRol());

        verify(repository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debe asignar rol cliente por defecto")
    void debeAsignarRolClientePorDefecto() {

        UsuarioCreateDTO dto = new UsuarioCreateDTO(
                "Maria",
                "maria@correo.com",
                "123456",
                null
        );

        Usuario usuarioGuardado = Usuario.builder()
                .idUsuario(2L)
                .nombre("Maria")
                .correo("maria@correo.com")
                .contrasena("123456")
                .rol("cliente")
                .estado("activo")
                .build();

        when(repository.findByCorreo("maria@correo.com"))
                .thenReturn(Optional.empty());

        when(repository.save(any(Usuario.class)))
                .thenReturn(usuarioGuardado);

        UsuarioDTO resultado = service.crearUsuario(dto);

        assertEquals("cliente", resultado.getRol());

        verify(repository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el correo ya existe")
    void debeLanzarExcepcionCuandoCorreoExiste() {

        Usuario existente = Usuario.builder()
                .idUsuario(1L)
                .correo("juan@correo.com")
                .build();

        UsuarioCreateDTO dto = new UsuarioCreateDTO(
                "Juan",
                "juan@correo.com",
                "123456",
                "cliente"
        );

        when(repository.findByCorreo("juan@correo.com"))
                .thenReturn(Optional.of(existente));

        assertThrows(
                EstadoInvalidoException.class,
                () -> service.crearUsuario(dto)
        );

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el rol es inválido")
    void debeLanzarExcepcionCuandoRolEsInvalido() {

        UsuarioCreateDTO dto = new UsuarioCreateDTO(
                "Juan",
                "juan@correo.com",
                "123456",
                "administrador"
        );

        when(repository.findByCorreo("juan@correo.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                EstadoInvalidoException.class,
                () -> service.crearUsuario(dto)
        );

        verify(repository, never()).save(any());
    }
}