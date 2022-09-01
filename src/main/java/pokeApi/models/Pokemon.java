package pokeApi.models;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Pokemon {
	//Class Fields
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private long idPokemon;
	private String name;
	private String img;
	private int hp;
	private int attack;
	private int defense;
	private int speed;

	@ManyToMany(mappedBy = "pokemons")
	private Collection<Type> types = new ArrayList<>();
	
	
	public Pokemon() {
		
	}
	/**
	 * Constructor Class
	 * @param name
	 * @param img
	 * @param hp
	 * @param attack
	 * @param defense
	 * @param speed
	 */

	public Pokemon(String name, String img, int hp, int attack, int defense, int speed) {
		this.name = name;
		this.img = img;
		this.hp = hp;
		this.attack = attack;
		this.defense = defense;
		this.speed = speed;
	}

	//Getters And Setters
	public long getIdPokemon() {
		return idPokemon;
	}

	public void setIdPokemon(long idPokemon) {
		this.idPokemon = idPokemon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	public int getDefense() {
		return defense;
	}
	
	public void setDefense(int defense) {
		this.defense = defense;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public Collection<Type> getTypes() {
		return types;
	}
	public void setTypes(Collection<Type> types) {
		this.types = types;
	}
	@Override
	public String toString() {
		String response = "{idPokemon:" + idPokemon + ", name:" + name + ", img:" + img + ", hp:" + hp + ", attack:"
				+ attack + ", defense:" + defense + ", speed:" + speed +", types:"+ types +"}";
		return response;
	}
}
