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

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.newton.moex.moex_access2.pojo.Resp;

@RestController
public class SampleController {
	String request = "";

	@GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Resp getLast(@RequestParam(name = "SECID", required = false, defaultValue = "MOEX") String ticker) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document res = null;
		String last = null;
		request = "https://iss.moex.com/iss/engines/stock/markets/shares/boards/tqbr/securities/" + ticker
				+ ".xml?iss.meta=off";
		try {
			builder = factory.newDocumentBuilder();
			res = builder.parse(request);
			last = res.getDocumentElement().getElementsByTagName("row").item(1).getAttributes().getNamedItem("LAST")
					.getNodeValue();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		// System.out.println(res.getDocumentElement().getElementsByTagName("row").item(1).getAttributes()
		// .getNamedItem("LAST").getNodeValue());

		return new Resp(res.getDocumentElement().getElementsByTagName("row").item(1).getAttributes()
				.getNamedItem("SECID").getNodeValue(), last);
	}
}
