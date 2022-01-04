package net.tfowler.traktadapter.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Images {

	@JsonProperty("coverType")
	private String coverType;

	@JsonProperty("url")
	private String url;

	@JsonProperty("remoteUrl")
	private String remoteUrl;

	public String getCoverType(){
		return coverType;
	}

	public String getUrl(){
		return url;
	}

	public String getRemoteUrl() {
		return remoteUrl;
	}

	@Override
	public String toString() {
		return "Images{" +
				"coverType='" + coverType + '\'' +
				", url='" + url + '\'' +
				", remoteUrl='" + remoteUrl + '\'' +
				'}';
	}
}