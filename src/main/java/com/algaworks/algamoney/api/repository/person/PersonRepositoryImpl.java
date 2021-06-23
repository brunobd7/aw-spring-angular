package com.algaworks.algamoney.api.repository.person;

import com.algaworks.algamoney.api.model.Launch;
import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.model.Person_;
import com.algaworks.algamoney.api.repository.filter.PersonFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PersonRepositoryImpl implements PersonRepositoryQuery{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<Person> filter(PersonFilter personFilter, Pageable pageable) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Person.class);

        Root<Person> rootPerson = criteriaQuery.from(Person.class);

        Predicate[] predicates = createRetrictsFilter(personFilter, criteriaBuilder, rootPerson);
        criteriaQuery.where(predicates);

        TypedQuery<Person> query = entityManager.createQuery(criteriaQuery);

        createPaginationRestricts(query, pageable);


        return new PageImpl<>(query.getResultList(),pageable, total(personFilter));
    }

    private Long total(PersonFilter personFilter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        Root<Person> root = criteriaQuery.from(Person.class); //SQL - FROM LAUNCH

        Predicate[] predicates = createRetrictsFilter(personFilter, criteriaBuilder, root); // SQL - WHERE
        criteriaQuery.where(predicates);

        criteriaQuery.select(criteriaBuilder.count(root)); //COUNT *
        return entityManager.createQuery(criteriaQuery).getSingleResult(); // retorna contagem total
    }

    private void createPaginationRestricts(TypedQuery<Person> query, Pageable pageable) {
         Integer currentPage = pageable.getPageNumber();
         Integer maxPageSize = pageable.getPageSize();
         Integer firstRowOfPage = currentPage * maxPageSize;

         //SETA PRIMEIRO RESULTA A SER EXIBIDO
         query.setFirstResult(firstRowOfPage);
         query.setMaxResults(maxPageSize);

    }

    private Predicate[] createRetrictsFilter(PersonFilter personFilter, CriteriaBuilder criteriaBuilder, Root<Person> rootPerson) {

        List<Predicate> predicateList = new ArrayList<>();

        if(!StringUtils.isEmpty(personFilter.getName())){

            /**GERANDO 'QUERY' de filtragem (WHERE person.name like '%parametroFiltro%')*/
            predicateList.add(

                    criteriaBuilder.like( criteriaBuilder.lower(rootPerson.get(Person_.name)), "%"+personFilter.getName()+"%" )

            );

        }


        return predicateList.toArray(new Predicate[predicateList.size()]);
    }
}
