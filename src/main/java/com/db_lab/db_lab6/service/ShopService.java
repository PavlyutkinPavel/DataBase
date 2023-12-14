package com.db_lab.db_lab6.service;

import com.db_lab.db_lab6.domain.Shop;
import com.db_lab.db_lab6.exception.ShopNotFoundException;
import com.db_lab.db_lab6.repository.ShopRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ShopService {
    private final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public List<Shop> getShops(Principal principal) {
        return shopRepository.findAll(Sort.by("id"));
    }

    public Shop getShop(Long id, Principal principal) {
        return shopRepository.findById(id).orElseThrow(ShopNotFoundException::new);
    }
    public void createShop(Shop shop) {
        shopRepository.save(shop);
    }

    public void updateShop(Shop shop) {
        shopRepository.saveAndFlush(shop);
    }

    @Transactional
    public void deleteShopById(Long id){
        shopRepository.deleteById(id);
    }

}