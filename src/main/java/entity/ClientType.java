package entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "client_type")
public class ClientType {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "client_type", fetch = FetchType.LAZY)
    private Set<Client> clients;

    public ClientType(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    @Override
    public String toString() {
        return "ClientType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", clients=" + clients +
                '}';
    }
}
