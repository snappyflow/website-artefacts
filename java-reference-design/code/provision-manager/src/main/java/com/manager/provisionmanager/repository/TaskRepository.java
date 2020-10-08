package com.manager.provisionmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manager.provisionmanager.entity.Tasks;

@Repository
public interface TaskRepository extends JpaRepository<Tasks, Long> {

}
