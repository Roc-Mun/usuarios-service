package com.food.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.demo.dto.UsuarioCreateDTO;
import com.food.demo.dto.UsuarioDTO;
import com.food.demo.exception.GlobalExceptionHandler;
import com.food.demo.exception.RecursoNoEncontradoException;
import com.food.demo.service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UsuarioControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(usuarioController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Debe listar usuarios correctamente")
    void debeListarUsuariosCorrectamente() throws Exception {

        UsuarioDTO usuario = new UsuarioDTO(
                1L,
                "Juan",
                "juan@correo.com",
                "cliente",
                "activo"
        );

        when(usuarioService.listarUsuarios())
                .thenReturn(List.of(usuario));

        mockMvc.perform(get("/api/v3/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[0].correo").value("juan@correo.com"));
    }

    @Test
    @DisplayName("Debe crear usuario correctamente")
    void debeCrearUsuarioCorrectamente() throws Exception {

        UsuarioCreateDTO request = new UsuarioCreateDTO(
                "Juan",
                "juan@correo.com",
                "1234",
                "cliente"
        );

        UsuarioDTO response = new UsuarioDTO(
                1L,
                "Juan",
                "juan@correo.com",
                "cliente",
                "activo"
        );

        when(usuarioService.crearUsuario(request))
                .thenReturn(response);

        mockMvc.perform(post("/api/v3/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idUsuario").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    @DisplayName("Debe retornar 404 cuando usuario no existe")
    void debeRetornar404CuandoUsuarioNoExiste() throws Exception {

        when(usuarioService.obtenerUsuarioPorId(99L))
                .thenThrow(new RecursoNoEncontradoException("Usuario no encontrado"));

        mockMvc.perform(get("/api/v3/usuarios/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"));
    }
}