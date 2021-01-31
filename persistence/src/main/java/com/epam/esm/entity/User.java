package com.epam.esm.entity;

import com.epam.esm.dao.ColumnName;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = ColumnName.USER_TABLE)
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String login;
    private String firstName;
    private String lastName;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "user")
    private List<Order> orders;
}