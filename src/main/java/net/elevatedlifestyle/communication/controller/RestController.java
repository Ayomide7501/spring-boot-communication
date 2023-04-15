package net.elevatedlifestyle.communication.controller;

import net.elevatedlifestyle.communication.dto.UserDto;
import net.elevatedlifestyle.communication.repository.ChatRoomRepository;
import net.elevatedlifestyle.communication.service.ChatRoomService;
import net.elevatedlifestyle.communication.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private ChatRoomRepository chatRoomRepository;
    private ChatRoomService chatRoomService;
    private UserService userService;

    public RestController(ChatRoomRepository chatRoomRepository, ChatRoomService chatRoomService, UserService userService) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatRoomService = chatRoomService;
        this.userService = userService;
    }
    @GetMapping("/getAll")
    private ResponseEntity<List<UserDto>> getAll(){
        List<UserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/getAllUsers")
    private ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/addtoroom")
    public ResponseEntity<String> getChatRoomContaining(){
        //User user = userService.findById(Long.valueOf("1"));
        //System.out.println("my user is: " + user);
        chatRoomService.addUserToChatRoom(Long.valueOf("1"), Long.valueOf("1"));
        return ResponseEntity.ok("Added");
    }
    @PostMapping("/reg")
    public ResponseEntity<String> addNewAdmin(){
        UserDto userDto = new UserDto();
        userDto.setName("Marie");
        userDto.setEmail("marie@admin.com");
        userDto.setRole("ROLE_ADMIN");
        userDto.setIsActive(true);
        userService.saveUser(userDto);
        return ResponseEntity.ok("Saved");
    }
    public String addFileToS3(){
        return "added";
    }
}
