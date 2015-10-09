package ua.devfactory.sounds.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.devfactory.sounds.dao.SoundsDao;
import ua.devfactory.sounds.dao.SoundsDaoException;
import ua.devfactory.sounds.dto.Track;
import ua.devfactory.users.dto.User;

public class SoundsDaoImpl implements SoundsDao{
	
	private DataSource dataSource;
	private Logger log = Logger.getLogger(getClass());
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Track> getRecentTracks() throws SoundsDaoException{
		
		Connection conn = null;
		List<Track> trackList = new ArrayList<>();
		try {
			conn = dataSource.getConnection();
			PreparedStatement statement = 
					conn.prepareStatement("SELECT tracks.id, producer, title, tag, public, user_id, likes , nick "
											+ "from tracks "
											+ "LEFT JOIN users ON users.id = tracks.user_id "
											+ "order by date desc");
			ResultSet result = statement.executeQuery();
			while (result.next()){
				Track track = new Track();
				track.setId(result.getInt("tracks.id"));
				track.setProducer(result.getString("producer"));
				track.setTitle(result.getString("title"));
				track.setTag(result.getString("tag"));
				track.setOpen(result.getInt("public") != 0 ? true : false );
				User user = new User();
				user.setId(result.getInt("user_id"));
				user.setNick(result.getString("nick"));
				track.setUser(user);
				track.setLikes(result.getInt("likes"));
				trackList.add(track);
			}
			return trackList;

		} catch (SQLException e) {
			log.error("cannot load recent tracks", e);
			throw new SoundsDaoException("cannot get recent tracks", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					log.error("cannot close connection", e);
				}
			}
		}
		
	}

	@Override
	public void saveTrack(Track track) throws SoundsDaoException{
		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("insert into tracks (producer, title, tag, public, user_id, likes)"
					+ " values (?,?,?,?,?,?)");
			ps.setString(1, track.getProducer());
			ps.setString(2, track.getTitle());
			ps.setString(3, track.getTag());
			ps.setByte(4, track.isOpen() != false ? (byte) 1 :(byte) 0);
			ps.setInt(5, track.getUser().getId());
			ps.setInt(6, track.getLikes());
			ps.executeUpdate();
			log.trace("SAVED TRACK: " + track.getProducer() + "-" + track.getTitle());
		} catch (SQLException e) {
			log.error("cannot save track to DB", e);
			throw new SoundsDaoException("cannot save track to DB", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					log.error("cannot close connection", e);
				}
			}
		}
	}
	@Override
	public Map<String, List<Track>> loadUsersPlaylists(String email) throws SoundsDaoException{
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement statement = 
					conn.prepareStatement("SELECT playlists.title as playlist_title, tracks.title as title, producer, tag, likes "
							+ "FROM tracks "
							+ "LEFT JOIN playlists_filling ON tracks.id = playlists_filling.track_id "
							+ "LEFT JOIN playlists ON playlists_filling.playlist_id = playlists.id "
							+ "LEFT JOIN users ON playlists.user_id = users.id "
							+ "WHERE users.email = ? "
							+ "ORDER BY playlists.title");
			statement.setString(1, email);
			Map<String, List<Track>> playlists = new LinkedHashMap<>();
			ResultSet result = statement.executeQuery();
			while (result.next()){
				String playlistName = result.getString("playlist_title");
				Track track = new Track();
				track.setProducer(result.getString("producer"));
				track.setTitle(result.getString("title"));
				track.setTag(result.getString("tag"));
				track.setLikes(result.getInt("likes"));
				if(playlists.containsKey(playlistName)){
					List<Track> trackList = playlists.get(playlistName);
					trackList.add(track);
				} else {
					List<Track> trackList = new ArrayList<>();
					trackList.add(track);
					playlists.put(playlistName,trackList);
				}
				
			}
			return playlists;

		} catch (SQLException e) {
			log.error("cannot load users playlists from DB", e);
			throw new SoundsDaoException("cannot load users playlists from DB", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					log.error("cannot close connection", e);
				}
			}
		}
	}

	@Override
	public void savePlaylist(int userId, String title) throws SoundsDaoException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("insert into playlists (title, user_id) values (?,?)");
			ps.setString(1, title);
			ps.setInt(2, userId);
			ps.executeUpdate();
			log.trace("SAVED PLAYLIST: " + title + " BY USER-id: " + userId);
		} catch (SQLException e) {
			log.error("cannot save playlist", e);
			throw new SoundsDaoException("cannot save playlist", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					log.error("cannot close connection", e);
				}
			}
		}
	}

	@Override
	public List<String> loadListOfPlaylists(int id) throws SoundsDaoException{
		Connection conn = null;
		List<String> listOfPlaylists = new ArrayList<>();
		try {
			conn = dataSource.getConnection();
			PreparedStatement statement = 
					conn.prepareStatement("select title "
										+ "from playlists "
										+ "left join users "
										+ "on playlists.user_id = users.id "
										+ "where users.id = ?");
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			while (result.next()){
				String platlistName = result.getString("title");
				listOfPlaylists.add(platlistName);
			}
			return listOfPlaylists;

		} catch (SQLException e) {
			log.error("cannot load list of playlists", e);
			throw new SoundsDaoException("cannot load list of playlists", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					log.error("cannot close connection", e);
				}
			}
		}
	}

	@Override
	public void saveTrackToPlaylist(String playlistTitle, int trackId, int userID) throws SoundsDaoException{
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO playlists_filling (playlist_id, track_id) "
														+ "VALUES ((SELECT playlists.id AS playlist_id "
														+ "FROM playlists LEFT JOIN users ON users.id = playlists.user_id "
														+ "WHERE playlists.title = ? "
														+ "AND users.id = ?) , ?)");
			ps.setString(1, playlistTitle);
			ps.setInt(2, userID);
			ps.setInt(3, trackId);
			ps.executeUpdate();
			log.trace("SAVED TRACK(ID): " + trackId + " to playlist " + playlistTitle);
		} catch (SQLException e) {
			log.error("cannot save track to playlist", e);
			throw new SoundsDaoException("cannot save track to playlist", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					log.error("cannot close connection", e);
				}
			}
		}
	}

	@Override
	public List<Track> loadListOfTracksByNick(String nick) throws SoundsDaoException{
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select tracks.id, producer, title, tag, public, likes "
														+ "from tracks "
														+ "left join users on user_id = users.id "
														+ "where users.nick = ?");
			ps.setString(1, nick);
			ResultSet result = ps.executeQuery();
			List<Track> tracks = new ArrayList<>();
			while (result.next()){
				Track track = new Track();
				track.setId(result.getInt("tracks.id"));
				track.setProducer(result.getString("producer"));
				track.setTitle(result.getString("title"));
				track.setTag(result.getString("tag"));
				track.setOpen(result.getByte("public") != 1 ? false : true);
				track.setLikes(result.getInt("likes"));
				tracks.add(track);
			}
			return tracks;
		} catch (SQLException e) {
			log.error("cannot load list of tracks", e);
			throw new SoundsDaoException("cannot load list of tracks", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					log.error("cannot close connection", e);
				}
			}
		}
	}
}
