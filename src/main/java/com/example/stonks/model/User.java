package com.example.stonks.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

// Open postgres console: psql -U user -d stonksdb -W

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    ///@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //private Set<Cost> costs;

    @ManyToMany()
    @JoinTable(name = "users_roles",
                joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();
    //private Set<Role> roles;

    // @ElementCollection(targetClass = Role.class, fetch = FetchType.LAZY)
    // @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    // @Enumerated(EnumType.STRING)
    // private Set<Role> roles;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // public Set<Cost> getCosts() {
    //     return costs;
    // }

    // public void setCosts(Set<Cost> costs) {
    //     this.costs = costs;
    // }

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
}
