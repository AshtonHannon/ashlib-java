package com.ahannon.ashlib.json;

import java.util.ArrayList;
import java.util.HashMap;

import com.ahannon.ashlib.json.exceptions.JSONFormattingException;

public class JSONObject {
	private final Character CURLY_OPEN_BRACKET = '{';
	private final Character CURLY_CLOSE_BRACKET = '}';
	private final Character SQUARE_OPEN_BRACKET = '[';
	private final Character SQUARE_CLOSE_BRACKET = ']';
	private final Character DOUBLE_QUOTE = '\"';
	private HashMap<String, Object> data = new HashMap<>();
	private char[] jsonString;
	private Integer position = 0;

	public JSONObject(String jsonString) {
		this.jsonString = jsonString.toCharArray();
		this.parse();
	}

	public JSONObject parse() {
		try {
			for (; this.position < this.jsonString.length; this.position++) {
				if (this.jsonString[this.position] == CURLY_OPEN_BRACKET) {
					this.data = this.parseMap();
				} else if (this.jsonString[this.position] == SQUARE_OPEN_BRACKET) {
					this.data.put("data", this.parseArray());
				}
			}
		} catch (JSONFormattingException e) {
			e.printStackTrace();
		}
		return this;
	}

	public Object get(String key) {
		return this.data.get(key);
	}

	public String toString() {
		return data.toString();
	}

	private void printCurrentCharacter() {
		System.out.println(this.jsonString[this.position]);
	}

	/* Parse strings from the jsonString */
	private String parseString() throws JSONFormattingException {
		/* Throw error if first character is not single quote */
		if (this.jsonString[this.position] != DOUBLE_QUOTE)
			throw new JSONFormattingException("First character is not single quote for parseString");

		StringBuilder str = new StringBuilder();
		Integer singleQuoteCount = 0;

		/* Parse string from single quote to single quote */
		for (; singleQuoteCount >= 0 && singleQuoteCount < 2; this.position++) {
			if (this.jsonString[this.position] == DOUBLE_QUOTE) {
				singleQuoteCount++;
			} else {
				str.append(this.jsonString[this.position]);
			}
		}

		return str.toString();
	}

	/* Parse maps from the jsonString */
	private HashMap<String, Object> parseMap() throws JSONFormattingException {
		/* Throw error if first character is not CURLY_OPEN_BRACKET */
		HashMap<String, Object> map = new HashMap<>();
		if (this.jsonString[this.position] != CURLY_OPEN_BRACKET)
			throw new JSONFormattingException("First character is not CURLY_OPEN_BRACKET for parseMap");

		/* Advance past CURLY_OPEN_BRACKET */
		this.position++;

		/* Pase the key string */
		String keyString = this.parseString();

		/* Advance until acceptable character */
		while (this.jsonString[this.position] != CURLY_OPEN_BRACKET
				&& this.jsonString[this.position] != SQUARE_OPEN_BRACKET
				&& this.jsonString[this.position] != DOUBLE_QUOTE) {
			this.position++;
		}

		/* Now parse value depending on its type */
		if (this.jsonString[this.position] == DOUBLE_QUOTE) {
			map.put(keyString, this.parseString());
		} else if (this.jsonString[this.position] == SQUARE_OPEN_BRACKET) {
			map.put(keyString, this.parseArray());
		}
		System.out.println(this.jsonString);
		/* Throw error if last character is not CURLY_CLOSE_BRACKET */
		if (this.jsonString[this.position] != CURLY_CLOSE_BRACKET)
			throw new JSONFormattingException("Last chracter is not CURLY_CLOSE_BRACKET for parseMap");

		return map;
	}

	/* Parse arrays from the jsonString */
	private ArrayList<Object> parseArray() throws JSONFormattingException {
		ArrayList<Object> ret = new ArrayList<>();

		/* Throw error if first character is not SQUARE_OPEN_BRACKET */
		if (this.jsonString[this.position] != SQUARE_OPEN_BRACKET)
			throw new JSONFormattingException("First character is not SQUARE_OPEN_BRACKET for parseMap");

		/* Advance past SQUARE_OPEN_BRACKET */
		this.position++;

		while (this.jsonString[this.position] != SQUARE_CLOSE_BRACKET) {
			/* Now parse value depending on its type */
			if (this.jsonString[this.position] == DOUBLE_QUOTE) {
				ret.add(this.parseString());
			} else if (this.jsonString[this.position] == CURLY_OPEN_BRACKET) {
				ret.add(this.parseMap());
			}
			/* Advance until another acceptable character */
			while (this.jsonString[this.position] != CURLY_OPEN_BRACKET
					&& this.jsonString[this.position] != SQUARE_OPEN_BRACKET
					&& this.jsonString[this.position] != DOUBLE_QUOTE
					&& this.jsonString[this.position] != SQUARE_CLOSE_BRACKET) {
				this.position++;
			}
		}
		/* Move one more past SQUARE_CLOSE_BRACKET */
		this.position++;

		return ret;
	}
}
