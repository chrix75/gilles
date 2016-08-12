package com.imscd.poc.domain.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Christian Sperandio on 16/07/2016.
 */
@RestController
@RequestMapping("/contact")
public class ContactController {
    private static Logger logger = LoggerFactory.getLogger(ContactController.class);

    @RequestMapping(path = "/{contactId}", method = RequestMethod.GET)
    public Map<String, String> getContactFromId(@PathVariable Long contactId) {
        logger.info("Fetch data about the contact with the ID " + contactId);

        // Fake - Beginning
        Map<String, String> user = new HashMap<>();
        user.put("name", "Christian Sperandio");
        user.put("title", "Software Architect");
        // Fake - End

        return user;
    }



}
