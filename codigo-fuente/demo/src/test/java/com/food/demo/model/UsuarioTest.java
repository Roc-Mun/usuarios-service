package com.food.demo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    @DisplayName("Debe crear usuario con constructor vacío y setters")
    void debeCrearUsuarioConConstructorVacioYSetters() {

        // Arrange
        Usuario usuario = new Usuario();

        // Act
        usuario.setIdUsuario(1L);
        usuario.setNombre("Rocio");
        usuario.setCorreo("rocio@test.cl");
        usuario.setContrasena("1234");
        usuario.setRol("cliente");
        usuario.setEstado("activo");

        // Assert
        assertEquals(1L, usuario.getIdUsuario());
        assertEquals("Rocio", usuario.getNombre());
        assertEquals("rocio@test.cl", usuario.getCorreo());
        assertEquals("1234", usuario.getContrasena());
        assertEquals("cliente", usuario.getRol());
        assertEquals("activo", usuario.getEstado());
    }

    @Test
    @DisplayName("Debe crear usuario con constructor completo")
    void debeCrearUsuarioConConstructorCompleto() {

        // Arrange & Act
        Usuario usuario = new Usuario(
                1L,
                "Rocio",
                "rocio@test.cl",
                "1234",
                "cliente",
                "activo"
        );

        // Assert
        assertEquals(1L, usuario.getIdUsuario());
        assertEquals("Rocio", usuario.getNombre());
        assertEquals("rocio@test.cl", usuario.getCorreo());
        assertEquals("1234", usuario.getContrasena());
        assertEquals("cliente", usuario.getRol());
        assertEquals("activo", usuario.getEstado());
    }

    @Test
    @DisplayName("Dos usuarios iguales deben tener equals y hashCode iguales")
    void debeValidarEqualsYHashCode() {

        // Arrange
        Usuario usuario1 = new Usuario(
                1L,
                "Rocio",
                "rocio@test.cl",
                "1234",
                "cliente",
                "activo"
        );

        Usuario usuario2 = new Usuario(
                1L,
                "Rocio",
                "rocio@test.cl",
                "1234",
                "cliente",
                "activo"
        );

        // Act & Assert
        assertEquals(usuario1, usuario2);
        assertEquals(usuario1.hashCode(), usuario2.hashCode());
    }
}