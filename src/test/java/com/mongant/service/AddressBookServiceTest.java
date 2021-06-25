package com.mongant.service;

import com.mongant.configuration.AppConfig;
import com.mongant.exceptions.WrongParameterException;
import com.mongant.model.Contact;
import com.mongant.model.ResponseContacts;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@TestPropertySource(locations = "classpath:test.properties")
public class AddressBookServiceTest {

    @Autowired
    private AddressBookService service;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testExceptionGetContactsInfo() throws Exception{
        exceptionRule.expect(WrongParameterException.class);
        exceptionRule.expectMessage("nameFilter parameter must by not null!");
        service.getContactsInfo("");
    }

    @Test
    public void testGetContactsInfo() {
        List<Contact> expectedResponse = new ArrayList<>();
        try {
            ResponseContacts response = service.getContactsInfo("^A.*$");
            assertEquals(8, response.getContacts().size());

            expectedResponse = response.getContacts().stream().
                    filter(e -> e.getName().startsWith("A")).
                    collect(Collectors.toList());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        assertTrue(expectedResponse.isEmpty());
    }
}