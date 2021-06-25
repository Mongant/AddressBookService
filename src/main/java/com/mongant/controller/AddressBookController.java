package com.mongant.controller;

import com.mongant.model.ResponseContacts;
import com.mongant.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressBookController {

    private final AddressBookService service;

    @Autowired
    public AddressBookController(AddressBookService service) {
        this.service = service;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/hello/contacts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseContacts getContactsInformation(@RequestParam("nameFilter") String filterRegExp) throws Exception{
        return service.getContactsInfo(filterRegExp);
    }
}
