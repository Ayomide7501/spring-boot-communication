package net.elevatedlifestyle.communication.repository;

import net.elevatedlifestyle.communication.model.ChatMessage;
import net.elevatedlifestyle.communication.model.ChatRoom;
import net.elevatedlifestyle.communication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findById(Long id);
    Optional<ChatRoom> findByName(String name);
    // Find all chat rooms containing the specified user
    List<ChatRoom> findAllByUsersContaining(User user);

    // Find all chat rooms containing the specified staff user
    //List<ChatRoom> findAllByUsersInAndUsers_Role(Set<User> users, String role);

}
