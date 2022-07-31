package store.streetvendor.domain.domain.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.BaseTimeEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Admin extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static Admin newAdmin(String email, String password) {
        return new Admin(email, password);
    }

}
