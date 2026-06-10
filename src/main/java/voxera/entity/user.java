package voxera.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class user {

    private String username;
    private String password;
    private String email;
    private String userDescription;
    private int id;
}
