package com.PlanetaCarros.PlanetaCarros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.PlanetaCarros.PlanetaCarros.models.Marca;



public interface MarcaRepository  extends CrudRepository<Marca, String> {
	Marca findByCodigo(long codigo);
	List<Marca> findByNome(String nome);
}