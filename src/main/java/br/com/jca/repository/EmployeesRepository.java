package br.com.jca.repository;

import br.com.jca.entity.EmployeesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeesRepository extends JpaRepository<EmployeesEntity, Long> {
    // Custom query methods can be added here
    List<EmployeesEntity> findByNameContainingIgnoreCase(String name);
}