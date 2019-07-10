package com.itemmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.itemmanager.business.Item;
import com.itemmanager.db.ItemRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemTests {
	@Autowired
	private ItemRepository itemRepo;
	private Item p;

	@Before
	public void testItemAdd() {
		p = new Item("Test Item", 10, 10);
		assertNotNull(itemRepo.save(p));
	}

	@Test
	public void testItemGetAll() {
		Iterable<Item> items = itemRepo.findAll();
		assertNotNull(items);
	}

	@Test
	public void testItemGet() {
		assertNotNull(itemRepo.findById(p.getId()));
	}

	@Test
	public void testItemUpdate() {
		p.setName("UpdateName");
		assertNotNull(itemRepo.save(p));
		assertEquals(p.getName(), "UpdateName");
	}

	@After
	public void testItemDelete() {
		itemRepo.delete(p);
		assertFalse(itemRepo.findById(p.getId()).isPresent());
	}
}
