package com.algaworks.algamoney.api.repository.projection;

import com.algaworks.algamoney.api.model.LaunchType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LaunchResume {

    private Integer id;
    private String description;
    private LocalDate payDay;
    private LocalDate dueDate;
    private BigDecimal amount;
    private String note;
    private LaunchType launchType;
    private String category;
    private String person;


    public LaunchResume(Integer id, String description, LocalDate payDay, LocalDate dueDate, BigDecimal amount,
                        String note, LaunchType launchType, String category, String person) {
        this.id = id;
        this.description = description;
        this.payDay = payDay;
        this.dueDate = dueDate;
        this.amount = amount;
        this.note = note;
        this.launchType = launchType;
        this.category = category;
        this.person = person;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPayDay() {
        return payDay;
    }

    public void setPayDay(LocalDate payDay) {
        this.payDay = payDay;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LaunchType getLaunchType() {
        return launchType;
    }

    public void setLaunchType(LaunchType launchType) {
        this.launchType = launchType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

}
