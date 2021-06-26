package com.mongant.service;

import com.mongant.model.Contact;
import com.mongant.repository.AddressBookRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class AsyncProcessing {

    private final AddressBookRepository repository;

    public AsyncProcessing(AddressBookRepository repository) {
        this.repository = repository;
    }

    @Async("asyncExecutor")
    public Future<List<Contact>> getFutureContacts(Predicate<String> contactFilter, int startPart, int endPart) throws Exception{
        List<Contact> result = repository.findAllContacts(startPart, endPart);
        System.out.println("getFutureContacts thread: " + Thread.currentThread().getName());
        return  new AsyncResult<>(result.parallelStream().
                filter(e -> !contactFilter.test(e.getName())).
                collect(Collectors.toList()));
    }
}
