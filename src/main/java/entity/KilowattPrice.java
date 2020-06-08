package entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "kilowatt_price")
public class KilowattPrice {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "changed_date")
    private LocalDate changedOnDate;

    @Column(name = "price")
    private BigDecimal price;

    public KilowattPrice() {}

    public KilowattPrice(LocalDate changedOnDate, BigDecimal price) {
        this.changedOnDate = changedOnDate;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getChangedOnDate() {
        return changedOnDate;
    }

    public void setChangedOnDate(LocalDate changedOnDate) {
        this.changedOnDate = changedOnDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "KilowattPrice{" +
                "id=" + id +
                ", changedOnDate=" + changedOnDate +
                ", price=" + price +
                '}';
    }
}
