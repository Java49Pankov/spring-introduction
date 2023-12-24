package telran.spring;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import telran.spring.controller.GreetingsController;
import telran.spring.dto.Person;
import telran.spring.service.GreetingsService;

record PersonIdString(String id, String name, String city, String mail, String phone) {
}

@WebMvcTest
public class GreetingsControllerTest {
	@Autowired
	GreetingsController controller;

	@MockBean
	GreetingsService greetingsService;

	@Autowired
	MockMvc mockMvc;

	Person personNormal = new Person(123, "Vasya", "Rehovot", "vasya@gmail.com", "054-1234567");
	Person personNormalUpdate = new Person(123, "Vasya", "Moscow", "vasya@gmail.com", "054-1234567");

	Person personWrongPhone = new Person(124, "Vasya", "Rehovot", "vasya@gmail.com", "54-1234567");
	Person personWrongEmail = new Person(123, "Vasya", "Rehovot", "vasyagmail.com", "054-1234567");
	Person personWrongCity = new Person(123, "Vasya", "Rehovot", "", "054-1234567");
	PersonIdString personWrongIdString = new PersonIdString("abs", "Vasya", "Rehovot", "vasya@gmail.com",
			"054-1234567");
	Person personWrongName = new Person(123, "sa", "Rehovot", "vasya@gmail.com", "054-1234567");

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void applicationContextTest() {
		assertNotNull(controller);
		assertNotNull(greetingsService);
		assertNotNull(mockMvc);
		assertNotNull(objectMapper);
	}

	@Test
	void normalFlowAddPersonTest() throws Exception {
		mockMvc
				.perform(post("http://localhost:8080/greetings")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(personNormal)))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void addPersonWrongPhoneTest() throws Exception {
		String response = mockMvc
				.perform(post("http://localhost:8080/greetings")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(personWrongPhone)))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn()
				.getResponse()
				.getContentAsString();

		assertEquals("not Israel mobile phone", response);
	}

	@Test
	void addPersonWrongEmailTest() throws Exception {
		String response = mockMvc
				.perform(post("http://localhost:8080/greetings")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(personWrongEmail)))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn()
				.getResponse()
				.getContentAsString();

		assertEquals("must be a well-formed email address", response);
	}

	@Test
	void addPersonWrongCityTest() throws Exception {
		String response = mockMvc
				.perform(post("http://localhost:8080/greetings")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(personWrongCity)))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn()
				.getResponse()
				.getContentAsString();

		assertEquals("must not be empty", response);
	}

	@Test
	void addPersonWrongNameTest() throws Exception {
		String response = mockMvc
				.perform(post("http://localhost:8080/greetings")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(personWrongName)))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn()
				.getResponse()
				.getContentAsString();

		assertEquals("Wrong name structure", response);
	}

	@Test
	void addPersonWrongIdTest() throws Exception {
		String response = mockMvc
				.perform(post("http://localhost:8080/greetings")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(personWrongIdString)))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn()
				.getResponse()
				.getContentAsString();

		assertEquals(
				"JSON parse error: Cannot deserialize value of type `long` from String \"abs\": not a valid `long` value",
				response);
	}

	@Test
	void updatePersonTest() throws Exception {
		mockMvc
				.perform(put("http://localhost:8080/greetings")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(personNormalUpdate)))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void getPersonTest() throws Exception {
		mockMvc
				.perform(get("http://localhost:8080/greetings/id/123"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void deletePersonTest() throws Exception {
		mockMvc
				.perform(delete("http://localhost:8080/greetings/123"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void getPersonByCityTest() throws Exception {
		mockMvc
				.perform(get("http://localhost:8080/greetings/city/Rehovot"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void getGreetingsTest() throws Exception {
		mockMvc
				.perform(get("http://localhost:8080/greetings/123"))
				.andDo(print())
				.andExpect(status().isOk());
	}
}
