package com.example.contractormarketplace.controller;

import com.example.contractormarketplace.domain.NewBid;
import com.example.contractormarketplace.entity.Bid;
import com.example.contractormarketplace.entity.Project;
import com.example.contractormarketplace.service.MarketplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@CrossOrigin(origins = "*")
public class Marketplace {

  @Autowired
  MarketplaceService marketplaceService;

  @PostMapping("/project")
  public @ResponseBody
  Project createProject(@RequestBody Project project) {
    return marketplaceService.createProject(project);
  }

  @GetMapping("/projects-by-client/{clientId}")
  public @ResponseBody
  List<Project> projectsByClient(@PathVariable("clientId") String clientId) {
    return marketplaceService.projectsByClient(clientId);
  }

  @GetMapping("/projects")
  public @ResponseBody
  List<Project> projects() {
    return marketplaceService.allProjects();
  }

  @GetMapping("/project/{projectId}")
  public @ResponseBody
  Project projectById(@PathVariable("projectId") long projectId) {
    return marketplaceService.projectById(projectId);
  }

  @PostMapping("/bid")
  public @ResponseBody
  Bid createBid(@RequestBody NewBid bid) {
    return marketplaceService.createBid(bid);
  }

  @GetMapping("/bids-by-project/{projectId}")
  public @ResponseBody
  List<Bid> projectBids(@PathVariable("projectId") long projectId) {
    return marketplaceService.bidsByProject(projectId);
  }

  @GetMapping("/bids-by-contractor/{contractorId}")
  public @ResponseBody
  List<Bid> contractorBids(@PathVariable("contractorId") String contractorId) {
    return marketplaceService.bidsByContractor(contractorId);
  }
}
