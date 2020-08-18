package com.rafaelcampos.osworks.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafaelcampos.osworks.domain.model.Cliente;

@RestController
public class ClienteController {
	Cliente cliente1 = new Cliente();
	Cliente cliente2 = new Cliente();
	
	@GetMapping("/clientes")
	public List<Cliente> listar() {
		
		cliente1.setId(1L);
		cliente1.setNome("Rafael Campos");
		cliente1.setEmail("rafaelcampos@email.com");
		cliente1.setTelefone("34 99999-9999");
		
		cliente2.setId(2L);
		cliente2.setNome("Roberto Carlos");
		cliente2.setEmail("robertocarloss@email.com");
		cliente2.setTelefone("11 99999-9999");
		
		return Arrays.asList(cliente1, cliente2);
	}

}
