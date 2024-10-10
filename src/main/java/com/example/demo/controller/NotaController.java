package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Nota;
import com.example.demo.repository.NotaRepository;
//Criando a classe
@RestController
public class NotaController {
	@Autowired
	private NotaRepository notaRepository;
	//endpoint para fazer a operação
	@RequestMapping(value = "/nota", method = RequestMethod.GET)
	public List<Nota> Get() {
		return notaRepository.findAll();
	}
	
	@RequestMapping(value = "/nota/{id}", method = RequestMethod.GET)
	public ResponseEntity<Nota> GetById(@PathVariable(value ="id") long id)
	{
	
		Optional<Nota> nota = notaRepository.findById(id);
		if(nota.isPresent())
			return new ResponseEntity<Nota>(nota.get(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	//Para lançar os dados na API
	@RequestMapping(value = "/nota", method = RequestMethod.POST)
	public Nota Post(@RequestBody Nota nota)
	{
		return notaRepository.save(nota);
	}
	//Para alterar os dados na API
	@RequestMapping(value = "/nota/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Nota> atualizarNota(@PathVariable(value = "id") long id,
	                                           @RequestBody Nota newNota) {
	    // Opcional: Validar os campos de newNota aqui

	    Optional<Nota> notaAntigaOptional = notaRepository.findById(id);
	    if (notaAntigaOptional.isPresent()) {
	        Nota notaAntiga = notaAntigaOptional.get();
	        notaAntiga.setNomeDoAluno(newNota.getNomeDoAluno());
	        // Atualizar outros campos se necessário
	        notaRepository.save(notaAntiga);
	        return new ResponseEntity<>(newNota, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	//Para deletar os dados na API
	@RequestMapping(value = "/nota/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> Delete(@PathVariable(value = "id")long id)
	{
		Optional<Nota> nota = notaRepository.findById(id);
		if (nota.isPresent()) {
			notaRepository.delete(nota.get());
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
		
 }
