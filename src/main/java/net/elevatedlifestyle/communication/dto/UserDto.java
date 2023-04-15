package net.elevatedlifestyle.communication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.elevatedlifestyle.communication.model.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long Id;
    private String name;
    private String email;
    private String password;
    private String role;
    private Boolean isActive;
    private List<Role> roles;
}
