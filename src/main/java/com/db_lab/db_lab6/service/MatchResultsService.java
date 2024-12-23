package com.db_lab.db_lab6.service;

import com.db_lab.db_lab6.domain.MatchResults;
import com.db_lab.db_lab6.domain.dto.MatchResultsDTO;
import com.db_lab.db_lab6.exception.FootballClubNotFoundException;
import com.db_lab.db_lab6.exception.MatchResultNotFoundException;
import com.db_lab.db_lab6.repository.FootballClubRepository;
import com.db_lab.db_lab6.repository.MatchResultsRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class MatchResultsService {
    private final MatchResultsRepository matchResultsRepository;
    private final FootballClubRepository footballClubRepository;
    private final MatchResults matchResults;

    public MatchResultsService(MatchResultsRepository matchResultsRepository, FootballClubRepository footballClubRepository, MatchResults matchResults) {
        this.matchResultsRepository = matchResultsRepository;
        this.footballClubRepository = footballClubRepository;
        this.matchResults = matchResults;
    }

    public List<MatchResults> getMatchResults() {
        return matchResultsRepository.findAllResults();
    }

    public MatchResults getMatchResult(Long id) {
        return matchResultsRepository.findByIdResult(id).orElseThrow(MatchResultNotFoundException::new);
    }
    public void createMatchResults(MatchResultsDTO matchResultsDTO) {
        Long winnerID = matchResultsDTO.getWinnerId();
        footballClubRepository.findByIdClub(winnerID).orElseThrow(FootballClubNotFoundException::new);
        int size = matchResultsRepository.findAll().size();
        matchResults.setId((long) (size+1));
        matchResults.setDescription(matchResultsDTO.getDescription());
        matchResults.setFinalScore(matchResultsDTO.getFinal_score());
        matchResults.setWinnerId(winnerID);
        matchResultsRepository.saveResult(matchResults);
    }

    public void updateMatchResults(MatchResults matchResults) {
        matchResultsRepository.findByIdResult(matchResults.getId()).orElseThrow(MatchResultNotFoundException::new);
        matchResultsRepository.saveAndFlushResult(matchResults);
    }

    @Transactional
    public void deleteMatchResultsById(Long id){
        matchResultsRepository.deleteById(id);
    }

}