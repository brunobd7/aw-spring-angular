package com.algaworks.algamoney.api.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
public class Andress {

    @Size(max=50)
    private String publicPlace;

    @Size(max=10)
    private String number;

    @Size(max=50)
    private String complement;

    @Size(max=50)
    private String district;

    @Size(max=8)
    private String cep;

    @Size(max=50)
    private String city;

    @Size(max=50)
    private String state;

    //GETS AND SETTERS

    public String getPublicPlace() {
        return publicPlace;
    }

    public void setPublicPlace(String publicPlace) {
        this.publicPlace = publicPlace;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
