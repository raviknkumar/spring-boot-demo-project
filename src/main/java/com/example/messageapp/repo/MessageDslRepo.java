package com.example.messageapp.repo;

import java.util.List;
import com.example.messageapp.entity.Message;

public interface MessageDslRepo {

    List<Message> findAllByMessage(String msgToSearch);
}
