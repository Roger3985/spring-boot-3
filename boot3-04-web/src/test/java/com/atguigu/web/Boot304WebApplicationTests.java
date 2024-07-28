package com.atguigu.web;

import com.atguigu.web.bean.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Boot304WebApplicationTests {

	@Test
	void contextLoads() {
	}

	public static void main(String[] args) throws JsonProcessingException {
		Person person = new Person();
		person.setId(1L);
		person.setUsername("張三");
		person.setEmail("aaa@qq.com");
		person.setAge(18);
		YAMLFactory factory = new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
		ObjectMapper mapper = new ObjectMapper(factory);
		String s = mapper.writeValueAsString(person);
		System.out.println(s);
	}

}
