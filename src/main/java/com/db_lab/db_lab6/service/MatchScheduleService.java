package com.db_lab.db_lab6.service;

import com.db_lab.db_lab6.domain.MatchSchedule;
import com.db_lab.db_lab6.domain.dto.MatchScheduleDTO;
import com.db_lab.db_lab6.exception.FootballClubNotFoundException;
import com.db_lab.db_lab6.exception.MatchScheduleNotFoundException;
import com.db_lab.db_lab6.repository.FootballClubRepository;
import com.db_lab.db_lab6.repository.MatchScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchScheduleService {
    private final MatchScheduleRepository matchScheduleRepository;
    private final FootballClubRepository footballClubRepository;
    private final MatchSchedule matchSchedule;

    public MatchScheduleService(MatchScheduleRepository matchScheduleRepository, MatchSchedule matchSchedule, FootballClubRepository footballClubRepository) {
        this.matchScheduleRepository = matchScheduleRepository;
        this.matchSchedule = matchSchedule;
        this.footballClubRepository = footballClubRepository;
    }

    public List<MatchSchedule> getMatchSchedules() {
        return matchScheduleRepository.findAllMatches();
    }

    public MatchSchedule getMatchSchedule(Long id) {
        return matchScheduleRepository.findByIdMatch(id).orElseThrow(MatchScheduleNotFoundException::new);
    }
    public void createMatchSchedule(MatchScheduleDTO matchScheduleDTO) {
        footballClubRepository.findByIdClub(matchScheduleDTO.getHomeTeam()).orElseThrow(FootballClubNotFoundException::new);
        footballClubRepository.findByIdClub(matchScheduleDTO.getAwayTeam()).orElseThrow(FootballClubNotFoundException::new);
        int size = matchScheduleRepository.findAllMatches().size();
        matchSchedule.setId((long) (size+1));
        matchSchedule.setMatchDate(matchScheduleDTO.getMatch_date());
        matchSchedule.setMatchLocation(matchScheduleDTO.getMatch_location());
        matchSchedule.setHomeTeam(matchScheduleDTO.getHomeTeam());
        matchSchedule.setAwayTeam(matchScheduleDTO.getAwayTeam());
        matchSchedule.setAvailableTickets(matchScheduleDTO.getAvailable_tickets());
        matchScheduleRepository.saveMatch(matchSchedule);
    }

    public void updateMatchSchedule(MatchSchedule matchSchedule) {
        matchScheduleRepository.findByIdMatch(matchSchedule.getId()).orElseThrow(MatchScheduleNotFoundException::new);
        matchScheduleRepository.saveAndFlushMatch(matchSchedule);
    }

    @Transactional
    public void deleteMatchScheduleById(Long id){
        matchScheduleRepository.deleteByIdMatch(id);
    }

}