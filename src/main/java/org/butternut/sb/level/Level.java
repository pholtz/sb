package org.butternut.sb.level;

import java.util.List;

public class Level
{
	private String name;
	private List<String> music;
	private List<Long> character;
	private List<List<Object>> notes;
	private List<List<Long>> coldpops;
	private List<List<Long>> suits;
	private List<Long> door;
	private List<List<Long>> blocks;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getMusic() {
		return music;
	}
	public void setMusic(List<String> music) {
		this.music = music;
	}
	public List<Long> getCharacter() {
		return character;
	}
	public void setCharacter(List<Long> character) {
		this.character = character;
	}
	public List<List<Object>> getNotes() {
		return notes;
	}
	public void setNotes(List<List<Object>> notes) {
		this.notes = notes;
	}
	public List<List<Long>> getColdpops() {
		return coldpops;
	}
	public void setColdpops(List<List<Long>> coldpops) {
		this.coldpops = coldpops;
	}
	public List<List<Long>> getSuits() {
		return suits;
	}
	public void setSuits(List<List<Long>> suits) {
		this.suits = suits;
	}
	public List<Long> getDoor() {
		return door;
	}
	public void setDoor(List<Long> door) {
		this.door = door;
	}
	public List<List<Long>> getBlocks() {
		return blocks;
	}
	public void setBlocks(List<List<Long>> blocks) {
		this.blocks = blocks;
	}
}
