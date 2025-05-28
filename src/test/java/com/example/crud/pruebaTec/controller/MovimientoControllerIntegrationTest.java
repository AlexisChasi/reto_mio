package com.example.crud.pruebaTec.controller;

import com.example.crud.pruebaTec.dto.MovimientoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MovimientoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {

        String clienteJson = """
        {
          "clienteid": "joselema123",
          "nombre": "Jose Lema",
          "identificacion": "1234567890",
          "direccion": "Otavalo sn y principal",
          "telefono": "0985245785",
          "contrasena": "secreto123",
          "estado": true
        }
    """;

        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteJson))
                .andExpect(status().isCreated());

        // Crear cuenta
        String cuentaJson = """
        {
          "numeroCuenta": "478758",
          "tipoCuenta": "Ahorro",
          "saldoInicial": 2000,
          "estado": true,
          "clienteId": 1
        }
    """;

        mockMvc.perform(post("/api/v1/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuentaJson))
                .andExpect(status().isOk());
    }

    @Test
    void registrarMovimiento() throws Exception {
        MovimientoDto dto = new MovimientoDto();
        dto.setTipoMovimiento("Deposito");
        dto.setValor(500);
        dto.setNumeroCuenta("478758");

        mockMvc.perform(post("/api/v1/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.saldo").exists())
                .andExpect(jsonPath("$.valor").value(500));
    }
}
