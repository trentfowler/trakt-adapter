package net.tfowler.traktadapter.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
public class Show {

	@JsonProperty("tvMazeId")
	private int tvMazeId;

	@JsonProperty("previousAiring")
	private String previousAiring;

	@JsonProperty("alternateTitles")
	private List<AlternateTitles> alternateTitles;

	@JsonProperty("episodeCount")
	private int episodeCount;

	@JsonProperty("episodeFileCount")
	private int episodeFileCount;

	@JsonProperty("year")
	private int year;

	@JsonProperty("added")
	private String added;

	@JsonProperty("imdbId")
	private String imdbId;

	@JsonProperty("remotePoster")
	private String remotePoster;

	@JsonProperty("title")
	private String title;

	@JsonProperty("path")
	private String path;

	@JsonProperty("rootFolderPath")
	private String rootFolderPath;

	@JsonProperty("sizeOnDisk")
	private long sizeOnDisk;

	@JsonProperty("network")
	private String network;

	@JsonProperty("monitored")
	private boolean monitored;

	@JsonProperty("cleanTitle")
	private String cleanTitle;

	@JsonProperty("titleSlug")
	private String titleSlug;

	@JsonProperty("seasonCount")
	private int seasonCount;

	@JsonProperty("seriesType")
	private String seriesType;

	@JsonProperty("genres")
	private List<String> genres;

	@JsonProperty("ratings")
	private Ratings ratings;

	@JsonProperty("qualityProfileId")
	private int qualityProfileId;

	@JsonProperty("id")
	private int id;

	@JsonProperty("useSceneNumbering")
	private boolean useSceneNumbering;

	@JsonProperty("seasonFolder")
	private boolean seasonFolder;

	@JsonProperty("airTime")
	private String airTime;

	@JsonProperty("overview")
	private String overview;

	@JsonProperty("images")
	private List<Images> images;

	@JsonProperty("seasons")
	private List<Season> seasons;

	@JsonProperty("tvdbId")
	private int tvdbId;

	@JsonProperty("firstAired")
	private String firstAired;

	@JsonProperty("lastInfoSync")
	private String lastInfoSync;

	@JsonProperty("totalEpisodeCount")
	private int totalEpisodeCount;

	@JsonProperty("runtime")
	private int runtime;

	@JsonProperty("certification")
	private String certification;

	@JsonProperty("tags")
	private List<Object> tags;

	@JsonProperty("sortTitle")
	private String sortTitle;

	@JsonProperty("profileId")
	private int profileId;

	@JsonProperty("tvRageId")
	private int tvRageId;

	@JsonProperty("nextAiring")
	private String nextAiring;

	@JsonProperty("addOptions")
	private AddOptions addOptions;

	@JsonProperty("status")
	private String status;

	@JsonProperty("languageProfileId")
	private int languageProfileId;

	public int getLanguageProfileId() {
		return languageProfileId;
	}

	public void setLanguageProfileId(int languageProfileId) {
		this.languageProfileId = languageProfileId;
	}

	public int getTvMazeId(){
		return tvMazeId;
	}

	public int getYear(){
		return year;
	}

	public String getAdded(){
		return added;
	}

	public String getImdbId(){
		return imdbId;
	}

	public String getRemotePoster(){
		return remotePoster;
	}

	public String getTitle(){
		return title;
	}

	public String getNetwork(){
		return network;
	}

	public boolean isMonitored(){
		return monitored;
	}

	public String getCleanTitle(){
		return cleanTitle;
	}

	public String getTitleSlug(){
		return titleSlug;
	}

	public int getSeasonCount(){
		return seasonCount;
	}

	public String getSeriesType(){
		return seriesType;
	}

	public List<String> getGenres(){
		return genres;
	}

	public Ratings getRatings(){
		return ratings;
	}

	public int getQualityProfileId(){
		return qualityProfileId;
	}

	public boolean isUseSceneNumbering(){
		return useSceneNumbering;
	}

	public boolean isSeasonFolder(){
		return seasonFolder;
	}

	public String getAirTime(){
		return airTime;
	}

	public String getOverview(){
		return overview;
	}

	public List<Images> getImages(){
		return images;
	}

	public List<Season> getSeasons(){
		return seasons;
	}

	public int getTvdbId(){
		return tvdbId;
	}

	public String getFirstAired(){
		return firstAired;
	}

	public int getRuntime(){
		return runtime;
	}

	public String getCertification(){
		return certification;
	}

	public List<Object> getTags(){
		return tags;
	}

	public String getSortTitle(){
		return sortTitle;
	}

	public int getProfileId(){
		return profileId;
	}

	public int getTvRageId(){
		return tvRageId;
	}

	public String getStatus(){
		return status;
	}

	public void setTvMazeId(int tvMazeId) {
		this.tvMazeId = tvMazeId;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setAdded(String added) {
		this.added = added;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public void setRemotePoster(String remotePoster) {
		this.remotePoster = remotePoster;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public void setMonitored(boolean monitored) {
		this.monitored = monitored;
	}

	public void setCleanTitle(String cleanTitle) {
		this.cleanTitle = cleanTitle;
	}

	public void setTitleSlug(String titleSlug) {
		this.titleSlug = titleSlug;
	}

	public void setSeasonCount(int seasonCount) {
		this.seasonCount = seasonCount;
	}

	public void setSeriesType(String seriesType) {
		this.seriesType = seriesType;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	public void setRatings(Ratings ratings) {
		this.ratings = ratings;
	}

	public void setQualityProfileId(int qualityProfileId) {
		this.qualityProfileId = qualityProfileId;
	}

	public void setUseSceneNumbering(boolean useSceneNumbering) {
		this.useSceneNumbering = useSceneNumbering;
	}

	public void setSeasonFolder(boolean seasonFolder) {
		this.seasonFolder = seasonFolder;
	}

	public void setAirTime(String airTime) {
		this.airTime = airTime;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public void setImages(List<Images> images) {
		this.images = images;
	}

	public void setSeasons(List<Season> seasons) {
		this.seasons = seasons;
	}

	public void setTvdbId(int tvdbId) {
		this.tvdbId = tvdbId;
	}

	public void setFirstAired(String firstAired) {
		this.firstAired = firstAired;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}

	public void setTags(List<Object> tags) {
		this.tags = tags;
	}

	public void setSortTitle(String sortTitle) {
		this.sortTitle = sortTitle;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	public void setTvRageId(int tvRageId) {
		this.tvRageId = tvRageId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRootFolderPath() {
		return rootFolderPath;
	}

	public void setRootFolderPath(String rootFolderPath) {
		this.rootFolderPath = rootFolderPath;
	}

	public String getPreviousAiring() {
		return previousAiring;
	}

	public void setPreviousAiring(String previousAiring) {
		this.previousAiring = previousAiring;
	}

	public List<AlternateTitles> getAlternateTitles() {
		return alternateTitles;
	}

	public void setAlternateTitles(List<AlternateTitles> alternateTitles) {
		this.alternateTitles = alternateTitles;
	}

	public int getEpisodeCount() {
		return episodeCount;
	}

	public void setEpisodeCount(int episodeCount) {
		this.episodeCount = episodeCount;
	}

	public long getSizeOnDisk() {
		return sizeOnDisk;
	}

	public void setSizeOnDisk(long sizeOnDisk) {
		this.sizeOnDisk = sizeOnDisk;
	}

	public int getTotalEpisodeCount() {
		return totalEpisodeCount;
	}

	public void setTotalEpisodeCount(int totalEpisodeCount) {
		this.totalEpisodeCount = totalEpisodeCount;
	}

	public String getNextAiring() {
		return nextAiring;
	}

	public void setNextAiring(String nextAiring) {
		this.nextAiring = nextAiring;
	}

	public int getEpisodeFileCount() {
		return episodeFileCount;
	}

	public void setEpisodeFileCount(int episodeFileCount) {
		this.episodeFileCount = episodeFileCount;
	}

	public String getLastInfoSync() {
		return lastInfoSync;
	}

	public void setLastInfoSync(String lastInfoSync) {
		this.lastInfoSync = lastInfoSync;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AddOptions getAddOptions() {
		return addOptions;
	}

	public void setAddOptions(AddOptions addOptions) {
		this.addOptions = addOptions;
	}

	@Override
	public String toString() {
		return "Show{" +
				"tvMazeId=" + tvMazeId +
				", previousAiring='" + previousAiring + '\'' +
				", alternateTitles=" + alternateTitles +
				", episodeCount=" + episodeCount +
				", episodeFileCount=" + episodeFileCount +
				", year=" + year +
				", added='" + added + '\'' +
				", imdbId='" + imdbId + '\'' +
				", remotePoster='" + remotePoster + '\'' +
				", title='" + title + '\'' +
				", path='" + path + '\'' +
				", rootFolderPath='" + rootFolderPath + '\'' +
				", sizeOnDisk=" + sizeOnDisk +
				", network='" + network + '\'' +
				", monitored=" + monitored +
				", cleanTitle='" + cleanTitle + '\'' +
				", titleSlug='" + titleSlug + '\'' +
				", seasonCount=" + seasonCount +
				", seriesType='" + seriesType + '\'' +
				", genres=" + genres +
				", ratings=" + ratings +
				", qualityProfileId=" + qualityProfileId +
				", id=" + id +
				", useSceneNumbering=" + useSceneNumbering +
				", seasonFolder=" + seasonFolder +
				", airTime='" + airTime + '\'' +
				", overview='" + overview + '\'' +
				", images=" + images +
				", seasons=" + seasons +
				", tvdbId=" + tvdbId +
				", firstAired='" + firstAired + '\'' +
				", lastInfoSync='" + lastInfoSync + '\'' +
				", totalEpisodeCount=" + totalEpisodeCount +
				", runtime=" + runtime +
				", certification='" + certification + '\'' +
				", tags=" + tags +
				", sortTitle='" + sortTitle + '\'' +
				", profileId=" + profileId +
				", tvRageId=" + tvRageId +
				", nextAiring='" + nextAiring + '\'' +
				", addOptions=" + addOptions +
				", status='" + status + '\'' +
				", languageProfileId=" + languageProfileId +
				'}';
	}
}