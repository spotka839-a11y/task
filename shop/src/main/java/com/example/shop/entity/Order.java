package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.info.SslInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"order\"")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private SslInfo.CertificateValidityInfo.Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pickuppoint_id", nullable = false)
    private PickupPoint pickupPoint;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    private User user;

    @Column(name = "get_code", nullable = false)
    private Integer getCode;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> items = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SslInfo.CertificateValidityInfo.Status getStatus() {
        return status;
    }



    public PickupPoint getPickupPoint() {
        return pickupPoint;
    }

    public void setPickupPoint(PickupPoint pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getGetCode() {
        return getCode;
    }

    public void setGetCode(Integer getCode) {
        this.getCode = getCode;
    }

    public List<OrderProduct> getItems() {
        return items;
    }

    public void setItems(List<OrderProduct> items) {
        this.items = items;
    }

    public Status setStatus(Status newStatus) {
        return newStatus;
    }

    public void setStatus(SslInfo.CertificateValidityInfo.Status status) {
        this.status = status;
    }
}