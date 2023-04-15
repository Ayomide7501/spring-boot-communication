package net.elevatedlifestyle.communication.controller;

import jakarta.servlet.http.HttpServletRequest;
import net.elevatedlifestyle.communication.dto.ChatRoomDto;
import net.elevatedlifestyle.communication.model.ChatMessage;
import net.elevatedlifestyle.communication.model.ChatRoom;
import net.elevatedlifestyle.communication.model.MessageType;
import net.elevatedlifestyle.communication.model.User;
import net.elevatedlifestyle.communication.repository.ChatRoomRepository;
import net.elevatedlifestyle.communication.repository.UserRepository;
import net.elevatedlifestyle.communication.service.ChatMessageService;
import net.elevatedlifestyle.communication.service.ChatRoomService;
import net.elevatedlifestyle.communication.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ChatController {
    private ChatRoomRepository chatRoomRepository;
    private ChatRoomService chatRoomService;
    private SimpMessagingTemplate simpMessagingTemplate;
    private UserService userService;
    private UserRepository userRepository;
    private ChatMessageService chatMessageService;

    public ChatController(ChatRoomRepository chatRoomRepository, ChatRoomService chatRoomService, SimpMessagingTemplate simpMessagingTemplate, UserService userService, UserRepository userRepository, ChatMessageService chatMessageService) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatRoomService = chatRoomService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userService = userService;
        this.userRepository = userRepository;
        this.chatMessageService = chatMessageService;
    }

    @PostMapping("/createroom")
    public String createRoom(@RequestParam("name") String roomName,@RequestParam("desc") String roomDesc){
        ChatRoom chatRoom = chatRoomService.createChatRoom(roomName, roomDesc);
        chatRoomService.addUserToChatRoom(chatRoom.getId(), Long.valueOf("1"));
        return "/admin/admin";
    }

    @GetMapping("/chats")
    public String chatList(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());
        List<ChatRoomDto> chatRoomList = chatRoomService.getAllChatRoomsForUser(user.getId());
        model.addAttribute("rooms", chatRoomList);
        return "chat/chatList";
    }

    @GetMapping("/chatroom/{id}")
    public String chatRoom(@PathVariable("id") Long id, Model model){
        List<ChatMessage> allMessages = chatMessageService.getChatMessagesForChatRoom(id);
        model.addAttribute("messages", allMessages);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        Long userId = user.getId();
        model.addAttribute("userid", userId);
        return "chat/chat";
    }

    @GetMapping("/chatroom/getAllMessages")
    public ResponseEntity<List<ChatMessage>> getMessagesForRoom(@RequestParam("roomId") Long id){
        List<ChatMessage> messages = chatMessageService.getChatMessagesForChatRoom(id);
        return ResponseEntity.ok(messages);
    }
    @PostMapping("/chat/sendmessage")
    public ResponseEntity<String> sendMessage(
                            @RequestParam("roomId") String roomId,
                            @RequestParam("type") String type,
                            @RequestParam("content") String content,
                            @RequestParam("id") String id){
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = currentTime.format(formatter);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSenderId(Long.valueOf(id));
        chatMessage.setContent(content);
        chatMessage.setTime(formattedTime);
        switch (type) {
            case "text" -> chatMessage.setMessageType("text");
            case "image" -> chatMessage.setMessageType("image");
            case "audio" -> chatMessage.setMessageType("audio");
            case "file" -> chatMessage.setMessageType("file");
            case "emoji" -> chatMessage.setMessageType("emoji");
            default -> {
            }
        }
        //simpMessagingTemplate.convertAndSend("/topic/" + roomId, chatMessage);
        ChatRoom chatRoom = chatRoomRepository.findById(Long.valueOf(roomId)).orElseThrow(() -> new RuntimeException("chat room not found"));
        chatMessage.setChatRoom(chatRoom);
        chatRoom.getMessages().add(chatMessage);
        chatRoomRepository.save(chatRoom);
        return ResponseEntity.ok("Message saved");
    }
    @MessageMapping("/chat/{roomId}/getMessages")
    public void getMessages(@PathVariable Long roomId){
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("chat room not found"));
        simpMessagingTemplate.convertAndSend("/topic/" + roomId, chatRoom.getMessages());
    }

    // Define the message-mapping destination and the destination for the response
    @MessageMapping("/chat/{roomId}/send")
    //@SendTo("/topic/chat/{roomId}")
    public ChatMessage sendChatMessage(@DestinationVariable("roomId") String roomId,
                                       ChatMessage chatMessage) {
        // Send the message to the destination
        // Return the message to be sent back to the client
        simpMessagingTemplate.convertAndSend("/topic/chat/" + roomId, chatMessage);
        return chatMessage;
    }
}
