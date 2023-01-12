package store.streetvendor.core.domain.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.BaseTimeEntity;

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

    private String nickName;

    public Admin(String email, String nickName) {
        this.email = email;
        this.nickName = nickName;
    }

    public static Admin newAdmin(String email, String nickName) {
        return new Admin(email, nickName);
    }

}
