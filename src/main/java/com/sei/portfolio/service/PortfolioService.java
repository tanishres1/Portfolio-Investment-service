package com.sei.portfolio.service;

import com.sei.portfolio.dto.PortfolioRequestDTO;
import com.sei.portfolio.model.AssetType;
import com.sei.portfolio.model.Investment;
import com.sei.portfolio.model.Portfolio;
import com.sei.portfolio.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PortfolioService {
    @Autowired
    private PortfolioRepository portfolioRepository;

    public List<Portfolio> createPortfolio(List<PortfolioRequestDTO> dtos) {
        System.out.println("create a portfolio called with input size" + dtos.size());
        List<Portfolio> portfolios = new ArrayList<>();

        for (PortfolioRequestDTO dto : dtos) {
            System.out.println("creating portfolio" + dto.getName());
            Portfolio portfolio = new Portfolio();
            portfolio.setName(dto.getName());

            Investment investment = new Investment();
            investment.setName(dto.getInvestmentName());
            investment.setAssetType(AssetType.valueOf(dto.getAssetType()));
            investment.setPortfolio(portfolio);

            portfolio.setInvestments(Arrays.asList(investment));
            portfolios.add(portfolio);
        }
        // ✅ Save all portfolios after the loop
        List<Portfolio> saved = portfolioRepository.saveAll(portfolios);
        System.out.println("saved" + saved.size() + "portfolio db");
        return saved;

    }


    // Get List of Portfolio
    public List<Portfolio> getAllPortfolios() {
        System.out.println("▶️ getAllPortfolios() called");
        return portfolioRepository.findAll();

    }

    public Portfolio updatePortfolio(PortfolioRequestDTO dto) {
        System.out.println("▶️ updatePortfolio() called for ID: " + dto.getId());
        Portfolio portfolio = portfolioRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Portfolio not found with id " + dto.getId()));
        // Update portfolio name
        portfolio.setName(dto.getName());
        System.out.println("Updated name to " + dto.getName());
        // Create new Investment and set values
        Investment investment = new Investment();
        investment.setName(dto.getInvestmentName()); // ✅ THIS IS IMPORTANT
        investment.setAssetType(AssetType.valueOf(dto.getAssetType()));
        investment.setPortfolio(portfolio);
        // Add to existing investments
        portfolio.getInvestments().add(investment);
        Portfolio updated = portfolioRepository.save(portfolio);
        System.out.println("Portfolio updated with new Investment name" + investment.getName());
        return updated;
    }

    public Portfolio getByPortfolioById(Long id) {
        System.out.println("get PortfolioById called for Id" + id);
        return portfolioRepository.findById(id).orElseThrow(() -> new RuntimeException("Portfolio id not found: " + id));
    }

    public Portfolio deletePortfolioById(Long id) {
        System.out.println("Delete PortfolioById called for id:" + id);

        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Portfolio not found with id: " + id));

        portfolioRepository.deleteById(id);  // ✅ Actual deletion

        return portfolio;
    }


    public long getPortfolioCount() {
        Long count = portfolioRepository.count();
        System.out.println("📊 Total portfolio count: " + count);
        return count;
    }


}
