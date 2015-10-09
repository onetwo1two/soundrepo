package ua.devfactory.sounds.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import ua.devfactory.sounds.dto.Track;
import ua.devfactory.users.dto.User;


public interface SoundsService {
	
	public List<Track> recentTracks() throws SoundsServiceException;
	
	public Map<String, List<Track>> getUsersPlaylists(String nick) throws SoundsServiceException;
	
	public void upload(MultipartFile file, String producer, String title, User user, String webRootDirPath) throws IOException;
	
	public void addPlaylist(int userId, String title) throws Exception;
	
	public List<String> getListOfPlaylists(int id) throws SoundsServiceException;
	
	public void addTrackToPlaylist(String playlistTitle, int trackId, int userID) throws SoundsServiceException;
	
	public List<Track> getUsersTracks(String nick) throws SoundsServiceException;
}
