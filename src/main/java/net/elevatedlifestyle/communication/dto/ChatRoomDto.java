package net.elevatedlifestyle.communication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatRoomDto {
    private Long id;
    private String name;
    private String desc;
    private String timeStamp;
}
