package net.elevatedlifestyle.communication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "fullName")
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Boolean isActive = true;
    @CreationTimestamp
    private LocalDateTime dateCreated;
    private String resetPasswordToken;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn (name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn (name = "role_id", referencedColumnName = "id")}
    )
    //private Set<Role> roles = new HashSet<>();
    private List<Role> roles = new ArrayList<>();

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private Set<ChatRoom> chatRooms = new HashSet<>();

}
