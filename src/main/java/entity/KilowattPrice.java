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

    @Column(name = "price")
    private double price;

    public KilowattPrice() {}

    public KilowattPrice(LocalDate changedOnDate, double price) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
