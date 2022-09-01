package pokeApi.rest;

import java.net.URI;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import pokeApi.models.Pokemon;
import pokeApi.models.Type;
import pokeApi.services.PokemonServices;

@RestController
@RequestMapping("api/pokemon")
public class PokemonREST {

	@Autowired
	private PokemonServices pokemonServices;

	@PostMapping
	private ResponseEntity<String> savePokemon(@RequestBody Pokemon pokemon){
		String temp = pokemonServices.createPokemon(pokemon);
		try {
			return ResponseEntity.ok(temp);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping
	@RequestMapping("api/typePo")
	private ResponseEntity<Type> saveType(@RequestBody Type tp){
		Type temp = pokemonServices.CreateTypes(tp);

		try {
			return ResponseEntity.created(new URI("api/typePo" + temp.getIdtype())).body(temp);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}


	@GetMapping
	@ResponseBody
	private ResponseEntity<JsonObject> getAllPokemon (@RequestParam int offset, @RequestParam int limit){
		try {
			//pokemonServices.getPokemons(offset*limit, limit)
			List<Pokemon> array = pokemonServices.getPokemons(offset*limit, limit);
			JsonObject response = new JsonObject();
			JsonArray pokemonsList = new JsonArray();
			for (Pokemon pokemon : array) {
				JsonObject PokemonObject = new JsonObject();
				PokemonObject.addProperty("idPokemon", pokemon.getIdPokemon());
				PokemonObject.addProperty("name", pokemon.getName());
				PokemonObject.addProperty("img", pokemon.getImg());
				PokemonObject.addProperty("hp", pokemon.getHp());
				PokemonObject.addProperty("attack", pokemon.getAttack());
				PokemonObject.addProperty("defense", pokemon.getDefense());
				PokemonObject.addProperty("speed", pokemon.getSpeed());
				JsonArray typesObject = new JsonArray();
				Collection<Type> types = pokemon.getTypes();
				for (Type type : types) {
					JsonObject convertedObject = new Gson().fromJson(type.toString(), JsonObject.class);
					typesObject.add(convertedObject);
				}
				PokemonObject.add("types", typesObject);
				pokemonsList.add(PokemonObject);
				//System.out.println(PokemonObject);
			}
			int total = pokemonServices.countPokemons();
			int totalPages = (int) Math.ceil(pokemonServices.countPokemons()/limit);
			response.addProperty("Total", total);
			response.addProperty("TotalPages", totalPages);
			response.addProperty("CurrentPage", offset);
			response.add("pokemon_list", pokemonsList);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}
}
