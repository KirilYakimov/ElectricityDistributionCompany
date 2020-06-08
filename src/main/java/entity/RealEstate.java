package entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "real_estate")
public class RealEstate {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "head_branch_phone", nullable = false)
    private int phone;

    @Column(name = "head_branch_email", nullable = false)
    private String email;

    @Column(name = "maintenance", nullable = false)
    private BigDecimal maintenance;

    public RealEstate() {
    }

    public RealEstate(String address, int phone, String email, BigDecimal maintenance) {
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.maintenance = maintenance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(BigDecimal maintenance) {
        this.maintenance = maintenance;
    }

    @Override
    public String toString() {
        return "RealEstate{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", maintenance=" + maintenance +
                '}';
    }
}
