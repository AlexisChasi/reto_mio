package com.example.crud.pruebaTec.controller;

import com.example.crud.pruebaTec.dto.ClienteDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearClienteExitosamente() throws Exception {
        ClienteDto clienteDto = ClienteDto.builder()
                .clienteid("clienteTest01")
                .nombre("Carlos Pérez")
                .identificacion("1234567001")
                .direccion("Av. Amazonas 123")
                .telefono("0987654321")
                .contrasena("clave123")
                .estado(true)
                .build();

        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clienteid").value("clienteTest01"));
    }

    @Test
    void obtenerTodosLosClientes() throws Exception {
        mockMvc.perform(get("/api/v1/clientes"))
                .andExpect(status().isOk());
    }
}
