package com.example.messageapp.repo;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.example.messageapp.entity.Message;
import com.example.messageapp.entity.QMessage;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class MessageDslRepoImpl implements MessageDslRepo{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Message> findAllByMessage(String msgToSearch) {
        final JPAQuery<Message> query = new JPAQuery<>(em);
        final QMessage message = QMessage.message1;
        return query.from(message).where(message.message.eq(msgToSearch)).fetch();
    }
}
