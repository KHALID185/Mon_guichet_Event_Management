package org.usermicroservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.usermicroservice.enums.Active;
import org.usermicroservice.enums.ERole;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String firstname;
    private String lastname;
    private String email;
    private String numberPhone;
    private String password;
    @CreationTimestamp
    private LocalDateTime creationTimestamp;
    @UpdateTimestamp
    private LocalDateTime updateTimestamp;
    @Enumerated(EnumType.STRING)
    private Active isActive;
    private boolean isEnabled;
    private Date verifiedAt;
    private String confirmationToken;
    private String resetPasswordToken;
    private Date resetPasswordTokenExpiryDate;
    @ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    private Collection<Role> roles = new ArrayList<>();
}
