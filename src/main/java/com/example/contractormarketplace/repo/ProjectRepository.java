package com.example.contractormarketplace.repo;

import com.example.contractormarketplace.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
  List<Project> findByClientId(String clientId);
  Optional<Project> findById(long projectId);

  @Query(
    value = "SELECT * FROM project WHERE deadline < :currentTime AND project_status = 0",
    nativeQuery = true
  )
  List<Project> findExpiredOpenProjects(@Param("currentTime") Date currentTime);
}
