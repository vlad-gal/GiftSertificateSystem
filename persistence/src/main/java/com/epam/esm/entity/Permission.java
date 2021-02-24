package com.epam.esm.entity;

import com.epam.esm.util.ColumnName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "roles")
@Table(name = ColumnName.PERMISSION_TABLE)
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long permissionId;
    private String permissionName;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = ColumnName.PERMISSIONS_HAS_ROLES_TABLE,
            joinColumns = @JoinColumn(name = ColumnName.PERMISSION_ID),
            inverseJoinColumns = @JoinColumn(name = ColumnName.ROLE_ID))
    private List<Role> roles;
}