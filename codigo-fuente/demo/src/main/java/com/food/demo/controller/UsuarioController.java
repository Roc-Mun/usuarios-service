package com.food.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.food.demo.dto.UsuarioCreateDTO;
import com.food.demo.dto.UsuarioDTO;
import com.food.demo.dto.UsuarioUpdateDTO;
import com.food.demo.service.UsuarioService;

@RestController
@RequestMapping("/api/v3/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @Operation(summary = "Listar usuarios")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    @GetMapping
    public List<UsuarioDTO> listarUsuarios() {
        return service.listarUsuarios();
    }

    @Operation(summary = "Crear usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@Valid @RequestBody UsuarioCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearUsuario(dto));
    }

    @Operation(summary = "Autenticar usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@Valid @RequestBody LoginRequest body) {
        return ResponseEntity.ok(service.autenticarUsuario(body.correo(), body.contrasena()));
    }

    @Operation(summary = "Obtener usuario por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public UsuarioDTO obtenerPorId(@PathVariable Long id) {
        return service.obtenerUsuarioPorId(id);
    }

    @Operation(summary = "Actualizar usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public UsuarioDTO actualizar(@PathVariable Long id, @RequestBody UsuarioUpdateDTO dto) {
        return service.actualizarUsuario(id, dto);
    }

    @Operation(summary = "Asignar rol a usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rol asignado correctamente"),
            @ApiResponse(responseCode = "400", description = "Rol inválido"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}/rol")
    public UsuarioDTO asignarRol(@PathVariable Long id, @RequestParam String rol) {
        return service.asignarRolUsuario(id, rol);
    }

    @Operation(summary = "Desactivar usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario desactivado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<UsuarioDTO> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(service.desactivarUsuario(id));
    }

    public record LoginRequest(
            @NotBlank @Email String correo,
            @NotBlank String contrasena) {
    }
}