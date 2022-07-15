package academy.softserve.os.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_price")
    private BigDecimal clientPrice;

    @Column(name = "worker_price")
    private BigDecimal workerPrice;

    @Column(name = "date_since")
    private LocalDate dateSince;

    @ManyToOne
    @JoinColumn(name = "job_id")
    @JsonIgnore
    private Job job;
}
