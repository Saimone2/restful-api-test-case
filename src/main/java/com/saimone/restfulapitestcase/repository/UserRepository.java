package com.saimone.restfulapitestcase.repository;

import com.saimone.restfulapitestcase.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
