package com.PlanetaCarros.PlanetaCarros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.PlanetaCarros.PlanetaCarros.models.Carro;
import com.PlanetaCarros.PlanetaCarros.models.Marca;

public interface CarroRepository extends CrudRepository<Carro, String> {
	
	Iterable<Carro>findByMarca(Marca marca);
	
	Carro findByPlaca(String placa);
	
	Carro findById(long id);
	
	List<Carro>findByNomeCarro(String nomeCarro);
}
