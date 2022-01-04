package net.tfowler.traktadapter.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Statistics{

	@JsonProperty("previousAiring")
	private String previousAiring;

	@JsonProperty("episodeCount")
	private int episodeCount;

	@JsonProperty("totalEpisodeCount")
	private int totalEpisodeCount;

	@JsonProperty("percentOfEpisodes")
	private double percentOfEpisodes;

	@JsonProperty("sizeOnDisk")
	private String sizeOnDisk;

	@JsonProperty("nextAiring")
	private String nextAiring;

	@JsonProperty("episodeFileCount")
	private int episodeFileCount;

	public String getPreviousAiring(){
		return previousAiring;
	}

	public int getEpisodeCount(){
		return episodeCount;
	}

	public int getTotalEpisodeCount(){
		return totalEpisodeCount;
	}

	public double getPercentOfEpisodes(){
		return percentOfEpisodes;
	}

	public String getSizeOnDisk(){
		return sizeOnDisk;
	}

	public String getNextAiring(){
		return nextAiring;
	}

	public int getEpisodeFileCount(){
		return episodeFileCount;
	}

	public void setPreviousAiring(String previousAiring) {
		this.previousAiring = previousAiring;
	}

	@Override
	public String toString() {
		return "Statistics{" +
				"previousAiring='" + previousAiring + '\'' +
				", episodeCount=" + episodeCount +
				", totalEpisodeCount=" + totalEpisodeCount +
				", percentOfEpisodes=" + percentOfEpisodes +
				", sizeOnDisk='" + sizeOnDisk + '\'' +
				", nextAiring='" + nextAiring + '\'' +
				", episodeFileCount=" + episodeFileCount +
				'}';
	}
}