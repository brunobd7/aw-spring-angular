package com.algaworks.algamoney.api.repository.launch;

import com.algaworks.algamoney.api.model.Category_;
import com.algaworks.algamoney.api.model.Launch;
import com.algaworks.algamoney.api.model.Launch_;
import com.algaworks.algamoney.api.model.Person_;
import com.algaworks.algamoney.api.repository.filter.LaunchFilter;
import com.algaworks.algamoney.api.repository.projection.LaunchResume;
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

//IMPLEMENTANDO INTERFACE PARA QUERYS PERSONALIZADAS DO JPA = CRITERIAS
public class LaunchRepositoryImpl implements LaunchRepositoryQuery {

    @PersistenceContext
    EntityManager entityManager;


    //MONTA UMA QUERY PARA CONSULTA PERSONALIZADA USANDO O JPA CRITERIA
    @Override
    public Page<Launch> filter(LaunchFilter launchFilter, Pageable pageable) {

        //construtor de criterias
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Launch> criteriaQuery = criteriaBuilder.createQuery(Launch.class);

        //PEGA OS ATRIBUTOS DO .CLASS PASSADO COMO PARAMETRO PARA DEFINIR O QUE VAI SER USADO PARA O FILTRO
        Root<Launch> root = criteriaQuery.from(Launch.class);

        //CRIAR RESTRIÇOES / ATRIBUTOS QUE COMPOEM O "WHERE" DE QUERY TRADICIONAL PARA A FILTRAGEM DOS DADOS
        Predicate[] predicates = createRestrictsForFilter(launchFilter , criteriaBuilder , root);
        criteriaQuery.where(predicates);

        //CRIANDO QUERY usando a CRITERIA
        TypedQuery<Launch> query = entityManager.createQuery(criteriaQuery);

        //RETORNANDO RESULTADO DA CRITERIA QUERY
//        return query.getResultList();

        //TRATAMENTO DE PAGINACAO
        createPaginationRestricts(query,pageable);

        // RETORNANDO RESULTADO COM PAGINACAO
        return new PageImpl<>(query.getResultList(), pageable,total(launchFilter));
    }

    /**PROJECAO / RESUMO DADOS COM ORIGEM NA MODEL LAUNCH*/
    @Override
    public Page<LaunchResume> resume(LaunchFilter launchFilter, Pageable pageable) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<LaunchResume> criteriaQuery = criteriaBuilder.createQuery(LaunchResume.class);
        Root<Launch> root = criteriaQuery.from(Launch.class);

        criteriaQuery.select(criteriaBuilder.construct(LaunchResume.class,
                root.get(Launch_.id), root.get(Launch_.description), root.get(Launch_.payDay),
                root.get(Launch_.dueDate), root.get(Launch_.amount), root.get(Launch_.note),
                root.get(Launch_.launchType),
                root.get(Launch_.category).get(Category_.name),
                root.get(Launch_.person).get(Person_.name)
        ));


        //CRIAR RESTRIÇOES / ATRIBUTOS QUE COMPOEM O "WHERE" DE QUERY TRADICIONAL PARA A FILTRAGEM DOS DADOS
        Predicate[] predicates = createRestrictsForFilter(launchFilter , criteriaBuilder , root);
        criteriaQuery.where(predicates);

        //CRIANDO QUERY usando a CRITERIA
        TypedQuery<LaunchResume> query = entityManager.createQuery(criteriaQuery);

        //RETORNANDO RESULTADO 'LIMPO' DA CRITERIA QUERY SEM TRATAMENTO DE PAGINACAO
//        return query.getResultList();

        //TRATAMENTO DE PAGINACAO

        createPaginationRestricts(query,pageable);

        // RETORNANDO RESULTADO COM PAGINACAO
        return new PageImpl<>(query.getResultList(), pageable,total(launchFilter));
    }

    //RETORNA OS ATRIBUTOS DO "LANCAMENTO" QUE SERÃO USADOS PARA FILTRAR
    //TOTALMENTE BASEADO NO DADOS DO FILTER PASSADO POR PARAMETRO
    private Predicate[] createRestrictsForFilter(LaunchFilter launchFilter, CriteriaBuilder criteriaBuilder, Root<Launch> root) {

        List<Predicate> predicatesList = new ArrayList<>();

        //VERIFICA E VALIDA OS ATRIBUTOS PASSADOS NO FILTRO E ADICIONA A CLAUSULA "WHERE"
        //EX. "where description like '%atributo filtro%'"
        if(!StringUtils.isEmpty(launchFilter.getDescription())){

            //SEM METAMODELS SUJEITO A ERRIS PASSANDO ATRIBUTOS "root.get" MANUAIS
            /*predicatesList.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("description")),
                            "%"+ launchFilter.getDescription() + "%"
                            )
            );*/

            //COM METAMODELS
            predicatesList.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get(Launch_.description)),
                            "%"+ launchFilter.getDescription() + "%"
                            )
            );
        }

        //VENCIMENTO DE
        if(launchFilter.getDueDate() != null){
            predicatesList.add(
                    criteriaBuilder.greaterThanOrEqualTo(root.get(Launch_.dueDate), launchFilter.getDueDate())
            );
        }
        //VENCIMENTO ATE
        if(launchFilter.getExpirationDate() != null){
            predicatesList.add(
                    criteriaBuilder.lessThanOrEqualTo(root.get(Launch_.dueDate), launchFilter.getExpirationDate())
            );

        }

        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }

    /**ALTERADO PARA '?' TIPO GENERICO PARA ACEITAR DE DIVERSAR MODEL PARA PAGINACAO*/
    private void createPaginationRestricts(TypedQuery<?> query, Pageable pageable) {
        Integer currentPage = pageable.getPageNumber();
        Integer maxPageSize = pageable.getPageSize();
        Integer firstRowOfPage = currentPage * maxPageSize;

        query.setFirstResult(firstRowOfPage);
        query.setMaxResults(maxPageSize);
    }

    private Long total(LaunchFilter launchFilter) {
        //construtor de criterias
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        Root<Launch> root = criteriaQuery.from(Launch.class); //SQL - FROM LAUNCH

        Predicate[] predicates = createRestrictsForFilter(launchFilter, criteriaBuilder, root); // SQL - WHERE
        criteriaQuery.where(predicates);

        criteriaQuery.select(criteriaBuilder.count(root)); //COUNT *
        return entityManager.createQuery(criteriaQuery).getSingleResult(); // retorna contagem
    }
}
