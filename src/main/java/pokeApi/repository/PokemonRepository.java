package pokeApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pokeApi.models.Pokemon;
@Repository 
public interface PokemonRepository extends JpaRepository<Pokemon,Long> {



	@Query(value = "SELECT p.* FROM pokemon p LIMIT :limit OFFSET :offset", 
			nativeQuery = true)
	public  List<Pokemon> findPokemons(@Param("offset") int offset, @Param("limit") int limit);
	
	@Query(value = "SELECT count(*) FROM pokemon", nativeQuery = true)
	public int countPokemons();
}
