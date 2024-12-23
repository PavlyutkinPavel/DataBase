package com.db_lab.db_lab6.service;

import com.db_lab.db_lab6.domain.FootballClub;
import com.db_lab.db_lab6.domain.dto.FootballClubDTO;
import com.db_lab.db_lab6.exception.FootballClubNotFoundException;
import com.db_lab.db_lab6.repository.FootballClubRepository;
import com.db_lab.db_lab6.security.repository.SecurityCredentialsRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FootballClubService {
    private final FootballClubRepository footballClubRepository;
    private final FootballClub footballClub;

    public FootballClubService(FootballClubRepository footballClubRepository, FootballClub footballClub) {
        this.footballClubRepository = footballClubRepository;
        this.footballClub = footballClub;
    }
    public List<FootballClub> getFootballClubs() {
        return footballClubRepository.findAllClubs();
    }
    public FootballClub getFootballClub(Long id) {
        return footballClubRepository.findByIdClub(id).orElseThrow(FootballClubNotFoundException::new);
    }
    public void createFootballClub(FootballClubDTO footballClubDTO) {
        int size = footballClubRepository.findAllClubs().size();
        footballClub.setId((long) (size+1));
        footballClub.setClubName(footballClubDTO.getClubName());
        footballClub.setLocation(footballClubDTO.getLocation());
        footballClub.setStatus(footballClubDTO.getStatus());
        footballClub.setAchievements(footballClubDTO.getAchievements());
        footballClub.setWins(footballClubDTO.getWins());
        footballClubRepository.saveFootballClub(footballClub);
    }

    public void updateFootballClub(FootballClub footballClub) {
        footballClubRepository.saveAndFlushFootballClub(footballClub);
    }
    @Transactional
    public void deleteFootballClubById(Long id){
        footballClubRepository.deleteById(id);
    }

}