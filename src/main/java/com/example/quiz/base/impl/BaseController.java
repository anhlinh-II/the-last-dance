package com.example.quiz.base.impl;

import com.example.quiz.base.baseInterface.BaseService;
import com.example.quiz.model.dto.request.RequestPagingDto;
import com.example.quiz.model.dto.response.ApiResponse;
import com.example.quiz.model.dto.response.PagingResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

public abstract class BaseController<E, ID, R, P, V> {

    protected final BaseService<E, ID, R, P, V> service;

    protected BaseController(BaseService<E, ID, R, P, V> service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ApiResponse<List<P>> findAll() {
        return ApiResponse.successOf(service.findAll());
    }

    @GetMapping("/paged")
    public ApiResponse<Page<P>> findAllPaged(Pageable pageable) {
        return ApiResponse.successOf(service.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<P> findById(@PathVariable ID id) {
        return ApiResponse.successOf(service.getById(id));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<P> create(@Valid @RequestBody R request) {
        return ApiResponse.successOf(service.create(request));
    }

    @PutMapping("/edit/{id}")
    public ApiResponse<P> update(@PathVariable ID id, @Valid @RequestBody R request) {
        return ApiResponse.successOf(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> delete(@PathVariable ID id) {
        service.delete(id);
        return ApiResponse.success();
    }

    @GetMapping("/views/{id}")
    public ApiResponse<V> getViewById(@PathVariable ID id) {
        return ApiResponse.successOf(service.getViewById(id));
    }

    @GetMapping("/views")
    public ApiResponse<Page<V>> getViewsPaged(Pageable pageable) {
        return ApiResponse.successOf(service.getViewPaging(pageable));
    }

    @PostMapping("/views/list")
    public ApiResponse<PagingResponseDto<Map<String, Object>>> getViewsPaged(@Valid @RequestBody RequestPagingDto request) {
        PagingResponseDto<Map<String, Object>> result = service.getViewPagingWithFilter(request);
        return ApiResponse.successOf(result);
    }
}

