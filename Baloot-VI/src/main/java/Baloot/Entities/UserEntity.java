package Baloot.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class UserEntity {
    public UserEntity() {

    }

    public UserEntity(String username, String email, String password, String address, String birthDate, float credit) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.birthDate = birthDate;
        this.credit = credit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public DiscountCodeEntity getCurrentDiscountCode() {
        return currentDiscountCode;
    }

    public void setCurrentDiscountCode(DiscountCodeEntity currentDiscountCode) {
        this.currentDiscountCode = currentDiscountCode;
    }


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    int id;

    @Column(name = "username")
    String username;

    @Column(name = "email")
    String email;

    @Column(name = "password")
    String password;

    @Column(name = "address")
    String address;

    @Column(name = "birth_date")
    String birthDate;

    @Column(name = "credit")
    float credit;

    @ManyToOne(targetEntity = DiscountCodeEntity.class)
    @JoinColumn(name="current_discount_code_id")
    DiscountCodeEntity currentDiscountCode;

}
