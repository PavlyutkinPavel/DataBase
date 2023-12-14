package com.db_lab.db_lab6.service;

import com.db_lab.db_lab6.domain.Coach;
import com.db_lab.db_lab6.exception.CoachNotFoundException;
import com.db_lab.db_lab6.repository.CoachRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoachService {
    private final CoachRepository coachRepository;

    public CoachService(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    public List<Coach> getCoaches() {
        return coachRepository.findAll(Sort.by("id"));
    }

    public Coach getCoach(Long id) {
        return coachRepository.findById(id).orElseThrow(CoachNotFoundException::new);
    }
    public void createCoach(Coach coach) {
        coachRepository.save(coach);
    }

    public void updateCoach(Coach coach) {
        coachRepository.saveAndFlush(coach);
    }

    @Transactional
    public void deleteCoachById(Long id){
        coachRepository.deleteById(id);
    }

}