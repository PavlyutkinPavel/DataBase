package com.db_lab.db_lab6.service;

import com.db_lab.db_lab6.domain.FootballClub;
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

    public FootballClubService(FootballClubRepository footballClubRepository) {
        this.footballClubRepository = footballClubRepository;
    }
    public List<FootballClub> getFootballClubs() {
        return footballClubRepository.findAll(Sort.by("id"));
    }
    public FootballClub getFootballClub(Long id) {
        return footballClubRepository.findById(id).orElseThrow(FootballClubNotFoundException::new);
    }
    public void createFootballClub(FootballClub footballClub) {
        footballClubRepository.save(footballClub);
    }

    public void updateFootballClub(FootballClub footballClub) {
        footballClubRepository.saveAndFlush(footballClub);
    }
    @Transactional
    public void deleteFootballClubById(Long id){
        footballClubRepository.deleteById(id);
    }

}