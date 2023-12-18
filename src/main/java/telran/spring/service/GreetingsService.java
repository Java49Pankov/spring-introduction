package telran.spring.service;

import telran.spring.Person;

public interface GreetingsService {
	String getGreetings(long id);

	// TODO add for HW #57
	// Person getPerson(long id);
	// List<Person> getPersonsByCity(String city);

	// TODO update for HW #57
	// Person addPerson(Person person);
	// Person deletePerson(long id);
	// Person updatePerson(Person person);
	String addName(IdName idName);

	String deleteName(long id);

	String updateName(IdName idName);
}
