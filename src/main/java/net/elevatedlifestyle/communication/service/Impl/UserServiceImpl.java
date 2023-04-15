package net.elevatedlifestyle.communication.service.Impl;

import net.elevatedlifestyle.communication.dto.UserDto;
import net.elevatedlifestyle.communication.model.Role;
import net.elevatedlifestyle.communication.model.User;
import net.elevatedlifestyle.communication.repository.RoleRepository;
import net.elevatedlifestyle.communication.repository.UserRepository;
import net.elevatedlifestyle.communication.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode("password1"));
        //addRole();
        Role role = roleRepository.findByName(userDto.getRole());
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
            User user1 = new User();
            user1.setName(user.get().getName());
            user1.setEmail(user.get().getEmail());
            user1.setId(user.get().getId());
            user1.setRoles(user.get().getRoles());
            user1.setIsActive(user.get().getIsActive());
            user1.setPassword(user.get().getPassword());
            user1.setDateCreated(user.get().getDateCreated());
            user1.setResetPasswordToken(user.get().getResetPasswordToken());
            user1.setChatRooms(user.get().getChatRooms());
        return user1;
    }


    private void addRole(){
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleRepository.save(userRole);

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleRepository.save(adminRole);

        Role chefRole = new Role();
        chefRole.setName("ROLE_CHEF");
        roleRepository.save(chefRole);

        Role staffRole = new Role();
        staffRole.setName("ROLE_STAFF");
        roleRepository.save(staffRole);
    }

    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setIsActive(user.getIsActive());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
