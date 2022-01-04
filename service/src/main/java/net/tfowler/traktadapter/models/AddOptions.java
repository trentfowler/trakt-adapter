package net.tfowler.traktadapter.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddOptions{

	@JsonProperty("ignoreEpisodesWithFiles")
	private boolean ignoreEpisodesWithFiles;

	@JsonProperty("ignoreEpisodesWithoutFiles")
	private boolean ignoreEpisodesWithoutFiles;

	@JsonProperty("searchForMissingEpisodes")
	private boolean searchForMissingEpisodes;

	public boolean isIgnoreEpisodesWithFiles(){
		return ignoreEpisodesWithFiles;
	}

	public boolean isIgnoreEpisodesWithoutFiles(){
		return ignoreEpisodesWithoutFiles;
	}

	public boolean isSearchForMissingEpisodes(){
		return searchForMissingEpisodes;
	}

	@Override
	public String toString() {
		return "AddOptions{" +
				"ignoreEpisodesWithFiles=" + ignoreEpisodesWithFiles +
				", ignoreEpisodesWithoutFiles=" + ignoreEpisodesWithoutFiles +
				", searchForMissingEpisodes=" + searchForMissingEpisodes +
				'}';
	}
}