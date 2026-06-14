package com.food.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de un usuario")
public class UsuarioDTO {

    @Schema(description = "Identificador único del usuario", example = "1")
    private Long idUsuario;

    @Schema(description = "Nombre del usuario", example = "Juan Pérez")
    private String nombre;

    @Schema(description = "Correo electrónico", example = "juan@correo.com")
    private String correo;

    @Schema(description = "Rol asignado", example = "organizador")
    private String rol;

    @Schema(description = "Estado del usuario", example = "activo")
    private String estado;
}