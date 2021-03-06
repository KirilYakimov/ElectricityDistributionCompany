package entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "address", nullable = false)
    private String address;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    private ClientType type;

    @OneToMany(mappedBy = "client_id", fetch = FetchType.LAZY)
    private Set<Bill> billList;

    @OneToOne(mappedBy = "client", fetch = FetchType.LAZY)
    private ClientStatistic clientStatistics;

    public Client() {
       billList = new HashSet<>();
       clientStatistics = new ClientStatistic();
    }

    public Client(String firstName, String lastName, String email, String address, ClientType type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.type = type;
        this.billList = new HashSet<>();
        this.clientStatistics = new ClientStatistic();
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    public Set<Bill> getBillList() {
        return billList;
    }

    public void setBillList(Set<Bill> billList) {
        this.billList = billList;
    }

    public BigDecimal getTotalPricePaid() {
        return clientStatistics.getTotalPricePaid();
    }

    public BigDecimal getHighestPricePaid() {
        return clientStatistics.getHighestPricePaid();
    }

    public ClientStatistic getClientStatistics() {
        return clientStatistics;
    }

    public void setClientStatistics(ClientStatistic clientStatistics) {
        this.clientStatistics = clientStatistics;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", type=" + type +
                ", billsList=" + billList +
                '}';
    }
}
