package com.example.login.registration.token;

import com.example.login.appuser.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createAt;
    @Column(nullable = false)
    private LocalDateTime expireAt;
    @Column(nullable = true)
    private LocalDateTime confirmAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;

    public ConfirmationToken(String token,
                             LocalDateTime createAt,
                             LocalDateTime expireAt,
                             AppUser appUser) {
        this.token = token;
        this.createAt = createAt;
        this.expireAt = expireAt;
        this.appUser = appUser;
    }
}
