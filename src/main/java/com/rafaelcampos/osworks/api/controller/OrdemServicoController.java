package com.rafaelcampos.osworks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rafaelcampos.osworks.api.inputmodel.OrdemServicoInputModel;
import com.rafaelcampos.osworks.api.representationmodel.OrdemServicoRepresentationModel;
import com.rafaelcampos.osworks.domain.model.OrdemServico;
import com.rafaelcampos.osworks.domain.repository.OrdemServicoRepository;
import com.rafaelcampos.osworks.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoRepresentationModel criar(@Valid @RequestBody OrdemServicoInputModel ordemServicoInput) {
		OrdemServico ordemServico = toEntity(ordemServicoInput);
		
		return toModel(gestaoOrdemServico.criar(ordemServico));
	}
	
	@GetMapping
	public List<OrdemServicoRepresentationModel> listar() {
		return toCollectionModel(ordemServicoRepository.findAll());
	}
	
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoRepresentationModel> buscar(@PathVariable Long ordemServicoId) {
		Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);
		
		if (ordemServico.isPresent()) {
			OrdemServicoRepresentationModel ordemServicoRepresentation = toModel(ordemServico.get());
			
			return ResponseEntity.ok(ordemServicoRepresentation);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("{ordemServicoId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long ordemServicoId) {
		gestaoOrdemServico.fechar(ordemServicoId);
	}
	
	@PutMapping("{ordemServicoId}/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelar(@PathVariable Long ordemServicoId) {
		gestaoOrdemServico.cancelar(ordemServicoId);
	}
	
	private OrdemServicoRepresentationModel toModel(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoRepresentationModel.class);
	}
	
	private List<OrdemServicoRepresentationModel> toCollectionModel(List<OrdemServico> ordensServico) {
		return ordensServico.stream()
				.map(ordemServico -> toModel(ordemServico))
				.collect(Collectors.toList());
	}
	
	private OrdemServico toEntity(OrdemServicoInputModel ordemServicoInput) {
		return modelMapper.map(ordemServicoInput, OrdemServico.class);
	}
}
