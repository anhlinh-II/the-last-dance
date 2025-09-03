package com.example.quiz.base.impl;

import com.example.quiz.base.baseInterface.BaseMapstruct;
import com.example.quiz.base.baseInterface.BaseRepository;
import com.example.quiz.base.baseInterface.BaseService;
import com.example.quiz.model.dto.request.RequestPagingDto;
import com.example.quiz.model.dto.response.PagingResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@SuperBuilder
public abstract class BaseServiceImpl<E, ID, R, P, V> implements BaseService<E, ID, R, P, V> {

    private final AdvancedFilterService advancedFilterService;
    private final BaseRepository<E, ID> repository;
    private final BaseMapstruct<E, R, P, V> mapper;
    private final JpaRepository<V, ID> viewRepository;

    @Override
    public P create(R request) {
        E entity = mapper.requestToEntity(request);
        E savedEntity = repository.save(entity);
        return mapper.entityToResponse(savedEntity);
    }

    @Override
    public P update(ID id, R request) {
        E existingEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));

        mapper.updateEntityFromRequest(request, existingEntity);

        E savedEntity = repository.save(existingEntity);
        return mapper.entityToResponse(savedEntity);
    }

    @Override
    public E findById(ID id) {
        Optional<E> optionalEntity = repository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        return optionalEntity.get();
    }

    @Override
    public Page<P> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::entityToResponse);
    }

    @Override
    public void deleteById(ID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public V getViewById(ID id) {
        return viewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("View not found with id: " + id));
    }

    @Override
    public Page<V> getViewPaging(Pageable pageable) {
        return viewRepository.findAll(pageable);
    }

    @Override
    public PagingResponseDto<Map<String, Object>> getViewPagingWithFilter(RequestPagingDto request) {
        return advancedFilterService.getFilteredViewData(getViewClass(), request);
    }

    // Abstract method to get the view class
    protected abstract Class<V> getViewClass();

    @Override
    public P getById(ID id) {
        return mapper.entityToResponse(findById(id)); // Delegate to updated findById method
    }

    @Override
    public Page<P> getAll(Pageable pageable) {
        return findAll(pageable); // Delegate to existing findAll method
    }

    @Override
    public void delete(ID id) {
        deleteById(id); // Delegate to existing deleteById method
    }

    @Override
    public List<P> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::entityToResponse)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public Optional<E> findEntityById(ID id) {
        return repository.findById(id);
    }
}
