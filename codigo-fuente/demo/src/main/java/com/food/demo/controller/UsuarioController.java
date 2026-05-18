package com.food.demo.controller;

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
@RequestMapping("/api/v2/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<UsuarioDTO> listarUsuarios() {
        return service.listarUsuarios();
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@Valid @RequestBody UsuarioCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearUsuario(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@Valid @RequestBody LoginRequest body) {
        return ResponseEntity.ok(service.autenticarUsuario(body.correo(), body.contrasena()));
    }

    @GetMapping("/{id}")
    public UsuarioDTO obtenerPorId(@PathVariable Long id) {
        return service.obtenerUsuarioPorId(id);
    }

    @PutMapping("/{id}")
    public UsuarioDTO actualizar(@PathVariable Long id, @RequestBody UsuarioUpdateDTO dto) {
        return service.actualizarUsuario(id, dto);
    }

    @PutMapping("/{id}/rol")
    public UsuarioDTO asignarRol(@PathVariable Long id, @RequestParam String rol) {
        return service.asignarRolUsuario(id, rol);
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<UsuarioDTO> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(service.desactivarUsuario(id));
    }

    public record LoginRequest(
            @NotBlank @Email String correo,
            @NotBlank String contrasena) {
    }
}