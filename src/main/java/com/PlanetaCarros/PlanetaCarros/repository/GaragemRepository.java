package com.PlanetaCarros.PlanetaCarros.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.PlanetaCarros.PlanetaCarros.models.Garagem;



public interface GaragemRepository extends CrudRepository<Garagem, Long>{
	
	Garagem findById(long id);
	Garagem findByNome(String nome);
	
	//BUSCA
	@Query(value = "select u from Garagem u where u.nome like %?1%")
	List<Garagem>findByNomes(String nome);

}
