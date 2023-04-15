package net.elevatedlifestyle.communication.service.Impl;

import net.elevatedlifestyle.communication.model.ChatMessage;
import net.elevatedlifestyle.communication.model.ChatRoom;
import net.elevatedlifestyle.communication.repository.ChatMessageRepository;
import net.elevatedlifestyle.communication.repository.ChatRoomRepository;
import net.elevatedlifestyle.communication.service.ChatMessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    private ChatRoomRepository chatRoomRepository;
    private ChatMessageRepository chatMessageRepository;

    public ChatMessageServiceImpl(ChatRoomRepository chatRoomRepository, ChatMessageRepository chatMessageRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public List<ChatMessage> getChatMessagesForChatRoom(Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow();
        List<ChatMessage> messages = chatMessageRepository.findByChatRoomOrderByTimeAsc(chatRoom);
        return  messages;
    }
}
