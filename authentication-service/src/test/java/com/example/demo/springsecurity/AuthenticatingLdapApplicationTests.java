/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.demo.springsecurity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

/**
 *
 * @author Rob Winch
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticatingLdapApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void loginWithValidCustomerUserThenAuthenticated() throws Exception {
		FormLoginRequestBuilder login = formLogin()
				.user("externalUser")
				.password("pass");

		mockMvc.perform(login)
				.andExpect(authenticated().withUsername("externalUser"));
	}

	@Test
	public void loginWithValidEmployeeUserThenAuthenticated() throws Exception {
		FormLoginRequestBuilder login = formLogin()
			.user("internalUser")
			.password("pass");

		mockMvc.perform(login)
			.andExpect(authenticated().withUsername("internalUser"));
	}

	@Test
	public void loginWithInvalidUserThenUnauthenticated() throws Exception {
		FormLoginRequestBuilder login = formLogin()
			.user("invalid")
			.password("invalidpassword");

		mockMvc.perform(login)
			.andExpect(unauthenticated());
	}
}
