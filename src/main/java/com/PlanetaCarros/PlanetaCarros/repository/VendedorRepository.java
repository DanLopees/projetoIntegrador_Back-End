package com.PlanetaCarros.PlanetaCarros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.PlanetaCarros.PlanetaCarros.models.Garagem;
import com.PlanetaCarros.PlanetaCarros.models.Vendedor;



public interface VendedorRepository extends CrudRepository<Vendedor, String> {

	Iterable<Vendedor> findByGaragem(Garagem garagem);

	// para o método delete por CPF
	Vendedor findByCpf(String cpf);
	
	Vendedor findById(long id);
	List<Vendedor> findByNome(String nome);

	// Query para a busca
	@Query(value = "select u from Vendedor u where u.nome like %?1%")
	List<Vendedor> findByNomesVendedor(String nome);

}
