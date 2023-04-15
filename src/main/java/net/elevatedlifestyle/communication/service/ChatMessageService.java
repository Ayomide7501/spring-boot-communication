package net.elevatedlifestyle.communication.service;

import net.elevatedlifestyle.communication.model.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    List<ChatMessage> getChatMessagesForChatRoom(Long roomId);
}
