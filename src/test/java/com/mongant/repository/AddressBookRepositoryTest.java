package com.mongant.repository;

import com.mongant.configuration.AppConfig;
import com.mongant.model.Contact;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@TestPropertySource(locations = "classpath:test.properties")
public class AddressBookRepositoryTest {

    @Autowired
    AddressBookRepository repository;

    @Test
    public void testFindAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        try {
            contacts = repository.findAllContacts(1, 10);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        assertFalse(contacts.isEmpty());
    }
}