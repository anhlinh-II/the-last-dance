package com.example.quiz.base.baseInterface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BaseService<E, ID, R, P, V> {
    // CRUD operations with Request/Response
    P create(R request);
    P update(ID id, R request);
    P getById(ID id);
    Page<P> getAll(Pageable pageable);
    void delete(ID id);

    P findById(ID id);

    Page<P> findAll(Pageable pageable);

    void deleteById(ID id);

    // View operations
    V getViewById(ID id);
    Page<V> getViewPaging(Pageable pageable);

    // Additional common operations (optional)
    List<P> findAll();
    Optional<E> findEntityById(ID id);
}
