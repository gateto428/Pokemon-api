package pokeApi.models;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Type {
	//Class Fields
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private long idtype;
	private String type;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "pokemon_type", 
	joinColumns = @JoinColumn(name = "idtype"),
	inverseJoinColumns  = @JoinColumn(name = "idPokemon"))
	@JsonIgnore
	private Collection<Pokemon> pokemons =  new ArrayList<>();
	
	public Type() {
	}

	/**
	 * 
	 * @param type
	 */

	public Type(String type) {
		this.type = type;
	}

	//Getters And Setters

	public long getIdtype() {
		return idtype;
	}

	public void setIdtype(long idtype) {
		this.idtype = idtype;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

	public Collection<Pokemon> getPokemons() {
		return pokemons;
	}

	public void setPokemons(Collection<Pokemon> pokemons) {
		this.pokemons = pokemons;
	}

	@Override
	public String toString() {
		return "{idtype:" + idtype + ", type:" + type + "}";
	}
}
