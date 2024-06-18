package ru.kata.spring.boot_security.demo.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int id;

    @Column(name = "role_name")
    private String name;

    @ManyToMany(mappedBy = "all_roles", cascade = CascadeType.ALL)
    private List<User> all_users;

    public Role() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<User> getAll_users() {
        return all_users;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAll_users(List<User> all_users) {
        this.all_users = all_users;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(name, role.name) && Objects.equals(all_users, role.all_users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, all_users);
    }
}
