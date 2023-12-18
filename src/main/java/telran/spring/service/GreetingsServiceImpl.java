package telran.spring.service;

import java.util.*;

import org.springframework.stereotype.Service;

import telran.spring.Person;

@Service
public class GreetingsServiceImpl implements GreetingsService {
	Map<Long, Person> greetingsMap = new HashMap<>();

	@Override
	public String getGreetings(long id) {
		Person person = greetingsMap.get(id);
		String personName = person == null ? " Unknown Guest" : person.name();
		return "Hello, " + personName;
	}

	@Override
	public Person addPerson(Person person) {
		long personId = person.id();
		if (greetingsMap.containsKey(personId)) {
			throw new IllegalStateException(person.id() + " already exist");
		}
		greetingsMap.put(personId, person);
		return person;
	}

	@Override
	public Person getPerson(long id) {
		return greetingsMap.get(id);
	}

	@Override
	public List<Person> getPersonsByCity(String city) {
		return greetingsMap.values().stream().filter(c -> c.city().equals(city)).toList();
	}

	@Override
	public Person deletePerson(long id) {
		if (greetingsMap.containsKey(id)) {
			throw new IllegalStateException(id + " does not exist");
		}
		return greetingsMap.remove(id);
	}

	@Override
	public Person updatePerson(Person person) {
		long personId = person.id();
		if (!greetingsMap.containsKey(personId)) {
			throw new IllegalStateException(personId + " does not exist");
		}
		greetingsMap.put(personId, person);
		return person;
	}

}
