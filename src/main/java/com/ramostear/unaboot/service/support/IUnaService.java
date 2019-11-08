package com.ramostear.unaboot.service.support;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IUnaService<T,ID> {
    /**
     * List all data
     * @return  data list
     */
    @NonNull
    List<T> listAll();

    /**
     * List all data by sort
     * @param sort      sort
     * @return          data list
     */
    @NonNull
    List<T> listAll(@NonNull Sort sort);

    /**
     * List all data by pageable
     * @param pageable          page info
     * @return                  data list
     */
    @NonNull
    Page<T> listAll(@NonNull Pageable pageable);

    /**
     * List all data by ids
     * @param ids       ids
     * @return          data list
     */
    @NonNull
    List<T> listAllByIds(@NonNull Collection<ID> ids);

    /**
     * List all data by ids and sort
     * @param ids           ids
     * @param sort          sort
     * @return              data list
     */
    @NonNull
    List<T> listAllByIdsAndSort(@NonNull Collection<ID> ids,@NonNull Sort sort);

    /**
     * Fetch data by id
     * @param id        id
     * @return          Optional
     */
    @NonNull
    Optional<T> fetchById(ID id);

    /**
     * Get data by id
     * @param id        id
     * @return          Domain Object value
     */
    @NonNull
    T getById(@NonNull ID id);

    @Nullable
    T getByIdNullable(@NonNull ID id);

    /**
     * exists by id
     * @param id        id
     * @return          true/false
     */
    boolean existsById(@NonNull ID id);

    /**
     * must exists by id or throw NotFoundException
     * @param id
     */
    void notNullById(@NonNull ID id);

    /**
     * total count
     * @return      long
     */
    long count();

    /**
     * save data
     * @param t     data
     * @return      data
     */
    @NonNull
    @Transactional
    T create(@NonNull T t);

    /**
     * save data in batch
     * @param collection        data collection
     * @return                  collection
     */
    @NonNull
    @Transactional
    List<T> createInBatch(@NonNull Collection<T> collection);

    /**
     * update database data
     * @param t
     * @return
     */
    @NonNull
    @Transactional
    T update(@NonNull T t);

    /**
     * update database data in batch
     * @param collection        data collection
     * @return
     */
    @NonNull
    @Transactional
    List<T> updateInBatch(@NonNull Collection<T> collection);

    /**
     * Flush all pending changes to the database
     */
    void flush();

    /**
     * remove by id
     * @param id
     * @return
     */
    @NonNull
    @Transactional
    T removeById(@NonNull ID id);

    @Nullable
    @Transactional
    T removeNullable(@NonNull ID id);

    @NonNull
    @Transactional
    void remove(@NonNull T t);

    @Transactional
    void removeInBatch(@NonNull Collection<ID> ids);

    @Transactional
    void removeAll(@NonNull Collection<T> collection);

    @Transactional
    void removeAll();
}
