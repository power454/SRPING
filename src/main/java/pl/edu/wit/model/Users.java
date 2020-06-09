package pl.edu.wit.model;


import sun.security.util.Password;

import javax.persistence.*;


@Entity
@Table(name="users")
public class Users {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String name;

    @Column
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @Column
    private String password;


}
