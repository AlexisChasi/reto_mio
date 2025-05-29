package com.example.crud.pruebaTec.controller;

import com.example.crud.pruebaTec.dto.ClienteDto;
import com.example.crud.pruebaTec.service.ClienteService;
import com.example.crud.pruebaTec.validation.OnCreate;
import com.example.crud.pruebaTec.validation.OnUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/clientes")
@Tag(name = "Clientes", description = "Operaciones sobre clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Obtener todos los clientes", description = "Devuelve una lista de todos los clientes registrados")
    @GetMapping
    public ResponseEntity<List<ClienteDto>> getClientes() {
        return ResponseEntity.ok(clienteService.getClientes());
    }

    @Operation(summary = "Crear nuevo cliente", description = "Registra un nuevo cliente con sus datos personales")
    @PostMapping
    public ResponseEntity<ClienteDto> createCliente(
            @Validated(OnCreate.class) @RequestBody ClienteDto clienteDTO) {
        return new ResponseEntity<>(clienteService.createCliente(clienteDTO), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Actualizar cliente",
            description = "Actualiza la información de un cliente existente por ID",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos permitidos para actualizar",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "ClienteUpdateExample",
                                    summary = "Ejemplo de cliente actualizado",
                                    value = "{\n" +
                                            "  \"nombre\": \"Whelinc\",\n" +
                                            "  \"direccion\": \"Amazonas\",\n" +
                                            "  \"telefono\": \"3898037864\",\n" +
                                            "  \"contrasena\": \"Contrase$13\",\n" +
                                            "  \"estado\": true\n" +
                                            "}"
                            )
                    )
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> updateCliente(
            @PathVariable Long id,
            @Validated(OnUpdate.class) @RequestBody ClienteDto clienteDTO) {
        return ResponseEntity.ok(clienteService.updateCliente(id, clienteDTO));
    }


    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente existente por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}
