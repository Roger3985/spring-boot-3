package com.atguigu.boot3.edis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.UUID;

@SpringBootTest
class Boot309RedisApplicationTests {

	/**
	 * key value 因為使用 StringRedisTemplate 所以以下的 key and value 都要是字符串(String)
	 */
	@Autowired
	StringRedisTemplate redisTemplate;

	/**
	 * string：普通字符串： redisTemplate.opsForValue()
	 */
	@Test
	void testString() {

		redisTemplate.opsForValue().set("haha", UUID.randomUUID().toString());

		String haha = redisTemplate.opsForValue().get("haha");

		System.out.println(haha);
	}

	/**
	 * list:列表:redisTemplate.opsForList()
	 */
	@Test
	void testList() {
		String listName = "listTest";
		redisTemplate.opsForList().leftPush(listName, "1");
		redisTemplate.opsForList().leftPush(listName, "2");
		redisTemplate.opsForList().leftPush(listName, "3");

		String pop = redisTemplate.opsForList().leftPop(listName);
		Assertions.assertEquals("3", pop);
	}

	/**
	 * set:集合:redisTemplate.opsForSet()
	 */
	@Test
	void testSet() {
		String setName = "setTest";

		// 1. 給集合中添加元素
		redisTemplate.opsForSet().add(setName, "1", "2", "3", "3");

		Boolean aBoolean = redisTemplate.opsForSet().isMember(setName, "2");
		Assertions.assertTrue(aBoolean);

		Boolean aBoolean1 = redisTemplate.opsForSet().isMember(setName, "5");
		Assertions.assertFalse(aBoolean1);
	}

	/**
	 * zset:有序集合:redisTemplate.opsForZSet()
	 */
	@Test
	void testZSet() {
		String setName = "zsetTest";

		// 1. 給集合中添加元素
		redisTemplate.opsForZSet().add(setName, "Roger1", 90.00);
		redisTemplate.opsForZSet().add(setName, "Roger2", 99.00);
		redisTemplate.opsForZSet().add(setName, "Roger3", 91.00);
		redisTemplate.opsForZSet().add(setName, "Roger4", 92.00);
		redisTemplate.opsForZSet().add(setName, "Roger5", 93.00);
		redisTemplate.opsForZSet().add(setName, "Roger6", 94.00);

		ZSetOperations.TypedTuple<String> popMax = redisTemplate.opsForZSet().popMax(setName);
		String value = popMax.getValue();
		Double score = popMax.getScore();
		System.out.println(value + "====>" + score);
	}

	/**
	 * hash:key:value(map結構):redisTemplate.opsForHash()
	 */
	@Test
	void testHash() {
		String mapName = "amap";
		redisTemplate.opsForHash().put("amap", "name", "Roger1");
		redisTemplate.opsForHash().put("amap", "age", "18");

		System.out.println(redisTemplate.opsForHash().get(mapName, "name"));
	}

}
