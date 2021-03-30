package com.algaworks.algamoney.api.repository.launch;

import com.algaworks.algamoney.api.model.Launch;
import com.algaworks.algamoney.api.repository.filter.LaunchFilter;
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
    public List<Launch> filter(LaunchFilter launchFilter) {

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
        return query.getResultList();
    }

    //RETORNA OS ATRIBUTOS DO "LANCAMENTO" QUE SERÃO USADOS PARA FILTRAR
    //TOTALMENTE BASEADO NO DADOS DO FILTER PASSADO POR PARAMETRO
    private Predicate[] createRestrictsForFilter(LaunchFilter launchFilter, CriteriaBuilder criteriaBuilder, Root<Launch> root) {

        List<Predicate> predicatesList = new ArrayList<>();

        //VERIFICA E VALIDA OS ATRIBUTOS PASSADOS NO FILTRO E ADICIONA A CLAUSULA "WHERE"
        //EX. "where description like '%atributo filtro%'"
        if(!StringUtils.isEmpty(launchFilter.getDescription())){

            predicatesList.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("description")),
                            "%"+ launchFilter.getDescription() + "%"
                            )
            );
        }

        //VENCIMENTO DE
        if(launchFilter.getDueDate() != null){
//            predicatesList.add()
        }
        //VENCIMENTO ATE
        if(launchFilter.getExpirationDate() != null){
//            predicatesList.add()

        }

        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }
}
