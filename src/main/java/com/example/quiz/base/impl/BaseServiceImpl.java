package com.example.quiz.base.impl;

import com.example.quiz.base.baseInterface.BaseMapstruct;
import com.example.quiz.base.baseInterface.BaseRepository;
import com.example.quiz.base.baseInterface.BaseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseServiceImpl<E, ID, R, P, V> implements BaseService<E, ID, R, P, V> {

    protected abstract BaseRepository<E, ID> getRepository();
    protected abstract BaseMapstruct<E, R, P, V> getMapper();
    protected abstract JpaRepository<V, ID> getViewRepository();

    @Override
    public P create(R request) {
        E entity = getMapper().requestToEntity(request);
        E savedEntity = getRepository().save(entity);
        return getMapper().entityToResponse(savedEntity);
    }

    @Override
    public P update(ID id, R request) {
        E existingEntity = getRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));

        getMapper().updateEntityFromRequest(request, existingEntity);

        E savedEntity = getRepository().save(existingEntity);
        return getMapper().entityToResponse(savedEntity);
    }

    @Override
    public P findById(ID id) {
        return getRepository().findById(id)
                .map(getMapper()::entityToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
    }

    @Override
    public Page<P> findAll(Pageable pageable) {
        return getRepository().findAll(pageable)
                .map(getMapper()::entityToResponse);
    }

    @Override
    public void deleteById(ID id) {
        if (!getRepository().existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        getRepository().deleteById(id);
    }

    @Override
    public V getViewById(ID id) {
        return getViewRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("View not found with id: " + id));
    }

    @Override
    public Page<V> getViewPaging(Pageable pageable) {
        return getViewRepository().findAll(pageable);
    }
}
