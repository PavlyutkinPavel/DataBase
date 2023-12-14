package com.db_lab.db_lab6.service;

import com.db_lab.db_lab6.domain.Stadium;
import com.db_lab.db_lab6.exception.StadiumNotFoundException;
import com.db_lab.db_lab6.repository.StadiumRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StadiumService {
    private final StadiumRepository stadiumRepository;

    public StadiumService(StadiumRepository stadiumRepository) {
        this.stadiumRepository = stadiumRepository;
    }

    public List<Stadium> getStadiums() {
        return stadiumRepository.findAll(Sort.by("id"));
    }

    public Stadium getStadium(Long id) {
        return stadiumRepository.findById(id).orElseThrow(StadiumNotFoundException::new);
    }
    public void createStadium(Stadium stadium) {
        stadiumRepository.save(stadium);
    }

    public void updateStadium(Stadium stadium) {
        stadiumRepository.saveAndFlush(stadium);
    }

    @Transactional
    public void deleteStadiumById(Long id){
        stadiumRepository.deleteById(id);
    }

}