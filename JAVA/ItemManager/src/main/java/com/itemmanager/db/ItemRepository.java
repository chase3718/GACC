package com.itemmanager.db;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.itemmanager.business.Item;

public interface ItemRepository extends CrudRepository<Item, Integer>{
	Optional<Item> findByName(String name);
}
