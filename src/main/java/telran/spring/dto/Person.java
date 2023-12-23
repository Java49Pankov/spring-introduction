package telran.spring.dto;

import jakarta.validation.constraints.*;

public record Person(long id, 
		@Pattern(regexp = "[A-Z][a-z]{2,}", message = "Wrong name structure") String name, 
		@NotEmpty String city,
		@Email @NotEmpty String email,
		@Pattern(regexp = "(\\+972-?|0)5\\d-?\\d{7}", message = "not Israel mobile phone") @NotEmpty String phone) {

}
