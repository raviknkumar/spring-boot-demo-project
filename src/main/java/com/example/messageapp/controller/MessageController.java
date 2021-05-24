package com.example.messageapp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.messageapp.entity.Message;
import com.example.messageapp.repo.MessageDslRepo;
import com.example.messageapp.repo.MessageRepo;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/my_message")
@RequiredArgsConstructor(onConstructor_ = {@Autowired })
public class MessageController {

    private final MessageDslRepo messageDslRepo;
    private final MessageRepo messageRepo;

    @RequestMapping("/filter")
    public List<Message> filter(@RequestParam("text") final String message){
        List<Message> messageList = messageDslRepo.findAllByMessage(message);
        return messageList;
    }

    @PostMapping ("")
    public Message save(@RequestBody Message message){
        message = messageRepo.save(message);
        return message;
    }
}
