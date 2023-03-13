package com.mafr.kafka.user.services;

import com.mafr.kafka.user.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> getAll();

    UserEntity getByID(Long id);

    String order(Long id) throws Exception;
}
