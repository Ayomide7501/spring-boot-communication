package net.elevatedlifestyle.communication.service.Impl;

import net.elevatedlifestyle.communication.dto.ChatRoomDto;
import net.elevatedlifestyle.communication.model.ChatMessage;
import net.elevatedlifestyle.communication.model.ChatRoom;
import net.elevatedlifestyle.communication.model.User;
import net.elevatedlifestyle.communication.repository.ChatRoomRepository;
import net.elevatedlifestyle.communication.repository.UserRepository;
import net.elevatedlifestyle.communication.service.ChatRoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {
    private ChatRoomRepository chatRoomRepository;
    private UserRepository userRepository;

    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository, UserRepository userRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ChatRoom createChatRoom(String name, String desc) {
        ChatRoom newRoom = new ChatRoom();
        newRoom.setName(name);
        newRoom.setDescription(desc);
        return chatRoomRepository.save(newRoom);
    }

    @Override
    public void addUserToChatRoom(Long roomId, Long id) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElse(null);
        User user = userRepository.findById(id).orElse(null);
        if (chatRoom != null && user != null && user.getIsActive()){
            chatRoom.addUser(user);
            chatRoomRepository.save(chatRoom);
        }
    }

    @Override
    public void removeUserFromChatRoom(Long roomId, Long id) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElse(null);
        User user = userRepository.findById(id).orElse(null);
        if (chatRoom !=null && user != null && user.getIsActive()){
            chatRoom.removeUser(user);
            chatRoomRepository.save(chatRoom);
        }
    }
    @Override
    public void addMessageToChatRoom(Long roomId, Long id, ChatMessage chatMessage) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElse(null);
        User user = userRepository.findById(id).orElse(null);
        if (chatRoom !=null && user != null && user.getIsActive()){
            chatRoom.addMessage(chatMessage);
            chatRoomRepository.save(chatRoom);
        }
    }
    @Override
    public List<ChatRoomDto> getAllChatRooms() {
        List<ChatRoom> chatRoomList = chatRoomRepository.findAll();
        return chatRoomList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    @Override
    public ChatRoom getChatRoomById(Long id) {
        return chatRoomRepository.findById(id).orElse(null);
    }
    @Override
    public List<ChatRoomDto> getAllChatRoomsForUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByUsersContaining(user);
        return chatRooms.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ChatRoomDto mapToDto(ChatRoom chatRoom){
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        chatRoomDto.setId(chatRoom.getId());
        chatRoomDto.setName(chatRoom.getName());
        chatRoomDto.setDesc(chatRoom.getDescription());
        chatRoomDto.setTimeStamp(chatRoom.getCreatedTime().toString());
        return chatRoomDto;
    }
}
