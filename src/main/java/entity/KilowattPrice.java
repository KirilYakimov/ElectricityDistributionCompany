package entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "kilowatt_price")
public class KilowattPrice {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "changed_date")
    private LocalDate changedOnDate;

    @OneToMany(mappedBy = "kilowatt_price", fetch = FetchType.LAZY)
    private Set<Bills> billsList;

}
