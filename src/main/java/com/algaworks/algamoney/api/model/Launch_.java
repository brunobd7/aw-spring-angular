package com.algaworks.algamoney.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Launch.class)
public abstract class Launch_ {

	public static volatile SingularAttribute<Launch, String> note;
	public static volatile SingularAttribute<Launch, BigDecimal> amount;
	public static volatile SingularAttribute<Launch, Person> person;
	public static volatile SingularAttribute<Launch, LocalDate> dueDate;
	public static volatile SingularAttribute<Launch, String> description;
	public static volatile SingularAttribute<Launch, LocalDate> payDay;
	public static volatile SingularAttribute<Launch, Integer> id;
	public static volatile SingularAttribute<Launch, Category> category;
	public static volatile SingularAttribute<Launch, LaunchType> launchType;

	public static final String NOTE = "note";
	public static final String AMOUNT = "amount";
	public static final String PERSON = "person";
	public static final String DUE_DATE = "dueDate";
	public static final String DESCRIPTION = "description";
	public static final String PAY_DAY = "payDay";
	public static final String ID = "id";
	public static final String CATEGORY = "category";
	public static final String LAUNCH_TYPE = "launchType";

}

