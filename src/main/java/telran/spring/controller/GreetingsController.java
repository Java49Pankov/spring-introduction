package telran.spring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import telran.spring.dto.Person;
import telran.spring.service.GreetingsService;

@RestController
@RequestMapping("greetings")
@RequiredArgsConstructor
public class GreetingsController {
	final GreetingsService greetingsService;

	@GetMapping("{id}")
	String getGreetings(@PathVariable long id) {
		return greetingsService.getGreetings(id);
	}

	@PostMapping
	Person addPerson(@RequestBody Person person) {
		return greetingsService.addPerson(person);
	}

	@GetMapping("id/{id}")
	Person getPerson(@PathVariable long id) {
		return greetingsService.getPerson(id);
	}

	@DeleteMapping("{id}")
	Person deletePerson(@PathVariable long id) {
		return greetingsService.deletePerson(id);
	}

	@PutMapping
	Person updatePerson(@RequestBody Person person) {
		return greetingsService.updatePerson(person);
	}

	@GetMapping("city/{city}")
	List<Person> getPersonByCity(@PathVariable String city) {
		return greetingsService.getPersonsByCity(city);
	}

}
