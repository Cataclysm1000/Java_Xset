package lesson12.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String birthDay;
    private Integer departmentId;
}
