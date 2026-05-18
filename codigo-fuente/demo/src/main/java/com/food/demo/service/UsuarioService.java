package com.food.demo.service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.food.demo.dto.UsuarioCreateDTO;
import com.food.demo.dto.UsuarioDTO;
import com.food.demo.dto.UsuarioUpdateDTO;
import com.food.demo.model.Usuario;
import com.food.demo.repository.UsuarioRepository;

import com.food.demo.exception.EstadoInvalidoException;
import com.food.demo.exception.RecursoNoEncontradoException;

@Service
public class UsuarioService {

    private static final Set<String> ROLES_VALIDOS = Set.of("cliente", "organizador", "stand");

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<UsuarioDTO> listarUsuarios() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    public UsuarioDTO crearUsuario(UsuarioCreateDTO dto) {
        String correo = dto.getCorreo().trim();

        if (repository.findByCorreo(correo).isPresent()) {
            throw new EstadoInvalidoException("Ya existe un usuario con ese correo");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(correo);
        usuario.setContrasena(dto.getContrasena());
        usuario.setEstado("activo");

        if (dto.getRol() == null || dto.getRol().trim().isEmpty()) {
            usuario.setRol("cliente");
        } else {
            validarRol(dto.getRol());
            usuario.setRol(dto.getRol().trim().toLowerCase());
        }

        return toDto(repository.save(usuario));
    }

    public UsuarioDTO autenticarUsuario(String correo, String contrasena) {
        return repository.findByCorreo(correo.trim())
                .filter(u -> Objects.equals(u.getContrasena(), contrasena))
                .map(this::toDto)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "Credenciales inválidas"
                        ));
    }

    public UsuarioDTO obtenerUsuarioPorId(Long id) {
        return toDto(obtenerEntidadPorId(id));
    }

    public UsuarioDTO actualizarUsuario(Long id, UsuarioUpdateDTO dto) {
        if (dto == null) {
            dto = new UsuarioUpdateDTO();
        }
        Usuario parcial = new Usuario();
        parcial.setNombre(dto.getNombre());
        parcial.setCorreo(dto.getCorreo());
        return toDto(actualizarEntidad(id, parcial));
    }

    public UsuarioDTO asignarRolUsuario(Long id, String rol) {
        validarRol(rol);
        Usuario existente = obtenerEntidadPorId(id);
        existente.setRol(rol.trim().toLowerCase());
        return toDto(repository.save(existente));
    }

    public UsuarioDTO desactivarUsuario(Long id) {
        Usuario existente = obtenerEntidadPorId(id);
        existente.setEstado("inactivo");
        return toDto(repository.save(existente));
    }

    private Usuario obtenerEntidadPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Usuario no encontrado"));
    }

    private Usuario actualizarEntidad(Long id, Usuario usuarioActualizado) {
        Usuario existente = obtenerEntidadPorId(id);

        if (usuarioActualizado.getNombre() != null &&
                !usuarioActualizado.getNombre().isBlank()) {
            existente.setNombre(usuarioActualizado.getNombre());
        }

        if (usuarioActualizado.getCorreo() != null &&
                !usuarioActualizado.getCorreo().isBlank()) {

            String nuevoCorreo = usuarioActualizado.getCorreo().trim();

            repository.findByCorreo(nuevoCorreo)
                    .filter(u -> !u.getIdUsuario().equals(id))
                    .ifPresent(u -> {
                        throw new EstadoInvalidoException(
                                "Ya existe un usuario con ese correo"
                        );
                    });

            existente.setCorreo(nuevoCorreo);
        }

        return repository.save(existente);
    }

    private UsuarioDTO toDto(Usuario u) {
        return new UsuarioDTO(
                u.getIdUsuario(),
                u.getNombre(),
                u.getCorreo(),
                u.getRol(),
                u.getEstado()
        );
    }

    private static void validarRol(String rol) {
        if (rol == null || rol.isBlank()) {
            throw new EstadoInvalidoException("El rol es obligatorio");
        }

        String normalizado = rol.trim().toLowerCase();

        if (!ROLES_VALIDOS.contains(normalizado)) {
            throw new EstadoInvalidoException(
                    "Rol inválido. Debe ser: cliente, organizador o stand"
            );
        }
    }
}