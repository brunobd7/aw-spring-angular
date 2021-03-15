package com.algaworks.algamoney.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "launch")
public class Launch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pay_day")
    @JsonFormat(pattern = "dd/MM/yyyy") //FORMATANDO DADOS DO PACTO java.time NO JSON  PARA O PATTERN DESEJADO
    private LocalDate payDay;

    @Column(name = "due_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dueDate;

    private BigDecimal amount;
    private String note;

    @Column(name = "launch_type")
    @Enumerated(EnumType.STRING) // SALVA STRING DO ENUM
    private LaunchType launchType;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "id_person")
    private Person person;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setAmount(BigDecimal value) {
        this.amount = value;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Launch launch = (Launch) o;
        return id.equals(launch.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
