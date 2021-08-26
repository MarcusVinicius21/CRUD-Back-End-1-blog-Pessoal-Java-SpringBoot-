package br.org.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.generation.blogpessoal.model.Postagens;
import br.org.generation.blogpessoal.repository.PostagensRepository;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagensController {

	@Autowired
	private PostagensRepository postagensRepository;

	@GetMapping
	public ResponseEntity<List<Postagens>> getAll() {
		return ResponseEntity.ok(postagensRepository.findAll());
	}

	@GetMapping("idifelse/{id}")
	public ResponseEntity<Postagens> getByIdIfElse(@PathVariable long id) {

		Optional<Postagens> postagens = postagensRepository.findById(id);

		if (postagens.isPresent()) {
			return ResponseEntity.ok(postagens.get());
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Postagens> getById(@PathVariable long id) {
		return postagensRepository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagens>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagensRepository.findAllByTituloContainingIgnoreCase(titulo));
	}

	@PostMapping
	public ResponseEntity<Postagens> postPostagens(@RequestBody Postagens postagens) {
		return ResponseEntity.status(HttpStatus.CREATED).body(postagensRepository.save(postagens));
	}

	@PutMapping
	public ResponseEntity<Postagens> putPostagens(@RequestBody Postagens postagens) {
		return ResponseEntity.status(HttpStatus.OK).body(postagensRepository.save(postagens));
	}

	@DeleteMapping
	public void deletePostagens(@PathVariable long id) {
		postagensRepository.deleteById(id);
	}
}
