package com.example.crud.pruebaTec.service.serviceImpl;

import com.example.crud.pruebaTec.exeption.ResourceConflictException;
import com.example.crud.pruebaTec.exeption.ResourceNotFoundException;
import com.example.crud.pruebaTec.model.Cliente;
import com.example.crud.pruebaTec.model.Cuenta;
import com.example.crud.pruebaTec.repository.ClienteRepository;
import com.example.crud.pruebaTec.repository.CuentaRepository;
import com.example.crud.pruebaTec.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public List<Cuenta> getCuentas() {
        return cuentaRepository.findAll();
    }

    @Override
    public Cuenta saveOrUpdateCuenta(Cuenta cuenta) {
        Optional<Cuenta> existingCuenta = cuentaRepository.findCuentaByNumeroCuenta(cuenta.getNumeroCuenta());

        if (existingCuenta.isPresent() && cuenta.getId() == null) {
            throw new ResourceConflictException("Ya existe una cuenta con ese número de cuenta");
        }

        Optional<Cliente> clienteOpt = clienteRepository.findById(cuenta.getCliente().getId());
        if (!clienteOpt.isPresent()) {
            throw new ResourceNotFoundException("Cliente no encontrado");
        }

        return cuentaRepository.save(cuenta);
    }

    @Override
    public ResponseEntity<Object> deleteCuenta(Long id) {
        if (!cuentaRepository.existsById(id)) {
            throw new ResourceNotFoundException("No existe una cuenta con ese ID");
        }
        cuentaRepository.deleteById(id);
        return null;
    }
}
