package com.example.crud.pruebaTec.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CuentaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static boolean clienteCreado = false;

    @BeforeEach
    void setupCliente() throws Exception {
        // Evitar crear el cliente más de una vez
        if (!clienteCreado) {
            String clienteJson = """
            {
              "clienteid": "clienteCuenta01",
              "nombre": "Laura Soto",
              "identificacion": "0987654321",
              "direccion": "Calle 10",
              "telefono": "0999988776",
              "contrasena": "secreta123",
              "estado": true
            }
            """;

            mockMvc.perform(post("/api/v1/clientes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(clienteJson))
                    .andExpect(status().isCreated())
                    .andReturn();

            clienteCreado = true;
        }
    }

    @Test
    void crearCuenta() throws Exception {
        String cuentaJson = """
        {
          "numeroCuenta": "12345678",
          "tipoCuenta": "Corriente",
          "saldoInicial": 1000,
          "estado": true,
          "clienteId": 1
        }
        """;

        mockMvc.perform(post("/api/v1/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuentaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroCuenta").value("12345678"));
    }

    @Test
    void obtenerCuentas() throws Exception {
        mockMvc.perform(get("/api/v1/cuentas"))
                .andExpect(status().isOk());
    }
}
