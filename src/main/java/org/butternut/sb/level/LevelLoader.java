package org.butternut.sb.level;

import java.io.IOException;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LevelLoader
{
	private final ObjectMapper objectMapper;
	
	public LevelLoader() {
		this.objectMapper = new ObjectMapper();
	}
	
	public Level load(String filename) {
		Level level = null;
		try {
			level = this.objectMapper.readValue(Paths.get("src/main/resources/", filename).toFile(), Level.class);
		} catch (IOException ioException) {
			throw new RuntimeException("Error while loading level -> " + filename, ioException);
		}
		return level;
	}
}
