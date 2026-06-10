package voxera.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class message {

    private String content;
    private String sender;
    private int senderId;
    private int id;
    private int timestamp;
}