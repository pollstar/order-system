package academy.softserve.os.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Client {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50)
    @Size(max = 50, min = 2)
    private String name;

    @OneToMany(mappedBy = "client")
    private List<Equipment> equipments;

    @OneToMany(mappedBy = "client")
    private List<Order> orders;
}
