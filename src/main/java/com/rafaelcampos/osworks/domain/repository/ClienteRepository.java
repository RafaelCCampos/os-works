package com.rafaelcampos.osworks.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rafaelcampos.osworks.domain.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	List<Cliente> findByNome(String nome);
	
	//Buscando por partes do nome.
	List<Cliente> findByNomeContaining(String nome);
	
	//Usar o nome da variável da entidade.
	List<Cliente> findByTelefone(String fone);
}
