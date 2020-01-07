package com.example.contractormarketplace.repo;

import com.example.contractormarketplace.entity.Bid;
import com.example.contractormarketplace.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
  List<Bid> findByProject(Project project);
  List<Bid> findByContractorId(String contractorId);

  @Query(
    value = "SELECT * FROM bid WHERE project_id = :projectId ORDER BY min, initial_price LIMIT 2",
    nativeQuery = true
  )
  List<Bid> findLowestTwoBids(@Param("projectId") long projectId);
}
