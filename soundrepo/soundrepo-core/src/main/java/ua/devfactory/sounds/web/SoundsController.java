package ua.devfactory.sounds.web;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ua.devfactory.sounds.dto.Track;
import ua.devfactory.sounds.services.SoundsService;
import ua.devfactory.sounds.services.SoundsServiceException;
import ua.devfactory.users.dto.User;
@Controller
@ControllerAdvice
public class SoundsController {
	
	private Logger log = Logger.getLogger(getClass());
	private SoundsService soundsService;
	
	public void setSoundsService(SoundsService soundsService) {
		this.soundsService = soundsService;
	}
	//TODO web part
	@RequestMapping("explore")
	public ModelAndView explore(){
		try {
			List<Track> explorersList = soundsService.recentTracks();
			return new ModelAndView("explore", "tracks", explorersList);
		} catch (SoundsServiceException e) {
			//TODO test it in web, write web part
			log.error(e.getMessage());
			return new ModelAndView("explore", "errors", Arrays.asList(e.getMessage()));
		}
	}
	// TODO web part
	@RequestMapping("/{nick}/sets")
	public ModelAndView playlists(@PathVariable ("nick") String nick){
		try{
			Map<String, List<Track>> usersPlaylists = soundsService.getUsersPlaylists(nick);
			if (usersPlaylists != null){
				ModelAndView model = new ModelAndView("sets");
				model.addObject("usersPlaylists", usersPlaylists);
				model.addObject("nick", nick);
				return model;
			} else {
				//TODO it can be null , think about link where it can be refer or add form for exceptions in explore.jsp
				return new ModelAndView("redirect:/explore.do");
			}
		}catch (SoundsServiceException e){
			log.error(e.getMessage());
			return new ModelAndView("sets", "errors", Arrays.asList(e.getMessage()));
		}
	}
		
	@RequestMapping(value = "{nick}/sets/add_playlist", method=RequestMethod.GET)
	public ModelAndView showPlaylistForm(HttpSession session, @PathVariable ("nick") String nick){
		User loggedUser = (User)session.getAttribute("user");
		if(loggedUser != null && loggedUser.getNick().equals(nick)){
			return new ModelAndView("add_playlist");
		} else {
			return new ModelAndView("redirect:/login.do");
		}
	}
	
	@RequestMapping(value = "{nick}/sets/add_playlist", method=RequestMethod.POST)
	public ModelAndView addPlaylist(HttpSession session, 
									@RequestParam(value = "playlist_title", required = true) String playlistTitle,
									@PathVariable("nick") String nick){
		User loggedUser = (User) session.getAttribute("user");
		if (loggedUser.getNick().equals(nick)){
			try {
				soundsService.addPlaylist(loggedUser.getId(), playlistTitle);
				ModelAndView model = new ModelAndView("add_playlist_ok");
				model.addObject("playlist", playlistTitle);
				model.addObject("user", loggedUser);
				return model;
			} catch (Exception e) {
				//TODO 820
				System.out.println(e.getMessage());
				//TODO @!!!! link!!!
				return new ModelAndView("add_playlist", "errors", Arrays.asList(e.getMessage()));
			}
		} else {
			return new ModelAndView("redirect:/login.do");
		}
	}
	//TODO web part
	@RequestMapping(value = "/{nick}/tracks", method=RequestMethod.GET)
	public ModelAndView showProfile(@PathVariable ("nick") String nick){
		try{
			List<Track> usersTracks = soundsService.getUsersTracks(nick);
			ModelAndView model = new ModelAndView("tracks");
			model.addObject("usersTracks", usersTracks);
			model.addObject("nick", nick);
			return model;
		}catch (SoundsServiceException e){
			log.error(e.getMessage());
			return new ModelAndView("tracks", "errors", Arrays.asList(e.getMessage()));
		}
	}
	//TODO web part
	@RequestMapping(value = "/add_to_playlist", method=RequestMethod.GET)
	public ModelAndView showListOfPlaylists(HttpSession session, @RequestParam ("trackId") int trackId){
		User loggedUser = (User)session.getAttribute("user");
		if(loggedUser != null){
			try {
				List<String> listOfPlaylists = soundsService.getListOfPlaylists(loggedUser.getId());
				ModelAndView model = new ModelAndView("add_to_playlist");
				model.addObject("id", trackId);
				model.addObject("list", listOfPlaylists);
				//TODO CHECK NEW EL IN VIEW(ADD TO PLAYLIST) where won't be playlists listed
				return model;
			} catch (SoundsServiceException e){
				log.error(e.getMessage());
				return new ModelAndView("add_to_playlist", "errors", Arrays.asList(e.getMessage()));
			}
		} else {
			return new ModelAndView("redirect:/login.do");
		}
	}
	//TODO web part
	@RequestMapping(value = "/add_to_playlist", method=RequestMethod.POST)
	public ModelAndView processPlaylistsFilling(HttpSession session, @RequestParam("playlist") String playlistTitle, @RequestParam("trackId") int trackId){
		User loggedUser = (User)session.getAttribute("user");
		if(loggedUser != null){
			try {
				soundsService.addTrackToPlaylist(playlistTitle, trackId, loggedUser.getId());
				
				//TODO temp redirect
				return new ModelAndView("redirect:/" + loggedUser.getNick() + "/sets.do");
			} catch (SoundsServiceException e){
				log.debug(e.getMessage());
				return new ModelAndView("add_to_playlist", "errors", Arrays.asList(e.getMessage()));
			}
		} else {
			return new ModelAndView("redirect:/login.do");
		}
	}
	
	@RequestMapping(value = "upload", method=RequestMethod.GET)
	public ModelAndView showUploadForm(HttpSession session){
		User loggedUser = (User)session.getAttribute("user");
		if(loggedUser != null){	
			return new ModelAndView("upload");
		} else {
			return new ModelAndView("redirect:/login.do");
		}
	}
	
	@RequestMapping(value = "upload", method=RequestMethod.POST)
	public ModelAndView addNewTrack( HttpSession session,
			@RequestParam("mp3") MultipartFile file, 
			@RequestParam("producer") String producer, 
			@RequestParam("title") String title){
		User loggedUser = (User)session.getAttribute("user");
		if(loggedUser != null){	
			 try {
				 //TODO spring validation
				List<String> errors = new ArrayList<>();
				if (file.isEmpty()) {
					errors.add("You forgot to add track");
				}
				if (producer.length() == 0){
					errors.add("Field \"Producer\" should not be empty");
				}
				if (title.length() == 0){
					errors.add("Title should not be empty");
				}
				if (title.length() > 40){
					errors.add("Title length shouldn't be longer than 40 characters");
				}
				if (producer.length() > 20){
					errors.add("\"Producer\" fields length shouldn't be longer than 20 characters");
				}
				if(!file.getContentType().equals("audio/mp3")){
					errors.add("Track type can be only mp3");
				}
				if(errors.size() != 0){
					return new ModelAndView("upload", "errors", errors);
				}
					
				//TODO check same track
				
				soundsService.upload(file, producer, title, loggedUser, session.getServletContext().getRealPath("/"));
				return new ModelAndView("redirect:explore.do");
				}
				catch (Exception e) {
					return new ModelAndView("upload", "errors", Arrays.asList(e.getMessage()));
				}
			 	
		} else {
			return new ModelAndView("redirect:/login.do");
		}
		
	}
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ModelAndView handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
		return new ModelAndView("upload", "errors", Arrays.asList("You have exceeded upload limit (max 15mb)."));
	}
}
