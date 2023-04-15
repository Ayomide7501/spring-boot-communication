package net.elevatedlifestyle.communication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chat_room")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable = false)
    private String description;
    @CreationTimestamp
    private LocalDateTime createdTime;

    @ManyToMany
    @JoinTable(name = "chat_room_user",
                joinColumns = {@JoinColumn(name = "chat_room_id", referencedColumnName = "id")},
                inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private Set<User> users = new HashSet<>();
    public void addUser(User user) {
        this.users.add(user);
        user.getChatRooms().add(this);
    }
    public void removeUser(User user) {
        this.users.remove(user);
        user.getChatRooms().remove(this);
    }
    public void addMessage(ChatMessage message) {
        this.messages.add(message);
        message.setChatRoom(this);
    }
    @OneToMany(cascade = CascadeType.ALL, targetEntity = ChatMessage.class)
    private List<ChatMessage> messages = new ArrayList<>();
}
