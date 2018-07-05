package de.fh_dortmund.swt.doppelkopf;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
public class Player implements Serializable{

	private static final long serialVersionUID = -5634458240601433318L;
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String password;
	private int victoryPoints;
	private LocalDateTime date;
	public Player()
	{
		
	}
	public Player(String name, String password) {
		this.name = name;
		this.password = password;
		this.victoryPoints = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//TODO Hashing Magic
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getVictoryPoints() {
		return victoryPoints;
	}
	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}
	public LocalDateTime getDate()
	{
		return this.date;
	}
	public void setDate(LocalDateTime now) {
		// TODO Auto-generated method stub
		this.date=now;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public int getId()
	{
		return this.id;
	}
}
