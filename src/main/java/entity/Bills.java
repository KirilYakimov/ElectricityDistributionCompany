package entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bills")
public class Bills {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne (fetch = FetchType.LAZY)
    @Column(name = "kilowatt")
    private KilowattPrice kilowattPrice;

    @Column(name = "electricity_consumption", nullable = false)
    private double electricity_consumption;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "paid", nullable = false)
    private boolean paid;



}
