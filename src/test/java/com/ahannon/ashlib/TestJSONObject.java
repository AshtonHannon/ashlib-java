package com.ahannon.ashlib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.ahannon.ashlib.json.JSONObject;

public class TestJSONObject {
	@Test
	public void simpleMap() {
		String jsonString = "{\"Test\": \"One\"}";
		JSONObject json = new JSONObject(jsonString);

		/* Assert that after parsing the string, key "Test" = value "One" */
		assertEquals(json.get("Test"), "One");
	}

	@Test
	public void arrayObject() {
		String jsonString = "{\"array\":[\"index1\",\"index2\"]}";
		JSONObject json = new JSONObject(jsonString);
		String expected[] = { "index1", "index2" };

		/* Assert that after parsing the string, key "Test" = value "One" */
		System.out.println(json.get("array"));
		System.out.println(expected);
		assertTrue(expected.equals(json.get("array")));
	}
}
