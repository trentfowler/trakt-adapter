package net.tfowler.traktadapter.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ratings{

	@JsonProperty("votes")
	private int votes;

	@JsonProperty("value")
	private double value;

	public int getVotes(){
		return votes;
	}

	public double getValue(){
		return value;
	}

	@Override
	public String toString() {
		return "Ratings{" +
				"votes=" + votes +
				", value=" + value +
				'}';
	}
}