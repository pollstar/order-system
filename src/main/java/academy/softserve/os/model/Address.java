package academy.softserve.os.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50)
    private String city;

    @Column(length = 50)
    private String street;

    @Column(length = 5)
    private String house;

    @Column(length = 50)
    private String room;

    @OneToMany(mappedBy = "address")
    private List<Equipment> equipments;
}
