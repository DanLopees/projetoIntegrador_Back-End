package com.PlanetaCarros.PlanetaCarros.controlles;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.PlanetaCarros.PlanetaCarros.models.Carro;
import com.PlanetaCarros.PlanetaCarros.models.Vendedor;
import com.PlanetaCarros.PlanetaCarros.models.Garagem;
import com.PlanetaCarros.PlanetaCarros.models.Marca;

import com.PlanetaCarros.PlanetaCarros.repository.GaragemRepository;
import com.PlanetaCarros.PlanetaCarros.repository.MarcaRepository;
import com.PlanetaCarros.PlanetaCarros.repository.VendedorRepository;
import com.PlanetaCarros.PlanetaCarros.repository.CarroRepository;

@Controller
public class BuscaController {
	
	@Autowired
	private GaragemRepository gr;
	
	@Autowired
	private MarcaRepository mr;
	
	@Autowired
	private VendedorRepository vr;
	
	@Autowired
	private CarroRepository cr;
	
	//GET
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView abrirIndex() {
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	
	//POST
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView buscarIndex(@RequestParam("buscar") String buscar, @RequestParam("nome") String nome) {
		
		ModelAndView mv = new ModelAndView("index");
		String mensagem = "Resultados da busca por " + buscar;
		
		if(nome.equals("nomegaragem")) {
			mv.addObject("garagens", gr.findByNomes(buscar));
			
		}else if(nome.equals("nomevendedor")) {
			mv.addObject("vendedores", vr.findByNomesVendedor(buscar));
			
		}else if(nome.equals("nomecarro")) {
			mv.addObject("carros", cr.findByNomesCarros(buscar));
			
		}else if(nome.equals("titulomarca")) {
			mv.addObject("marcas", mr.findByNomesMarcas(buscar));
			
		}else {
			mv.addObject("garagens", gr.findByNomes(buscar));
			mv.addObject("vendedores", vr.findByNomesVendedor(buscar));
			mv.addObject("carros", cr.findByNomesCarros(buscar));
			mv.addObject("marcas", mr.findByNomesMarcas(buscar));
		}
		
		mv.addObject("mensagem", mensagem);
		
		return mv;
	}

}
