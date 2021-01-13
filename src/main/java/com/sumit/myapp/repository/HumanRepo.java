package com.sumit.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sumit.myapp.model.Human;

@Repository
public interface HumanRepo extends JpaRepository<Human, Long> {
}
