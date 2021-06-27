package com.mongant.service;

import com.mongant.exceptions.WrongParameterException;
import com.mongant.model.Contact;
import com.mongant.model.ResponseContacts;
import com.mongant.repository.DataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
@DependsOn("dataInitializer")
public class AddressBookService {

    private final AsyncProcessing asyncProcessing;
    private final int maxId;

    @Value("${process.partition}")
    private int partition;

    @Autowired
    public AddressBookService(AsyncProcessing asyncProcessing) {
        this.asyncProcessing = asyncProcessing;
        maxId = DataInitializer.getMaxId();
    }

    public ResponseContacts getContactsInfo(String regEx) throws Exception {
        ResponseContacts response = new ResponseContacts();

        //throw exception if regular expression is empty
        if(StringUtils.isEmpty(regEx)) throw new WrongParameterException("nameFilter parameter must by not null!");

        response.setContacts(processPartition(regEx));
        return response;
    }

    private List<Contact> processPartition(String regEx) throws Exception {
        List<Future<List<Contact>>> futureContacts = new ArrayList<>();
        try {
            Predicate<String> contactFilter = Pattern.compile(regEx).asPredicate();
            int startPart = 1;
            while (startPart <= maxId) {
                int endPart = startPart + partition;
                Thread currentThread = Thread.currentThread();
                System.out.println("processPartition thread: " + currentThread.getName());
                futureContacts.add(asyncProcessing.getFutureContacts(contactFilter, startPart, endPart));
                startPart = endPart + 1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
        return convertFuturesToContacts(futureContacts);
    }

    private List<Contact> convertFuturesToContacts(List<Future<List<Contact>>> futureContacts) throws Exception {
        List<Contact> contacts = new ArrayList<>();
        for(Future<List<Contact>> future : futureContacts) {
            contacts.addAll(future.get());
        }
        return contacts;
    }
}
