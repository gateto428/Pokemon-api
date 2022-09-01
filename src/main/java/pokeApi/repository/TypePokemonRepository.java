package pokeApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pokeApi.models.Type;
@Repository 
public interface TypePokemonRepository extends JpaRepository<Type, Long> {
	
	public List<Type> findByTypeIgnoreCaseContaining(String type);
	
	@Query(value = "SELECT * FROM type WHERE idtype  = :idtype", nativeQuery = true)
	public Type findByTypeId( @Param("idtype") Long idtype);

}
