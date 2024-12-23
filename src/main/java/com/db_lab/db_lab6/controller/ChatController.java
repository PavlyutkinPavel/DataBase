package com.db_lab.db_lab6.controller;

import com.db_lab.db_lab6.domain.Chat;
import com.db_lab.db_lab6.domain.Message;
import com.db_lab.db_lab6.security.service.SecurityService;
import com.db_lab.db_lab6.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Tag(name = "Chat Controller", description = "Main controller, makes all operations with chats")
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    private final SecurityService securityService;

    public ChatController(ChatService chatService, SecurityService securityService) {
        this.chatService = chatService;
        this.securityService = securityService;
    }

    @Operation(summary = "get all chats in app")
    @GetMapping
    public ResponseEntity<List<Chat>> getChats(Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            List<Chat> chats = chatService.getChats();
            if (chats.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(chats, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "user can get all his chats in app")
    @GetMapping("/chat_list_of_user/{username}")
    public ResponseEntity<List<Chat>> getChatList(Principal principal, @PathVariable String username) {
        if (username == principal.getName() || securityService.checkIfAdmin(principal.getName())) {
            List<Chat> chats = chatService.getChatList(securityService.getUserIdByLogin(principal.getName()));
            if (chats.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(chats, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    //no security because chat don't have active user
    @Operation(summary = "user can get any chat he has in app")
    @GetMapping("/{id}")
    public ResponseEntity<Chat> getChat(@PathVariable Long id, Principal principal) {
        Chat chat = chatService.getChat(id, principal);
        if (chat != null) {
            return new ResponseEntity<>(chat, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "user can get all chat messages he has in his chats")
    @GetMapping("/messages/{id}")
    public ResponseEntity<List<Message>> getChatMessages(@PathVariable Long id, Principal principal) {
        List<Message> messages = chatService.getChatMessages(id, principal);
        if (messages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(messages, HttpStatus.OK);
        }
    }
    @Operation(summary = "user can get chat message he has in his chats")
    @GetMapping("/{chatId}/find_message/{messageId}")
    public ResponseEntity<Message> findMessage(@PathVariable Long chatId, @PathVariable Long messageId, Principal principal) {
        Message message = chatService.findMessage(chatId, messageId, principal);
        if (message != null) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "users can create their own chats")
    @PostMapping
    public ResponseEntity<HttpStatus> createChat(@RequestBody Chat chat) {
        chatService.createChat(chat);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "admins can update their own chats")
    @PutMapping
    public ResponseEntity<HttpStatus> updateChat(@RequestBody Chat chat, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            chatService.updateChat(chat);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "users can update name of their own chats")
    @PutMapping("/update_name/{id}")
    public ResponseEntity<HttpStatus> updateChatName(@PathVariable Long id, @RequestParam String newChatName, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            chatService.updateChatName(id, newChatName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "users can update description of their own chats")
    @PutMapping("/update_description/{id}")
    public ResponseEntity<HttpStatus> updateChatDescription(@PathVariable Long id, @RequestParam String newChatDescription, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            chatService.updateChatDescription(id, newChatDescription);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "admins can delete their own chats")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteChat(@PathVariable Long id, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            chatService.deleteChatById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "admins can clear all chat history")
    @DeleteMapping("/clear_messages/{chatId}")
    public ResponseEntity<HttpStatus> clearAllMessages(@PathVariable Long chatId, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            chatService.clearAllMessages(chatId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "admins can clear any message from their own chats")
    @DeleteMapping("/{chatId}/delete_message/{messageId}")
    public ResponseEntity<HttpStatus> deleteMessage(@PathVariable Long chatId, @PathVariable Long messageId, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            chatService.deleteMessage(chatId, messageId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "admins can delete users from their own chats")
    @DeleteMapping("/{chatId}/delete_user/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long chatId, @PathVariable Long userId, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            chatService.deleteUser(userId, chatId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Operation(summary = "users can leave chat")
    @DeleteMapping("/{chatId}/leave_chat/{username}")
    public ResponseEntity<HttpStatus> leaveChat(@PathVariable Long chatId, @PathVariable String username, Principal principal) {
        deleteUser(chatId, securityService.getUserIdByLogin(username), principal);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}