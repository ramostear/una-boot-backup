package com.ramostear.unaboot.repository.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName UnaRepositoryImpl
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/9 0009 0:33
 * @Version 1.0
 **/
@Slf4j
public class UnaRepositoryImpl<DOMAIN,ID> extends SimpleJpaRepository<DOMAIN,ID> implements UnaRepository<DOMAIN,ID> {

    private final JpaEntityInformation<DOMAIN,ID> entityInformation;

    private final EntityManager entityManager;

    public UnaRepositoryImpl(JpaEntityInformation<DOMAIN, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
    }

    @Override
    public List<DOMAIN> findAllByIdIn(Iterable<ID> ids, Sort sort) {
        Assert.notNull(ids,"The given Iterable of Id`s must be not null");
        Assert.notNull(sort,"Sort param must not be null");
        log.debug("Customized findAllByIdIn method was invoked");

        if(!ids.iterator().hasNext()) return Collections.emptyList();

        if(entityInformation.hasCompositeId()){
            List<DOMAIN> result = new ArrayList<>();
            ids.forEach(id->super.findById(id).ifPresent(result::add));
            return result;
        }
        ByIdsSpecification<DOMAIN> specification = new ByIdsSpecification<>(entityInformation);
        TypedQuery<DOMAIN> query = super.getQuery(specification,sort);
        return query.setParameter(specification.parameter,ids).getResultList();
    }

    @Override
    public Page<DOMAIN> findAllByIdIn(Iterable<ID> ids, Pageable pageable) {
        Assert.notNull(ids,"The given Iterable of Id`s must be not null");
        Assert.notNull(pageable,"page info must not be null");

        if(!ids.iterator().hasNext()) return new PageImpl<>(Collections.emptyList());

        if(entityInformation.hasCompositeId()) throw new UnsupportedOperationException("Unsupported find all by composite id with page info");

        ByIdsSpecification<DOMAIN> specification = new ByIdsSpecification<>(entityInformation);
        TypedQuery<DOMAIN> query = super.getQuery(specification,pageable).setParameter(specification.parameter,ids);
        TypedQuery<Long> countQuery = getCountQuery(specification,getDomainClass()).setParameter(specification.parameter,ids);

        return pageable.isUnpaged() ?
                new PageImpl<>(query.getResultList()) :
                readPage(query,getDomainClass(),pageable,countQuery);
    }

    @Override
    @Transactional
    public long deleteByIdIn(Iterable<ID> ids) {
        List<DOMAIN> entities = findAllById(ids);
        deleteInBatch(entities);
        return entities.size();
    }


    protected <S extends DOMAIN> Page<S> readPage(TypedQuery<S> query, Class<S> domainClass, Pageable pageable, TypedQuery<Long> countQuery) {

        if (pageable.isPaged()) {
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }

        return PageableExecutionUtils.getPage(query.getResultList(),pageable,()->
                executeCountQuery(countQuery)
        );
    }


    private static long executeCountQuery(TypedQuery<Long> query) {
        Assert.notNull(query, "TypedQuery must not be null!");
        List<Long> totals = query.getResultList();
        long total = 0L;

        Long element;
        for(Iterator var4 = totals.iterator(); var4.hasNext(); total += element == null ? 0L : element) {
            element = (Long)var4.next();
        }

        return total;
    }

    private static final class ByIdsSpecification<T> implements Specification<T> {
        private static final long serialVersionUID = 1L;
        private final JpaEntityInformation<T,?> entityInformation;

        private ParameterExpression<Iterable> parameter;

        public ByIdsSpecification(JpaEntityInformation<T,?> entityInformation){
            this.entityInformation = entityInformation;
        }

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
            Path<?> path = root.get(this.entityInformation.getIdAttribute());
            this.parameter = builder.parameter(Iterable.class);
            return path.in(this.parameter);
        }
    }

}
