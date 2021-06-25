package com.mongant.service;

import com.mongant.exceptions.WrongParameterException;
import com.mongant.model.Contact;
import com.mongant.model.ResponseContacts;
import com.mongant.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class AddressBookService {

    private final AddressBookRepository repository;
    private int maxPart;

    @Value("${process.partition}")
    private int partition;

    @Autowired
    public AddressBookService(AddressBookRepository repository) {
        this.repository = repository;
    }

    public ResponseContacts getContactsInfo(String regEx) throws Exception {
        ResponseContacts response = new ResponseContacts();

        if(StringUtils.isEmpty(regEx)) throw new WrongParameterException("nameFilter parameter must by not null!");

        response.setContacts(processPartition(regEx));
        return response;
    }

    private List<Contact> processPartition(String regEx) throws Exception {
        List<Contact> contacts = new ArrayList<>();
        try {
            Predicate<String> contactFilter = Pattern.compile(regEx).asPredicate();
            setMaxPart();
            int startPart = 1;
            while (startPart <= maxPart) {
                int endPart = startPart + partition;
                List<Contact> result = repository.findAllContacts(startPart, endPart);
                contacts.addAll(result.stream().filter(e -> !contactFilter.test(e.getName())).collect(Collectors.toList()));
                startPart = endPart + 1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
        return contacts;
    }

    public void setMaxPart() throws Exception{
        if(maxPart == 0) {
            maxPart = repository.findMaxPartition();
        }
    }
}
