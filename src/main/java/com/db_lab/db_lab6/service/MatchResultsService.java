package com.db_lab.db_lab6.service;

import com.db_lab.db_lab6.domain.MatchResults;
import com.db_lab.db_lab6.exception.MatchResultNotFoundException;
import com.db_lab.db_lab6.repository.MatchResultsRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class MatchResultsService {
    private final MatchResultsRepository matchResultsRepository;

    public MatchResultsService(MatchResultsRepository matchResultsRepository) {
        this.matchResultsRepository = matchResultsRepository;
    }

    public List<MatchResults> getMatchResults() {
        return matchResultsRepository.findAll(Sort.by("id"));
    }

    public MatchResults getMatchResult(Long id) {
        return matchResultsRepository.findById(id).orElseThrow(MatchResultNotFoundException::new);
    }
    public void createMatchResults(MatchResults matchResults) {
        matchResultsRepository.save(matchResults);
    }

    public void updateMatchResults(MatchResults matchResults) {
        matchResultsRepository.saveAndFlush(matchResults);
    }

    @Transactional
    public void deleteMatchResultsById(Long id){
        matchResultsRepository.deleteById(id);
    }

}