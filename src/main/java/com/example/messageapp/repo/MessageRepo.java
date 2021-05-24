package com.example.messageapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.example.messageapp.entity.Message;

/**
 * collectionResourceRel: used as link relation, json name shown in the _links section
 * for the MessageController
 * path: is path used as base path for every url inside message resource
 * like: {url}/{path}{?page,size,sort}"
 */
@RepositoryRestResource (collectionResourceRel = "resource_message", path = "message")
public interface MessageRepo extends JpaRepository<Message, Integer> {
}
