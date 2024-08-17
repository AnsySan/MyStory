package com.clone.twitter.userservice.mapper;

public interface AbstractMapper<E, D>{

    E toEntity(D dto);

    D toDto(E entity);

}