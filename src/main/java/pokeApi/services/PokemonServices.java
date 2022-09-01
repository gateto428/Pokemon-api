package pokeApi.services;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import pokeApi.models.Pokemon;
import pokeApi.models.Type;
import pokeApi.repository.PokemonRepository;
import pokeApi.repository.TypePokemonRepository;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import pokeApi.services.PokemonServices;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;



@Service
public class PokemonServices {

	@Autowired
	private TypePokemonRepository typePokemonRepository;
	@Autowired
	private PokemonRepository pokemonRepository;

	@PostConstruct
	public void init() {
		try {
			List<Type> list = typeList("https://pokeapi.co/api/v2/type");
			typePokemonRepository.saveAll(list);
			for (int i = 1; i <= 500; i++ ) {
				 pokemonList("https://pokeapi.co/api/v2/pokemon/" + i);
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private  List<Type> typeList(String sURL) throws IOException {
		URL url = new URL(sURL);
		URLConnection request = url.openConnection();
		request.connect();
		JsonParser jp = new JsonParser();
		JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); 
		JsonObject rootobj = root.getAsJsonObject(); 
		JsonArray typesPokemons = rootobj.get("results").getAsJsonArray();
		List<Type> types = new ArrayList<>();
		for(int i = 0; i<typesPokemons.size(); i++) {
			types.add(new Type((typesPokemons.get(i).getAsJsonObject().get("name").getAsString())));
		}
		return types;
	}
	
	private  void pokemonList(String sURL) throws IOException {
		URL url = new URL(sURL);
		URLConnection request = url.openConnection();
		request.connect();
		JsonParser jp = new JsonParser(); 
		JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); 
		JsonObject rootobj = root.getAsJsonObject();
		String name = rootobj.get("name").getAsString();
		String img =  rootobj.get("sprites").getAsJsonObject().get("other").getAsJsonObject().get("dream_world").getAsJsonObject().get("front_default").getAsString();
		int hp = rootobj.get("stats").getAsJsonArray().get(0).getAsJsonObject().get("base_stat").getAsInt();
		int attack= rootobj.get("stats").getAsJsonArray().get(1).getAsJsonObject().get("base_stat").getAsInt();
		int defense= rootobj.get("stats").getAsJsonArray().get(2).getAsJsonObject().get("base_stat").getAsInt();
		int speed= rootobj.get("stats").getAsJsonArray().get(5).getAsJsonObject().get("base_stat").getAsInt();
		JsonArray typesPokemon   = rootobj.get("types").getAsJsonArray();
		Pokemon pokemon = new Pokemon(name, img, hp, attack, defense, speed);
		Collection<Type> typeIds = new ArrayList<>();
		for(int i = 0; i< typesPokemon.size(); i++) {
			String typeP = typesPokemon.get(i).getAsJsonObject().get("type").getAsJsonObject().get("name").getAsString();
			typeIds.add(typePokemonRepository.findByTypeIgnoreCaseContaining(typeP).get(0));
		}
		
		pokemon.getTypes().addAll(typeIds);
		pokemon = pokemonRepository.save(pokemon);
		for (Type type : typeIds) {
			type.getPokemons().add(pokemon);
			typePokemonRepository.save(type);
		}
		
	
	}
	

	public Type CreateTypes(Type tp) {
		return typePokemonRepository.save(tp);
	}
	
	public String createPokemon(Pokemon pokemon) {
		Pokemon newpokemon  = new Pokemon();
		newpokemon.setName(pokemon.getName());
		newpokemon.setImg(pokemon.getImg());
		newpokemon.setHp(pokemon.getHp());
		newpokemon.setAttack(pokemon.getAttack());
		newpokemon.setDefense(pokemon.getDefense());
		newpokemon.setSpeed(pokemon.getSpeed());
		newpokemon.getTypes()
                .addAll(pokemon
                        .getTypes()
                        .stream()
                        .map(v -> {
                            Type vv = typePokemonRepository.findByTypeId(v.getIdtype());
                            vv.getPokemons().add(newpokemon);
                            return vv;
                        }).collect(Collectors.toList()));
         pokemonRepository.save(newpokemon);
		return "Saved!";
	}

	public List<Pokemon> getPokemons(int offset, int limit){
		return pokemonRepository.findPokemons(offset, limit);
	}
	
	public int countPokemons() {
		return pokemonRepository.countPokemons();
	}

	public void  delete(Pokemon pokemon) {
		pokemonRepository.delete(pokemon);
	}

	public Optional<Pokemon> findId(long id) {
		return pokemonRepository.findById(id);
	}
}
