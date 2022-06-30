package academy.softserve.os.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 20)
    @Size(max = 20)
    private String login;

    @Column(name = "password_hash")
    private String passwordHash;

    @OneToOne(mappedBy = "user")
    private Worker worker;
}
