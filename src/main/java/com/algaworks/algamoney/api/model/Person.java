package com.algaworks.algamoney.api.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @NotNull
    @Size(min=3,max=50)
    private String name;

    @NotNull
    private Boolean active;

    @Embedded
    private Andress andress;

    //GETS AND SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Andress getAndress() {
        return andress;
    }

    public void setAndress(Andress andress) {
        this.andress = andress;
    }
}
