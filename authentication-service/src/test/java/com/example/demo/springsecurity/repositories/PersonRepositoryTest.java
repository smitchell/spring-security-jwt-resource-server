package com.example.demo.springsecurity.repositories;

import com.example.demo.springsecurity.models.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;


    @Test
    public void testGetAllPersons() {
        List<Person> persons = personRepository.getAllPersons();
        assertNotNull(persons);
        assertEquals(persons.size(), 2);
    }

    @Test
    public void testGetAllPersonsNames() {
        List<String> persons = personRepository.getAllPersonNames();
        assertNotNull(persons);
        assertEquals(persons.size(), 2);
    }

    @Test
    public void testFindPerson() {
        Person person = personRepository.findPerson("uid=john,ou=people,dc=example,dc=com");
        assertNotNull(person);
        assertEquals(person.getFullName(), "John Doe");
    }
}
