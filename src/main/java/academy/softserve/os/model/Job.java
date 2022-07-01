package academy.softserve.os.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Job {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "job")
    private List<Price> prices;

}
