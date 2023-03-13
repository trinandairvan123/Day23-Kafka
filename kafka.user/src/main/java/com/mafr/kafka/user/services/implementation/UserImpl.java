package com.mafr.kafka.user.services.implementation;

import com.mafr.kafka.user.entity.UserEntity;
import com.mafr.kafka.user.repository.UserRepository;
import com.mafr.kafka.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<UserEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public UserEntity getByID(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public String order(Long id) throws Exception {
        if (id == null) throw new Exception("Please input id user!");

        UserEntity dataCurrent = this.getByID(id);
        dataCurrent.setOrdertimes(dataCurrent.getOrdertimes()+1);

        repository.save(dataCurrent);
        return "Success";
    }

}