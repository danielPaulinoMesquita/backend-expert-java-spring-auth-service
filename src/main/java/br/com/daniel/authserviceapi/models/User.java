package br.com.daniel.authserviceapi.models;

import br.com.userservice.commonslib.model.enums.ProfileEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Getter
@AllArgsConstructor
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private Set<ProfileEnum> profiles;
}
