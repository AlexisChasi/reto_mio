package com.example.crud.pruebaTec.service;

import com.example.crud.pruebaTec.dto.CuentaDto;
import com.example.crud.pruebaTec.exeption.ResourceConflictException;
import com.example.crud.pruebaTec.exeption.ResourceNotFoundException;
import com.example.crud.pruebaTec.mapper.CuentaMapper;
import com.example.crud.pruebaTec.model.Cliente;
import com.example.crud.pruebaTec.model.Cuenta;
import com.example.crud.pruebaTec.repository.ClienteRepository;
import com.example.crud.pruebaTec.repository.CuentaRepository;
import com.example.crud.pruebaTec.service.serviceImpl.CuentaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CuentaServiceTest {

    private CuentaRepository cuentaRepository;
    private ClienteRepository clienteRepository;
    private CuentaService cuentaService;

    @BeforeEach
    void setUp() {
        cuentaRepository = mock(CuentaRepository.class);
        clienteRepository = mock(ClienteRepository.class);
        cuentaService = new CuentaServiceImpl(cuentaRepository, clienteRepository);
    }

    @Test
    void crearCuentaExitosamente() {
        CuentaDto dto = CuentaDto.builder()
                .numeroCuenta("12345678")
                .tipoCuenta("Ahorro")
                .saldoInicial(500)
                .estado(true)
                .clienteId(1L)
                .build();

        Cliente cliente = new Cliente();
        cliente.setId(1L);

        when(cuentaRepository.findByNumeroCuenta("12345678")).thenReturn(Optional.empty());
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cuenta cuenta = CuentaMapper.toEntity(dto, cliente);
        cuenta.setId(1L);
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuenta);

        CuentaDto result = cuentaService.createCuenta(dto);

        assertNotNull(result);
        assertEquals("12345678", result.getNumeroCuenta());
    }

    @Test
    void errorSiNumeroCuentaYaExiste() {
        CuentaDto dto = CuentaDto.builder()
                .numeroCuenta("12345678")
                .tipoCuenta("Ahorro")
                .saldoInicial(500)
                .estado(true)
                .clienteId(1L)
                .build();

        when(cuentaRepository.findByNumeroCuenta("12345678"))
                .thenReturn(Optional.of(new Cuenta()));

        assertThrows(ResourceConflictException.class, () -> cuentaService.createCuenta(dto));
    }

    @Test
    void errorSiClienteNoExisteAlCrear() {
        CuentaDto dto = CuentaDto.builder()
                .numeroCuenta("12345678")
                .tipoCuenta("Ahorro")
                .saldoInicial(500)
                .estado(true)
                .clienteId(99L)
                .build();

        when(cuentaRepository.findByNumeroCuenta("12345678")).thenReturn(Optional.empty());
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cuentaService.createCuenta(dto));
    }

    @Test
    void obtenerTodasLasCuentas() {
        Cuenta cuenta = new Cuenta();
        cuenta.setId(1L);
        cuenta.setNumeroCuenta("12345678");
        cuenta.setTipoCuenta("Corriente");
        cuenta.setSaldoInicial(1000);
        cuenta.setEstado(true);
        cuenta.setCliente(new Cliente());

        when(cuentaRepository.findAll()).thenReturn(List.of(cuenta));

        List<CuentaDto> result = cuentaService.getCuentas();

        assertEquals(1, result.size());
        assertEquals("12345678", result.get(0).getNumeroCuenta());
    }

    @Test
    void actualizarCuentaExitosamente() {
        CuentaDto dto = CuentaDto.builder()
                .numeroCuenta("87654321")
                .tipoCuenta("Ahorro")
                .saldoInicial(1000)
                .estado(false)
                .clienteId(1L)
                .build();

        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Cuenta cuentaExistente = new Cuenta();
        cuentaExistente.setId(1L);

        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuentaExistente));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(CuentaMapper.toEntity(dto, cliente));

        CuentaDto result = cuentaService.updateCuenta(1L, dto);

        assertEquals("87654321", result.getNumeroCuenta());
        assertEquals("Ahorro", result.getTipoCuenta());
    }

    @Test
    void errorAlActualizarCuentaNoExiste() {
        when(cuentaRepository.findById(999L)).thenReturn(Optional.empty());

        CuentaDto dto = CuentaDto.builder()
                .numeroCuenta("00000001")
                .tipoCuenta("Ahorro")
                .saldoInicial(300)
                .estado(true)
                .clienteId(1L)
                .build();

        assertThrows(ResourceNotFoundException.class, () -> cuentaService.updateCuenta(999L, dto));
    }

    @Test
    void errorAlActualizarSiClienteNoExiste() {
        CuentaDto dto = CuentaDto.builder()
                .numeroCuenta("00000001")
                .tipoCuenta("Ahorro")
                .saldoInicial(300)
                .estado(true)
                .clienteId(99L)
                .build();

        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(new Cuenta()));
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cuentaService.updateCuenta(1L, dto));
    }

    @Test
    void eliminarCuentaExitosamente() {
        when(cuentaRepository.existsById(1L)).thenReturn(true);

        cuentaService.deleteCuenta(1L);

        verify(cuentaRepository, times(1)).deleteById(1L);
    }

    @Test
    void errorAlEliminarCuentaInexistente() {
        when(cuentaRepository.existsById(999L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> cuentaService.deleteCuenta(999L));
    }
}
