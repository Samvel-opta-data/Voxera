package voxera.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class UserRegisterRequestDto {

    private String username;
    private String email;
    private String password;
    private String userDescription;
}