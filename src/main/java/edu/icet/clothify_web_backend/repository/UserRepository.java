package edu.icet.clothify_web_backend.repository;

import edu.icet.clothify_web_backend.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity,Integer> {
}
