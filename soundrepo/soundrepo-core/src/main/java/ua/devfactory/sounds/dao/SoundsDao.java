package ua.devfactory.sounds.dao;

import java.util.List;
import java.util.Map;

import ua.devfactory.sounds.dto.Track;

public interface SoundsDao {
	
	public List<Track> getRecentTracks() throws SoundsDaoException;
	
	public void saveTrack(Track track) throws SoundsDaoException;
	
	public Map<String, List<Track>> loadUsersPlaylists(String email) throws SoundsDaoException;
	
	public void savePlaylist(int userId, String title) throws SoundsDaoException;
	
	public List<String> loadListOfPlaylists(int id) throws SoundsDaoException;
	
	public void saveTrackToPlaylist(String playlistTitle, int trackId, int userID) throws SoundsDaoException;
	
	public List<Track> loadListOfTracksByNick(String nick) throws SoundsDaoException;
}
