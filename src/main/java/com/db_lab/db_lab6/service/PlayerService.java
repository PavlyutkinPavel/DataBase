package com.db_lab.db_lab6.service;

import com.db_lab.db_lab6.domain.Player;
import com.db_lab.db_lab6.exception.PlayerNotFoundException;
import com.db_lab.db_lab6.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers() {
        return playerRepository.findAll(Sort.by("id"));
    }

    public Player getPlayer(Long id) {
        return playerRepository.findById(id).orElseThrow(PlayerNotFoundException::new);
    }
    public void createPlayer(Player player) {
        playerRepository.save(player);
    }

    public void updatePlayer(Player player) {
        playerRepository.saveAndFlush(player);
    }

    @Transactional
    public void deletePlayerById(Long id){
        playerRepository.deleteById(id);
    }

}