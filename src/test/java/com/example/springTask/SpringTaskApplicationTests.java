package com.example.springTask;

import org.apache.commons.validator.routines.EmailValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SpringTaskApplicationTests {

	@Test
	public final void testEmailValidator() {
		List<String> validEmails = Arrays.asList("test@mail.ru", "test2@mail.md", "ppasd.ghusfhfd@gmail.md", "sadsarsf.fgre@sdgfsgd");
		List<String> invalidEmails = Arrays.asList("", "   ", "\r\t   \r\t", "hellgr", "dfsdf  @ dafsdfsd", "sad222....@asd...mail.md");
		for(String email: validEmails) {
			Assertions.assertTrue(EmailValidator.getInstance(true).isValid(email));
		}
		for(String email: invalidEmails) {
			Assertions.assertFalse(EmailValidator.getInstance(true).isValid(email));
		}

	}
}
