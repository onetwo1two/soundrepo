package ua.devfactory.sounds.dto;

import ua.devfactory.users.dto.User;

public class Track {
	//TODO hashcode , to string
	private int id;
	private String producer;
	private String title;
	private String tag;
	private boolean open;
	private User user;
	private int likes;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	@Override
	public String toString() {
		return "Track [id=" + id + ", producer=" + producer + ", title="
				+ title + ", tag=" + tag + ", open=" + open + ", user=" + user
				+ ", likes=" + likes + "]";
	}
	
	
	
}
