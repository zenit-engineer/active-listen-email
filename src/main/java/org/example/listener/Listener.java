package org.example.listener;

import org.example.service.EmailService;

import java.util.concurrent.Callable;

public class Listener implements Callable<Void> {
    private final EmailService service;

    public Listener(EmailService service) {
        this.service = service;
    }

    @Override
    public Void call() throws Exception {
        service.checkInbox();
        return null;
    }
}
