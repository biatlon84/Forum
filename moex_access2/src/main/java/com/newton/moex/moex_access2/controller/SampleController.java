/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.newton.moex.moex_access2.controller;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.newton.moex.moex_access2.pojo.document;

@Controller
public class SampleController {
	// private static final String template = "Hello, %s!";
	String uri = "";

	// @Autowired
	RestTemplate restTemplate = new RestTemplate();

	@GetMapping("/")
	@ResponseBody
	public Greeting sayHello(@RequestParam(name = "name", required = false, defaultValue = "MOEX") String name) {

		String ticker = name;

		// uri =
		// "https://iss.moex.com/iss/engines/stock/markets/shares/boards/tqbr/securities/"
		// + ticker
		// + ".xml?iss.meta=off";

		uri = "https://iss.moex.com/iss/engines/stock/markets/shares/boards/tqbr/securities/" + ticker;

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_XML }));
		// Request to return XML format
		headers.setContentType(MediaType.APPLICATION_XML);
		headers.set("iss.meta", "off");

		HttpEntity<document> entity = new HttpEntity<document>(headers);

		ResponseEntity<document> roott = restTemplate.exchange(uri, HttpMethod.GET, entity, document.class);
		// XmlMapper xmlMapper = new XmlMapper();
		document res = roott.getBody();
		System.out.print(res.data.get(1).rows.rowu.LAST);
		double last = res.data.get(0).rows.rowu.LAST;
		return new Greeting(0, Double.toString(last));
	}
}
