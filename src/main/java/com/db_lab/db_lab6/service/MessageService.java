package com.db_lab.db_lab6.service;

import com.db_lab.db_lab6.domain.Message;
import com.db_lab.db_lab6.exception.UserNotFoundException;
import com.db_lab.db_lab6.exception.MessageNotFoundException;
import com.db_lab.db_lab6.domain.Role;
import com.db_lab.db_lab6.repository.MessageRepository;
import com.db_lab.db_lab6.security.domain.SecurityCredentials;
import com.db_lab.db_lab6.security.repository.SecurityCredentialsRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final SecurityCredentialsRepository securityCredentialsRepository;

    public MessageService(MessageRepository messageRepository, SecurityCredentialsRepository securityCredentialsRepository) {
        this.messageRepository = messageRepository;
        this.securityCredentialsRepository = securityCredentialsRepository;
    }

    public List<Message> getMessages() {
        return messageRepository.findAll(Sort.by("id"));
    }

    public Message getMessage(Long id, Principal principal) {
        SecurityCredentials credentials = securityCredentialsRepository.findUserIdByLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
        Message message =  messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);
        Role role = credentials.getUserRole();
        if((principal.getName() == message.getSender()) || (role.toString() == "ADMIN")){
            return message;
        }else{
            return null;
        }
    }
    public void createMessage(Message message) {
        messageRepository.save(message);
    }

    public void updateMessage(Message message) {
        messageRepository.saveAndFlush(message);
    }

    public void deleteMessageById(Long id){
        messageRepository.deleteById(id);
    }

    public void deleteAllMessages(){
        messageRepository.deleteAll();
    }
}
