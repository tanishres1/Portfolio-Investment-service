package com.sei.portfolio.controller;

import com.sei.portfolio.dto.PortfolioRequestDTO;
import com.sei.portfolio.model.Portfolio;
import com.sei.portfolio.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @PostMapping
    public Portfolio createPortfolio(@RequestBody PortfolioRequestDTO dto) {
        return portfolioService.createPortfolio(dto);
    }

    @GetMapping
    public List<Portfolio> getAllPortfolios() {
        return portfolioService.getAllPortfolios();
    }

    @PutMapping()
    public Portfolio updatePortfolio(@RequestBody PortfolioRequestDTO dto) {
        return portfolioService.updatePortfolio(dto);

    }

    @GetMapping("/get-portfolio")
    public Portfolio getByPortfolio(@RequestParam Long id) {
        return portfolioService.getByPortfolioById(id);
    }

    @DeleteMapping("/{id}")
    public Portfolio deleteById(@PathVariable Long id) {
        return portfolioService.deletePortfolioById(id);
    }


}
