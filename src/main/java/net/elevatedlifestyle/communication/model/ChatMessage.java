package net.elevatedlifestyle.communication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long senderId;
    private String messageType;
    private String content;
    private String time;
    @CreationTimestamp
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ChatRoom.class)
    @JoinColumn(name = "chatroom_id")
    @JsonIgnore
    private ChatRoom chatRoom;
}
