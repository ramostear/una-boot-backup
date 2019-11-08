package com.ramostear.unaboot.repository.support;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;

import java.util.List;


@NoRepositoryBean
public interface UnaRepository<ENTITY,ID> extends JpaRepository<ENTITY,ID> {
    @NonNull
    List<ENTITY> findAllByIdIn(@NonNull Iterable<ID> ids, @NonNull Sort sort);

    @NonNull
    Page<ENTITY> findAllByIdIn(@NonNull Iterable<ID> ids, @NonNull Pageable pageable);

    long deleteByIdIn(@NonNull Iterable<ID> ids);
}
