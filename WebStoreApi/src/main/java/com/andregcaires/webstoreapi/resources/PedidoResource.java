package com.andregcaires.webstoreapi.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.andregcaires.webstoreapi.domain.Pedido;
import com.andregcaires.webstoreapi.dto.CategoriaDTO;
import com.andregcaires.webstoreapi.services.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService _service;
	
	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
	public ResponseEntity<?> findAll() {
		List<Pedido> body = _service.findAll();
		
		return ResponseEntity.ok().body(body);
	}
	
	@RequestMapping(value = {"/{id}", "/{id}/"}, method = RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		Pedido body = _service.find(id);
		
		return ResponseEntity.ok().body(body);
	}
	
	@RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) {
		obj = _service.insert(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping( value = {"/page", "/page/"}, method = RequestMethod.GET )
	public ResponseEntity<Page<Pedido>> findPage( 
			@RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex
			, @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage
			, @RequestParam(value = "orderBy", defaultValue = "instante") String orderBy
			, @RequestParam(value = "direction", defaultValue = "DESC") String direction ) {
		
		Page<Pedido> body = _service.findPage(pageIndex, linesPerPage, orderBy, direction);

		return ResponseEntity.ok().body(body);
	}
}
