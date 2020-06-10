package learn.rr.microservice.orderms.model;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Table
public class Order {

    @PrimaryKey
    private UUID id;

    private LocalDate date;

    @Column("customer_name")
    private String customerName;

    @Column("customer_email")
    private String customerEmail;

    @Column("customer_address")
    private String customerAddress;

    @Column("items")
    private Set<Item> items;
    private double total;

    public Order() {
    }

    public Order(UUID id, LocalDate date, String customerName, String customerEmail, String customerAddress, Set<Item> items, double total) {
        this.id = id;
        this.date = date;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
        this.items = items;
        this.total = total;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", customerName='" + customerName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", items=" + items +
                ", total=" + total +
                '}';
    }
}
