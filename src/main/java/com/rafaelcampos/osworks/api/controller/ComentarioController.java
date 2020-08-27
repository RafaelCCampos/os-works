package com.rafaelcampos.osworks.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rafaelcampos.osworks.api.inputmodel.ComentarioInputModel;
import com.rafaelcampos.osworks.api.representationmodel.ComentarioRepresentationModel;
import com.rafaelcampos.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.rafaelcampos.osworks.domain.model.Comentario;
import com.rafaelcampos.osworks.domain.model.OrdemServico;
import com.rafaelcampos.osworks.domain.repository.OrdemServicoRepository;
import com.rafaelcampos.osworks.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@GetMapping
	public List<ComentarioRepresentationModel> listar(@PathVariable Long ordemServicoId) {
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
		
		return toCollectionModel(ordemServico.getComentarios());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioRepresentationModel adicionar(@PathVariable Long ordemServicoId,
			@RequestBody @Valid ComentarioInputModel comentarioInput) {
		Comentario comentario = gestaoOrdemServico.adicionarComentario(ordemServicoId,
				comentarioInput.getDescricao());
		return toModel(comentario);
	}
		
	private ComentarioRepresentationModel toModel(Comentario comentario) {
		return modelMapper.map(comentario, ComentarioRepresentationModel.class);
	}
	
	private List<ComentarioRepresentationModel> toCollectionModel(List<Comentario> comentarios) {
		return comentarios.stream().map(comentario -> toModel(comentario))
				.collect(Collectors.toList());
	}
}