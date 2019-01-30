package com.andregcaires.webstoreapi.resources;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.andregcaires.webstoreapi.domain.Categoria;
import com.andregcaires.webstoreapi.dto.CategoriaDTO;
import com.andregcaires.webstoreapi.services.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Categoria body = service.find(id);
		
		return ResponseEntity.ok().body(body);
	}
	
	@RequestMapping(value = {"/", ""}, method= RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {

		List<CategoriaDTO> body = new ArrayList<>();
		
		// 1
		for(Categoria obj : service.findAll()) {
			body.add(new CategoriaDTO(obj));
		}
		
		/* 2
		List<CategoriaDTO> listaDTO = service.findAll().stream()
				.map( obj -> new CategoriaDTO(obj)).collect(Collectors.toList() );
		//*/
		return ResponseEntity.ok().body(body);
	}
	
	@RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj) {
		Categoria body = service.insert(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(body.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping( value = {"{id}", ""}, method = RequestMethod.PUT )
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id) {
		
		if(obj.getId() != id) {
			obj.setId(id);			
		}
		
		Categoria body = service.update(obj);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping( value = {"/{id}"}, method = RequestMethod.DELETE )
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		
		service.delete(id);
		return ResponseEntity.noContent().build();
		
	}
	
}
