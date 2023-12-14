package com.db_lab.db_lab6.controller;

import com.db_lab.db_lab6.domain.Shop;
import com.db_lab.db_lab6.domain.User;
import com.db_lab.db_lab6.security.service.SecurityService;
import com.db_lab.db_lab6.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Tag(name = "Shop Controller", description = "Makes all operations with Shop")
@RestController
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;
    private final SecurityService securityService;

    public ShopController(ShopService shopService, SecurityService securityService) {
        this.shopService = shopService;
        this.securityService = securityService;
    }

    @Operation(summary = "get all Shops(for admins)")
    @GetMapping
    public ResponseEntity<List<Shop>> getShopList(Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            List<Shop> shops = shopService.getShops(principal);
            if (shops.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(shops, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @Operation(summary = "get Shop (for authorized users)")
    @GetMapping("/{id}")
    public ResponseEntity<Shop> getShop(@PathVariable Long id, Principal principal) {
        Shop shop = shopService.getShop(id, principal);
        if (shop != null) {
            return new ResponseEntity<>(shop, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //maybe should be deleted(or only for admins)
    @Operation(summary = "create Shop (for authorized users)")
    @PostMapping
    public ResponseEntity<HttpStatus> createShop(@RequestBody Shop shop) {
        shopService.createShop(shop);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "update Shop (for authorized users)")
    @PutMapping
    public ResponseEntity<HttpStatus> updateShop(@RequestBody Shop shop, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            shopService.updateShop(shop);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "delete Shop (for authorized users)")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteShop(@PathVariable Long id, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            shopService.deleteShopById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
