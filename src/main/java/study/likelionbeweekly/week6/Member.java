package study.likelionbeweekly.week6;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Member extends BaseEntity {

    @Column(length = 15, nullable = false)
    private String name;

    @Column(length = 63, nullable = false, unique = true)
    private String email;

    @Column(length = 1023, nullable = false)
    private String password;
}
