package com.example.contractormarketplace.service;

import com.example.contractormarketplace.domain.NewBid;
import com.example.contractormarketplace.domain.ProjectStatus;
import com.example.contractormarketplace.entity.Bid;
import com.example.contractormarketplace.entity.Project;
import com.example.contractormarketplace.repo.BidRepository;
import com.example.contractormarketplace.repo.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MarketplaceService {

  @Autowired
  BidRepository bidRepository;

  @Autowired
  ProjectRepository projectRepository;

  public List<Project> allProjects() {
    return projectRepository.findAll();
  }

  public Project projectById(long projectId) {
    return projectRepository.findById(projectId).get();
  }

  public List<Project> projectsByClient(String clientId) {
    return projectRepository.findByClientId(clientId);
  }

  public Bid createBid(NewBid newBid) {
    Optional<Project> project = projectRepository.findById(newBid.getProjectId());
    if (!project.isPresent()) throw new RuntimeException("No project found for that id");

    Bid bid = new Bid();
    bid.setProject(project.get());
    bid.setContractorId(newBid.getContractorId());
    bid.setMin(newBid.getMin());
    bid.setInitialPrice(newBid.getPrice());
    bid.setDate(newBid.getDate());
    bidRepository.save(bid);
    return bid;
  }

  public Project createProject(Project project) {
    return projectRepository.save(project);
  }

  public List<Bid> bidsByProject(long projectId) {
    Optional<Project> project = projectRepository.findById(projectId);
    if (project.isPresent()) {
      return bidRepository.findByProject(project.get());
    } else {
      return Collections.emptyList();
    }
  }

  public List<Bid> bidsByContractor(String userId) {
    return bidRepository.findByContractorId(userId);
  }

  public void closeAllOpenExpiredProjects() {
    List<Project> openExpiredProjects = findExpiredOpenProjects();
    System.out.println("openExpiredProjects");
    System.out.println(openExpiredProjects);
    openExpiredProjects.forEach(project -> closeProject(project));
  }

  public void closeProject(Project project) {
    setWinningBid(project.getId());
    project.setProjectStatus(ProjectStatus.CLOSED);
    projectRepository.save(project);
  }

  public List<Project> findExpiredOpenProjects() {
    return projectRepository.findExpiredOpenProjects(new Date());
  }

  public void setWinningBid(long projectId) {
    List<Bid> lowestTwo = bidRepository.findLowestTwoBids(projectId);

    // If there is only one bid, that person gets the project for their bid price
    if (lowestTwo.size() == 1) {
      Bid onlyBid = lowestTwo.get(0);
      onlyBid.setFinalPrice(onlyBid.getInitialPrice());
      onlyBid.setWin(true);
      bidRepository.save(onlyBid);
    } else if (lowestTwo.size() == 2) {
      Bid lowestBid = lowestTwo.get(0);
      Bid secondLowestBid = lowestTwo.get(1);
    /*
      If there was a tie at the min value,
      whichever bid came in 1st in the order wins, with the min value as the final value
    */
      if (lowestBid.getMin().equals(secondLowestBid.getMin())) {
        lowestBid.setFinalPrice(lowestBid.getMin());
        lowestBid.setWin(true);
        bidRepository.save(lowestBid);
      }

    /*
      If it wasn't a tie, the bid with the lowest min value with get a final price
      of the other min value minus one penny, but not below zero
    */
      BigDecimal secondLowestMinMinusOnePenny = secondLowestBid.getMin().subtract(BigDecimal.valueOf(0.01));
      lowestBid.setFinalPrice(secondLowestMinMinusOnePenny.min(BigDecimal.ZERO));
      lowestBid.setWin(true);
      bidRepository.save(lowestBid);
    }

    /*
     If there were no bids, do nothing
    */
  }
}
