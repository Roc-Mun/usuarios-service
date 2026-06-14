package com.food.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos necesarios para crear un usuario")
public class UsuarioCreateDTO {

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Correo electrónico del usuario", example = "juan@correo.com")
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Correo inválido")
    private String correo;

    @Schema(description = "Contraseña de acceso", example = "clave123")
    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;

    @Schema(description = "Rol del usuario", example = "organizador")
    private String rol;
}