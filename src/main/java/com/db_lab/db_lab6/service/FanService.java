package com.db_lab.db_lab6.service;

import com.db_lab.db_lab6.domain.Fan;
import com.db_lab.db_lab6.exception.FanNotFoundException;
import com.db_lab.db_lab6.repository.FanRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FanService {
    private final FanRepository fanRepository;

    public FanService(FanRepository fanRepository) {
        this.fanRepository = fanRepository;
    }

    public List<Fan> getFans() {
        return fanRepository.findAll(Sort.by("id"));
    }

    public Fan getFan(Long id) {
        return fanRepository.findById(id).orElseThrow(FanNotFoundException::new);
    }
    public void createFan(Fan fan) {
        fanRepository.save(fan);
    }

    public void updateFan(Fan fan) {
        fanRepository.saveAndFlush(fan);
    }

    @Transactional
    public void deleteFanById(Long id){
        fanRepository.deleteById(id);
    }

}