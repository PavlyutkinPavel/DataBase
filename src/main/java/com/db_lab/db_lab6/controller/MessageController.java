package com.db_lab.db_lab6.controller;

import com.db_lab.db_lab6.domain.Message;
import com.db_lab.db_lab6.security.controller.SecurityController;
import com.db_lab.db_lab6.security.domain.RegistrationDTO;
import com.db_lab.db_lab6.security.service.SecurityService;
import com.db_lab.db_lab6.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/message")
@Tag(name = "Message Controller", description = "Main controller, makes all operations with chats")
public class MessageController {
    private final MessageService messageService;
    private final SecurityService securityService;

    private final SecurityController securityController;

    private final Path ROOT_FILE_PATH = Paths.get("src/main/resources/static/file_messages");

    public MessageController(MessageService messageService, SecurityService securityService, SecurityController securityController) {
        this.messageService = messageService;
        this.securityService = securityService;
        this.securityController = securityController;
    }

    @Operation(summary = "get all messages(for admins)")
    @GetMapping
    public ResponseEntity<List<Message>> getMessages(Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            List<Message> messages = messageService.getMessages();
            if (messages.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(messages, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "get message(for authorized users)")
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessage(@PathVariable Long id, Principal principal) {
        Message message = messageService.getMessage(id, principal);
        if (message != null) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "create messages(for authorized users)")
    @PostMapping
    public ResponseEntity<HttpStatus> createMessage(@RequestBody Message message) {
        messageService.createMessage(message);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "create file message(for authorized users)")
    @PostMapping("/file")
    public ResponseEntity<HttpStatus> createFileMessage(@RequestParam("file") MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.ROOT_FILE_PATH.resolve(file.getOriginalFilename()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @Operation(summary = "update message(for authorized users)")
    @PutMapping
    public ResponseEntity<HttpStatus> updateMessage(@RequestBody Message message, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName()) || (Objects.equals(message.getSender(), principal.getName()))) {
            messageService.updateMessage(message);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "delete message(for authorized users and admins)")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMessage(@PathVariable Long id, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName()) || (Objects.equals(messageService.getMessage(id, principal).getSender(), principal.getName()))) {
            messageService.deleteMessageById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    //very strong!
    @Operation(summary = "delete all messages(for admins)")
    @DeleteMapping("/delete_all_messages")
    public ResponseEntity<HttpStatus> deleteAllMessages(Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            messageService.deleteAllMessages();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "send message(websocket)")
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(
            @Payload Message chatMessage
    ) {
        return chatMessage;
    }

    @Operation(summary = "add user(websocket)")
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Message addUser(
            @Payload Message chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setUserLogin((String) headerAccessor.getSessionAttributes().put("username", chatMessage.getSender()));
        registrationDTO.setFirstName((String) headerAccessor.getSessionAttributes().put("username", chatMessage.getSender()));
        registrationDTO.setLastName((String) headerAccessor.getSessionAttributes().put("username", chatMessage.getSender()));
        registrationDTO.setUserPassword("12345678");
        securityController.registration(registrationDTO);
        return chatMessage;
    }

}