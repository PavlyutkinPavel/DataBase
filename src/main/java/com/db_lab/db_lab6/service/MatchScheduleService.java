package com.db_lab.db_lab6.service;

import com.db_lab.db_lab6.domain.MatchSchedule;
import com.db_lab.db_lab6.exception.MatchScheduleNotFoundException;
import com.db_lab.db_lab6.repository.MatchScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchScheduleService {
    private final MatchScheduleRepository matchScheduleRepository;

    public MatchScheduleService(MatchScheduleRepository matchScheduleRepository) {
        this.matchScheduleRepository = matchScheduleRepository;
    }

    public List<MatchSchedule> getMatchSchedules() {
        return matchScheduleRepository.findAll(Sort.by("id"));
    }

    public MatchSchedule getMatchSchedule(Long id) {
        return matchScheduleRepository.findById(id).orElseThrow(MatchScheduleNotFoundException::new);
    }
    public void createMatchSchedule(MatchSchedule matchSchedule) {
        matchScheduleRepository.save(matchSchedule);
    }

    public void updateMatchSchedule(MatchSchedule matchSchedule) {
        matchScheduleRepository.saveAndFlush(matchSchedule);
    }

    @Transactional
    public void deleteMatchScheduleById(Long id){
        matchScheduleRepository.deleteById(id);
    }

}