package net.elevatedlifestyle.communication.service;

import net.elevatedlifestyle.communication.dto.ChatRoomDto;
import net.elevatedlifestyle.communication.model.ChatMessage;
import net.elevatedlifestyle.communication.model.ChatRoom;
import net.elevatedlifestyle.communication.model.User;

import java.util.List;

public interface ChatRoomService {

    ChatRoom createChatRoom(String name, String desc);
    void addUserToChatRoom(Long roomId, Long id);
    void removeUserFromChatRoom(Long roomId, Long id);
    void addMessageToChatRoom(Long roomId, Long id, ChatMessage chatMessage);
    List<ChatRoomDto> getAllChatRooms();
    ChatRoom getChatRoomById(Long id);
    List<ChatRoomDto> getAllChatRoomsForUser(Long id);
}
