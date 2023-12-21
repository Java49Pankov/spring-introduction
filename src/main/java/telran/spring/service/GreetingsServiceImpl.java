package telran.spring.service;

import java.util.*;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import telran.exceptions.NotFoundException;
import telran.spring.dto.Person;

@Service
@Slf4j
public class GreetingsServiceImpl implements GreetingsService {
	Map<Long, Person> greetingsMap = new HashMap<>();

	@Override
	public String getGreetings(long id) {
		Person person = greetingsMap.get(id);
		String name = "";
		if (person == null) {
			log.error("person with id {} not found", id);
			name = "Unknown guest";
		} else {
			name = person.name();
			log.debug("person name is {}", name);

		}
		return "Hello, " + name;
	}

	@Override
	public Person getPerson(long id) {
		Person person = greetingsMap.get(id);
		if (person == null) {
			log.error("person with id {} not found", id);
		} else {
			log.debug("person with id {} exists", id);
		}
		return person;
	}

	@Override
	public List<Person> getPersonsByCity(String city) {
		return greetingsMap.values().stream().filter(p -> p.city().equals(city)).toList();
	}

	@Override
	public Person addPerson(Person person) {
		long id = person.id();
		if (greetingsMap.containsKey(id)) {
			throw new IllegalStateException(String.format("person with id %d already exists", id));
		}
		greetingsMap.put(id, person);
		log.debug("person with id {} has been saved", id);
		return person;
	}

	@Override
	public Person deletePerson(long id) {
		if (!greetingsMap.containsKey(id)) {
			throw new NotFoundException(String.format("person with id %d doesn't exist", id));
		}
		Person person = greetingsMap.remove(id);
		log.debug("person with id {} has been deleted", id);
		return person;
	}

	@Override
	public Person updatePerson(Person person) {
		long id = person.id();
		if (!greetingsMap.containsKey(id)) {
			throw new NotFoundException(String.format("person with id %d doesn't exist", id));
		}
		greetingsMap.put(id, person);
		log.debug("person with id {} has changed", id);
		return person;
	}

}
