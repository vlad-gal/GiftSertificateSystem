package com.epam.esm.entity;

import com.epam.esm.util.ColumnName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = ColumnName.ROLE_TABLE)
@Data
@EqualsAndHashCode(exclude = "users")
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;
    private String roleName;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = ColumnName.PERMISSIONS_HAS_ROLES_TABLE,
            joinColumns = @JoinColumn(name = ColumnName.ROLE_ID),
            inverseJoinColumns = @JoinColumn(name = ColumnName.PERMISSION_ID))
    private List<Permission> permissions;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            mappedBy = "role")
    private List<User> users;
}