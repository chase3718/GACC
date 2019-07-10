package com.itemmanager.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itemmanager.business.Item;
import com.itemmanager.business.JsonResponse;
import com.itemmanager.db.ItemRepository;;

@CrossOrigin
@RestController
@RequestMapping("/items")
public class ItemController {
	@Autowired
	private ItemRepository itemRepo;
	
	@GetMapping("/")
	public JsonResponse getAll() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(itemRepo.findAll());
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<Item> i = itemRepo.findById(id);
			if (i.isPresent()) {
				jr = JsonResponse.getInstance(i);
			} else {
				jr = JsonResponse.getInstance("No item found with id: " + id);
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@PostMapping("/")
	public JsonResponse add(@RequestBody Item item) {
		JsonResponse jr = null;
		try {
			if (item.getQuality() > 50) {
				item.setQuality(50);
			} else if (item.getQuality() < 0) {
				item.setQuality(0);
			}
			jr = JsonResponse.getInstance(itemRepo.save(item));
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@DeleteMapping("/")
	public JsonResponse delete(@RequestBody Item item) {
		JsonResponse jr = null;
		try {
			if (itemRepo.existsById(item.getId())) {
				itemRepo.delete(item);
				jr = JsonResponse.getInstance("Item deleted");
			} else {
				jr = JsonResponse.getInstance("No Item: " + item);
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PutMapping("/")
	public JsonResponse update(@RequestBody Item item) {
		JsonResponse jr = null;
		try {
			if (itemRepo.existsById(item.getId())) {
				jr = JsonResponse.getInstance(itemRepo.save(item));
			} else {
				jr = JsonResponse.getInstance("No Item exists with id: " + item.getId());
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@GetMapping("/progress-day")
	public JsonResponse progressDay() {
		JsonResponse jr = null;
		try {
			Iterable<Item> items = itemRepo.findAll();
			for (Item item: items) {
				if (item.getQuality() > 50) {
					item.setQuality(50);
				} else if (item.getQuality() < 0) {
					item.setQuality(0);
				}
				if (item.getName().equals("Aged Brie")) {
					if (item.getSellIn() > 0 && item.getQuality() < 50) {
						item.setQuality(item.getQuality() + 1);
					}
					item.setSellIn(item.getSellIn() - 1);
				} else if (item.getName().equals("Sulfuras")) {
					
				} else if (item.getName().equals("Concert backstage passes")) {
					if (item.getSellIn() > 10) {
						item.setQuality(item.getQuality() + 1);
					} else if (item.getSellIn() <= 10) {
						item.setQuality(item.getQuality() + 2);
					} else {
						item.setQuality(0);
					}
					if (item.getQuality() > 50) {
						item.setQuality(50);
					}
					item.setSellIn(item.getSellIn() - 1);
				} else {
					if (item.getSellIn() >= 0) {
						if (item.getQuality() > 0) {
							item.setQuality(item.getQuality() - 1);
						}
					} else {
						if (item.getQuality() > 0) {
							item.setQuality(item.getQuality() - 2);
							if (item.getQuality() < 0) {
								item.setQuality(0);
							}
						}
					}
					item.setSellIn(item.getSellIn() - 1);
				}
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
}
