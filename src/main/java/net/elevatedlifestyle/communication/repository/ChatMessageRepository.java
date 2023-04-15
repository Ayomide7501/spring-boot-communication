package net.elevatedlifestyle.communication.repository;

import net.elevatedlifestyle.communication.model.ChatMessage;
import net.elevatedlifestyle.communication.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoomOrderByTimeAsc(ChatRoom chatRoom);
}
