package com._chaumedia.GroupLottery.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String surname;

    private String name;

    private String phone;

    private String email;

    @CreatedDate
    private LocalDateTime create_at;

    @LastModifiedDate
    private LocalDateTime update_at;

    private LocalDate date_of_birth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)

    private Status status;

    private String avatar;

    private LocalDateTime last_login;

    private String password;

    private String address;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    @JsonIgnoreProperties("users") 
    @ToString.Exclude
    private Role role;
    
    public enum Gender {
        male, female;
    }

    public enum Status {
        active, baned
    }  
    
}
