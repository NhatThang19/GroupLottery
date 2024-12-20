package com._chaumedia.GroupLottery.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Họ không được để trống")
    @Size(min = 1, max = 50, message = "Họ phải có độ dài từ {min} đến {max} ký tự")
    private String surname;

    @NotNull(message = "Tên không được để trống")
    @Size(min = 1, max = 50, message = "Tên phải có độ dài từ {min} đến {max} ký tự")
    private String name;

    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @Email(message = "Email không hợp lệ", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @CreatedDate
    private LocalDateTime create_at;

    @LastModifiedDate
    private LocalDateTime update_at;

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    private LocalDate date_of_birth;

    @NotNull(message = "Giới tính không được để trống")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "Trạng thái không được để trống")
    @Enumerated(EnumType.STRING)
    private Status status;

    private String avatar;

    private LocalDateTime last_login;

    @Size(min = 6, message = "Mật khẩu phải có ít nhất {min} ký tự")
    private String password;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    @JsonIgnoreProperties("users")
    @ToString.Exclude
    @NotNull(message = "Vai trò không được để trống")
    private Role role;

    public enum Gender {
        male, female;
    }

    public enum Status {
        active, baned
    }

}
