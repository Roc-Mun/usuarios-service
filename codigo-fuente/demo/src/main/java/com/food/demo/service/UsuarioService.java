package com.food.demo.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.food.demo.model.Usuario;
import com.food.demo.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario crearUsuario(Usuario usuario) {

        Optional<Usuario> existente = repository.findByCorreo(usuario.getCorreo());

        if (existente.isPresent()) {
            throw new RuntimeException("Ya existe un usuario con ese correo");
        }

        return repository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return repository.findAll();
    }

    public Usuario obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario obtenerPorCorreo(String correo) {
        return repository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void eliminarUsuario(Long id) {
        repository.deleteById(id);
    }
}
