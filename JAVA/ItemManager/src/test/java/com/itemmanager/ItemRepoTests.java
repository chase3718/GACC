package com.itemmanager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.itemmanager.business.Item;
import com.itemmanager.db.ItemRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ItemRepoTests {
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private ItemRepository itemRepo;
	
	@Test
	public void findByItemNameShouldReturnItem() {
		entityManager.persist(new Item("Test Item", 10, 10));
		Optional<Item> u = itemRepo.findByName("Test Item");
		assertThat(u.get().getName()).isEqualTo("Test Item");
	}
	
}
