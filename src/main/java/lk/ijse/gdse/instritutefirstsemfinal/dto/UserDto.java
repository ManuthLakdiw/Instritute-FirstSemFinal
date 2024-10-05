package lk.ijse.gdse.instritutefirstsemfinal.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UserDto {
    private String usName;
    private String usPassword;
    private String usEmail;
    private String usPhone;

    public UserDto(String usName, String usPassword){
        this.usName = usName;
        this.usPassword = usPassword;
    }
}
