package study.likelionbeweekly.week7.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import study.likelionbeweekly.week7.BaseEntity;

@Getter
@Setter
@Entity
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Column(length = 16, nullable = false)
    private String name;

    @Column(length = 64, nullable = false, unique = true)
    private String email;

    @Column(length = 1024, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
