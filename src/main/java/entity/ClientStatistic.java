package entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "client_statistic")
public class ClientStatistic {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "client_id",
            unique = true, nullable = false)
    @MapsId
    private Client client;

    @Column(name = "highest_price_paid")
    private BigDecimal highestPricePaid;

    @Column(name = "total_price_paid")
    private BigDecimal totalPricePaid;

    public ClientStatistic() { }

    public ClientStatistic(Client client) {
        this.client = client;
    }

    public ClientStatistic(Client client, BigDecimal highestPricePaid, BigDecimal totalPricePaid) {
        this.client = client;
        this.highestPricePaid = highestPricePaid;
        this.totalPricePaid = totalPricePaid;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BigDecimal getHighestPricePaid() {
        return highestPricePaid;
    }

    public void setHighestPricePaid(BigDecimal highestPricePaid) {
        this.highestPricePaid = highestPricePaid;
    }

    public BigDecimal getTotalPricePaid() {
        return totalPricePaid;
    }

    public void setTotalPricePaid(BigDecimal totalPricePaid) {
        this.totalPricePaid = totalPricePaid;
    }

    @Override
    public String toString() {
        return "ClientStatistic{" +
                "id=" + id +
                ", client_id=" + client +
                ", highest_price_paid=" + highestPricePaid +
                ", total_price_paid=" + totalPricePaid +
                '}';
    }
}
