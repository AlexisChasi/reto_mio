package com.example.crud.pruebaTec.service.serviceImpl;

import com.example.crud.pruebaTec.dto.CuentaDto;
import com.example.crud.pruebaTec.exeption.ResourceConflictException;
import com.example.crud.pruebaTec.exeption.ResourceNotFoundException;
import com.example.crud.pruebaTec.mapper.CuentaMapper;
import com.example.crud.pruebaTec.model.Cliente;
import com.example.crud.pruebaTec.model.Cuenta;
import com.example.crud.pruebaTec.repository.ClienteRepository;
import com.example.crud.pruebaTec.repository.CuentaRepository;
import com.example.crud.pruebaTec.service.CuentaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public CuentaServiceImpl(CuentaRepository cuentaRepository, ClienteRepository clienteRepository) {
        this.cuentaRepository = cuentaRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<CuentaDto> getCuentas() {
        log.info("Obteniendo todas las cuentas");
        return cuentaRepository.findAll()
                .stream()
                .map(CuentaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CuentaDto createCuenta(CuentaDto cuentaDto) {
        log.info("Creando nueva cuenta con número: {}", cuentaDto.getNumeroCuenta());
        validarCuentaDto(cuentaDto);

        if (cuentaRepository.findByNumeroCuenta(cuentaDto.getNumeroCuenta()).isPresent()) {
            log.warn("Ya existe una cuenta con ese número: {}", cuentaDto.getNumeroCuenta());
            throw new ResourceConflictException("Ya existe una cuenta con ese número");
        }

        Cliente cliente = clienteRepository.findById(cuentaDto.getClienteId())
                .orElseThrow(() -> {
                    log.warn("Cliente no encontrado con ID: {}", cuentaDto.getClienteId());
                    return new ResourceNotFoundException("Cliente no encontrado");
                });

        Cuenta cuenta = CuentaMapper.toEntity(cuentaDto, cliente);
        Cuenta saved = cuentaRepository.save(cuenta);

        log.info("Cuenta creada exitosamente. ID: {}, número: {}", saved.getId(), saved.getNumeroCuenta());
        return CuentaMapper.toDto(saved);
    }

    @Override
    public CuentaDto updateCuenta(Long id, CuentaDto cuentaDto) {
        log.info("Actualizando cuenta con ID: {}", id);
        validarCuentaDto(cuentaDto);

        Cuenta existente = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe una cuenta con ese ID"));

        Cliente cliente = clienteRepository.findById(cuentaDto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        Cuenta cuenta = CuentaMapper.toEntity(cuentaDto, cliente);
        cuenta.setId(id);
        Cuenta updated = cuentaRepository.save(cuenta);

        log.info("Cuenta actualizada correctamente. ID: {}", updated.getId());
        return CuentaMapper.toDto(updated);
    }

    @Override
    public void deleteCuenta(Long id) {
        log.info("Solicitando eliminación de cuenta con ID: {}", id);
        if (!cuentaRepository.existsById(id)) {
            log.warn("No existe cuenta con ID: {}", id);
            throw new ResourceNotFoundException("No existe una cuenta con ese ID");
        }
        cuentaRepository.deleteById(id);
        log.info("Cuenta eliminada exitosamente. ID: {}", id);
    }


    private void validarCuentaDto(CuentaDto dto) {
        if (dto.getNumeroCuenta() == null || dto.getNumeroCuenta().isBlank()) {
            throw new IllegalArgumentException("Número de cuenta no puede estar vacío");
        }
        if (dto.getSaldoInicial() < 0) {
            throw new IllegalArgumentException("Saldo debe ser positivo o cero");
        }
        if (dto.getClienteId() == null) {
            throw new IllegalArgumentException("Debe especificar el cliente asociado");
        }
    }
}
