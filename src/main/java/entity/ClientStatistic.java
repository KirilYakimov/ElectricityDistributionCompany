package entity;

import javax.persistence.*;

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
    private double highestPricePaid;

    @Column(name = "total_price_paid")
    private double totalPricePaid;

    public ClientStatistic() { }

    public ClientStatistic(Client client) {
        this.client = client;
    }

    public ClientStatistic(Client client, double highestPricePaid, double totalPricePaid) {
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

    public double getHighestPricePaid() {
        return highestPricePaid;
    }

    public void setHighestPricePaid(double highestPricePaid) {
        this.highestPricePaid = highestPricePaid;
    }

    public double getTotalPricePaid() {
        return totalPricePaid;
    }

    public void setTotalPricePaid(double totalPricePaid) {
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
