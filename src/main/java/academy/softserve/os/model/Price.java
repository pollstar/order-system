package academy.softserve.os.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Price {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "client_price")
    private BigDecimal clientPrice;

    @Column(name = "worker_price")
    private BigDecimal workerPrice;

    @Column(name = "date_since")
    private LocalDate dateSince;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;
}
