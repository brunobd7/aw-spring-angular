package com.algaworks.algamoney.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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


    @JsonIgnore //IGNORA VERIFICACAO JACKSON E NAO INTERPRETA COMO PROPERTIE NO JSON
    @Transient // HIBERNATE INGNORA COMO ATRIBUTO
    public Boolean isInactive(){
        return !this.active;
    }

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
