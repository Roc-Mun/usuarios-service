package com.food.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para actualizar un usuario")
public class UsuarioUpdateDTO {

    @Schema(description = "Nuevo nombre del usuario", example = "Juan Pérez")
    private String nombre;

    @Schema(description = "Nuevo correo electrónico", example = "juan@correo.com")
    private String correo;
}