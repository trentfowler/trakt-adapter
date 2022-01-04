package net.tfowler.traktadapter.models;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
public class Season {

	@JsonProperty("seasonNumber")
	private int seasonNumber;

	@JsonProperty("monitored")
	private boolean monitored;

	@JsonProperty("statistics")
	private Statistics statistics;

	public int getSeasonNumber(){
		return seasonNumber;
	}

	public boolean isMonitored(){
		return monitored;
	}

	public void setSeasonNumber(int seasonNumber) {
		this.seasonNumber = seasonNumber;
	}

	public void setMonitored(boolean monitored) {
		this.monitored = monitored;
	}

	public Statistics getStatistics() {
		return statistics;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

	@Override
	public String toString() {
		return "Season{" +
				"seasonNumber=" + seasonNumber +
				", monitored=" + monitored +
				", statistics=" + statistics +
				'}';
	}
}