package com.newton.moex.access.controller;

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

import com.newton.moex.access.pojo.Resp;

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
