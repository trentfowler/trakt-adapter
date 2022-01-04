package net.tfowler.traktadapter.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlternateTitles {

	@JsonProperty("title")
	private String title;

	@JsonProperty("seasonNumber")
	private int seasonNumber;

	@JsonProperty("sceneSeasonNumber")
	private int sceneSeasonNumber;

	public int getSeasonNumber(){
		return seasonNumber;
	}

	public String getTitle(){
		return title;
	}

	public int getSceneSeasonNumber() {
		return sceneSeasonNumber;
	}

	public void setSceneSeasonNumber(int sceneSeasonNumber) {
		this.sceneSeasonNumber = sceneSeasonNumber;
	}

	@Override
	public String toString() {
		return "AlternateTitles{" +
				"title='" + title + '\'' +
				", seasonNumber=" + seasonNumber +
				", sceneSeasonNumber=" + sceneSeasonNumber +
				'}';
	}
}