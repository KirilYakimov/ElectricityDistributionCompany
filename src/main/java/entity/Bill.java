package entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client_id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "kilowatt_price", nullable = false)
    private BigDecimal kilowatt_price;

    @Column(name = "electricity_consumption", nullable = false)
    private BigDecimal electricity_consumption;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "paid", nullable = false)
    private boolean paid;

    public Bill() {}

    public Bill(Client client_id, LocalDate date, BigDecimal kilowatt_price, BigDecimal electricity_consumption, BigDecimal price, boolean paid) {
        this.client_id = client_id;
        this.date = date;
        this.kilowatt_price = kilowatt_price;
        this.electricity_consumption = electricity_consumption;
        this.price = price;
        this.paid = paid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient_id() {
        return client_id;
    }

    public void setClient_id(Client client_id) {
        this.client_id = client_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getKilowatt_price() {
        return kilowatt_price;
    }

    public void setKilowatt_price(BigDecimal kilowatt_price) {
        this.kilowatt_price = kilowatt_price;
    }

    public BigDecimal getElectricity_consumption() {
        return electricity_consumption;
    }

    public void setElectricity_consumption(BigDecimal electricity_consumption) {
        this.electricity_consumption = electricity_consumption;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "Bills{" +
                "id=" + id +
                ", client_id=" + client_id +
                ", date=" + date +
                ", kilowatt_price=" + kilowatt_price +
                ", electricity_consumption=" + electricity_consumption +
                ", price=" + price +
                ", paid=" + paid +
                '}';
    }
}
