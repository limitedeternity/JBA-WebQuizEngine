package com.limitedeternity.engine.repositories;

import com.limitedeternity.engine.models.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);
}
