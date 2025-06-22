package com.sei.portfolio.controller;

import com.sei.portfolio.dto.PortfolioRequestDTO;
import com.sei.portfolio.model.Portfolio;
import com.sei.portfolio.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public List<Portfolio> createPortfolio(@RequestBody List<PortfolioRequestDTO> dtos) {
        System.out.println("Post creating portfolios count" + dtos.size());
        return portfolioService.createPortfolio(dtos);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public List<Portfolio> getAllPortfolios() {
        System.out.println("Get Fetching all portfolio");
        return portfolioService.getAllPortfolios();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping()
    public Portfolio updatePortfolio(@RequestBody PortfolioRequestDTO dto) {
        System.out.println("Updating portfolio id" + dto);
        Portfolio updatedPortfolio = portfolioService.updatePortfolio(dto);
        return updatedPortfolio;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/get-portfolio")
    public Portfolio getByPortfolio(@RequestParam Long id) {
        System.out.println("🔍 getPortfolio called with ID: " + id);
        return portfolioService.getByPortfolioById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public Portfolio deleteById(@PathVariable Long id) {
        return portfolioService.deletePortfolioById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/count")
    public long getPortfolioCount() {
        return portfolioService.getPortfolioCount();
    }

}
