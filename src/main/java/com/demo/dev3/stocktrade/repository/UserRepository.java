package com.demo.dev3.stocktrade.repository;

import com.demo.dev3.stocktrade.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Victim Musundire
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
