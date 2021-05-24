package com.example.messageapp.dbevent.handlers;

import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import com.example.messageapp.entity.Message;
import lombok.extern.slf4j.Slf4j;

@RepositoryEventHandler
@Slf4j
public class MessageEventHandler {

    public MessageEventHandler() {
        super();
    }

    @HandleBeforeCreate()
    public void handleMessageBeforeCreate(Message message) {
        log.info("Inside Message Before Create....");
    }

    @HandleAfterCreate
    public void handleMessageAfterCreate(Message message) {
        log.info("Inside  After Create ....");
    }

    @HandleBeforeSave ()
    public void handleMessageBeforeSave(Message message) {
        log.info("Inside Message Before Save....");
    }

    @HandleAfterSave
    public void handleMessageAfterSave(Message message) {
        log.info("Inside  After Create ....");
    }

    @HandleBeforeDelete
    public void handleMessageBeforeDelete(Message Message) {
        log.info("Inside  Message Before Delete ....");
    }

    @HandleAfterDelete
    public void handleMessageAfterDelete(Message Message) {
        log.info("Inside  Message After Delete ....");
    }

}
