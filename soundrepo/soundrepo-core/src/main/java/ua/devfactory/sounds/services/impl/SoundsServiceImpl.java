package ua.devfactory.sounds.services.impl;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import ua.devfactory.sounds.dao.SoundsDao;
import ua.devfactory.sounds.dao.SoundsDaoException;
import ua.devfactory.sounds.dto.Track;
import ua.devfactory.sounds.services.SoundsService;
import ua.devfactory.sounds.services.SoundsServiceException;
import ua.devfactory.users.dto.User;
import ua.devfactory.users.services.UsersService;

public class SoundsServiceImpl implements SoundsService{
	
	private Logger log = Logger.getLogger(getClass());
	private SoundsDao soundsDao;
	private UsersService usersService;
	
	public void setSoundsDao(SoundsDao soundsDao) {
		this.soundsDao = soundsDao;
	}
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	//TODO ready / handle exception in controller
	@Override
	public List<Track> recentTracks() throws SoundsServiceException{
		List<Track> recentTracks = null;
		try {
			log.debug("loading recent tracks form db");
			recentTracks = soundsDao.getRecentTracks();
			return recentTracks;
		} catch (SoundsDaoException e) {
			log.error("failed to get recent tracks", e);
			return recentTracks;
		}
	}
	//TODO ready / handle exception in controller
	@Override
	public Map<String, List<Track>> getUsersPlaylists(String nick) throws SoundsServiceException{
		log.debug("Searching for user with nick (" + nick + ") ");
		User existingUser = usersService.loadUserByNick(nick);
		if (existingUser != null) {
			try {
				log.debug("User found, getting playlist for user - " + nick);
				return soundsDao.loadUsersPlaylists(existingUser.getEmail());
			} catch (SoundsDaoException e) {
				log.error("Failed to load playlists for user (" + existingUser.getNick() + ")", e);
			}
		}
		log.info("User with nick (" + nick +") is not exist");
		return null;
	}
	
	@Override
	public void upload(MultipartFile file, String producer, String title, User user, String webRootDirPath) throws IOException {
		try {
			log.debug("Loading list of tracks for user (" + user.getNick() + ")");
			List<Track> usersListOfTracks = soundsDao.loadListOfTracksByNick(user.getNick());
			for (Track track : usersListOfTracks){
				if (producer.equals(track.getProducer()) && title.equals(track.getTitle())){
					//TODO exception
					throw new RuntimeException("You have already used this combination of producer + title");
				}
			}
			
			byte[] bytes = file.getBytes();
			BufferedOutputStream stream =
					new BufferedOutputStream(new FileOutputStream(new File(webRootDirPath + "resources/sounds/"
							+ producer + "-" + title + ".mp3")));
			log.debug("uploading file in filesystem");
			stream.write(bytes);
			stream.close();
			
			Track track = new Track();
			track.setProducer(producer);
			track.setTitle(title);
			track.setTag("testTag");
			track.setOpen(true);
			track.setUser(user);
			track.setLikes(777);
			log.debug("going to save in db");
			soundsDao.saveTrack(track);
		} catch (SoundsDaoException e) {
			log.error("Failed to upload file", e);
			//TODO exception
			throw new RuntimeException(e);
		}
	}
	//TODO ready / handle exception in controller + add in interface
	@Override
	public void addPlaylist(int userId, String title) throws SoundsServiceException{
		try {
			log.debug("Loading list of playlists for user (id-" + userId +")");
			List<String> listOfPlaylists = soundsDao.loadListOfPlaylists(userId);
			for(String playlistTitle : listOfPlaylists){
				if(title.equals(playlistTitle)){
					throw new SoundsServiceException("Playlist already exists");
				} 
			}
			log.debug("Saving playlist \"" + title + "\" for user (id-" + userId +")");
			soundsDao.savePlaylist(userId, title);
		} catch (SoundsDaoException e) {
			log.error("Failed to add playlist \"" + title + "\" for user (id-" + userId +")", e);
			throw new SoundsServiceException("Failed to add playlist", e);
		}
	}
	//TODO ready / handle exception in controller
	@Override
	public List<String> getListOfPlaylists(int id) throws SoundsServiceException{
		try {
			log.debug("Loading list of playlists for user (id-" + id +")");
			return soundsDao.loadListOfPlaylists(id);
		} catch (SoundsDaoException e) {
			log.error("Failed to get list of playlists", e);
			throw new SoundsServiceException("Failed to get list of playlists", e);
		}
	}
	//TODO ready / handle exception in controller
	@Override
	public void addTrackToPlaylist(String playlistTitle, int trackId, int userID) throws SoundsServiceException{
		try {
			log.debug("Saving track (id-" + trackId + ") to playlist " + playlistTitle + " by user (id-" + userID +")");
			soundsDao.saveTrackToPlaylist(playlistTitle, trackId, userID);
		} catch (SoundsDaoException e) {
			log.error("Failed to add track (id-" + trackId + ") to playlist " + playlistTitle + " by user (id-" + userID +")", e);
			throw new SoundsServiceException("Failed to add track to playlist", e);
		}
	}
	//TODO ready / handle exception in controller
	@Override
	public List<Track> getUsersTracks(String nick) throws SoundsServiceException{
		try {
			log.debug("Loading user's(nick-" + nick + ") tracks");
			return soundsDao.loadListOfTracksByNick(nick);
		} catch (SoundsDaoException e) {
			log.error("Failed to load user's(nick-" + nick + ") tracks", e);
			throw new SoundsServiceException("Failed to load user's(nick-" + nick + ") tracks", e);
		}
	}
}
