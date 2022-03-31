package com.PlanetaCarros.PlanetaCarros.controlles;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.PlanetaCarros.PlanetaCarros.models.Carro;
import com.PlanetaCarros.PlanetaCarros.models.Marca;
import com.PlanetaCarros.PlanetaCarros.repository.CarroRepository;
import com.PlanetaCarros.PlanetaCarros.repository.MarcaRepository;

@Controller
public class MarcaController {
	
	@Autowired
	private MarcaRepository mr;
	@Autowired
	private CarroRepository cr;

	// CADASTRA MARCA
		@RequestMapping(value = "/cadastrarMarca", method = RequestMethod.GET)
		public String form() {
			return "marca/formMarca";
		}
		
		@RequestMapping(value = "/cadastrarMarca", method = RequestMethod.POST)
		public String form(@Valid Marca marca, BindingResult result, RedirectAttributes attributes) {

			if (result.hasErrors()) {
				attributes.addFlashAttribute("mensagem", "Verifique os campos...");
				return "redirect:/cadastrarMarca";
			}

			mr.save(marca);
			attributes.addFlashAttribute("mensagem", "Marca cadastrada com sucesso!");
			return "redirect:/cadastrarMarca";
		}
		
		// LISTA MARCA

		@RequestMapping("/marcas")
		public ModelAndView listaMarcas() {
			ModelAndView mv = new ModelAndView("marca/listaMarca");
			Iterable<Marca> marcas = mr.findAll();
			mv.addObject("marcas", marcas);
			return mv;
		}

		//
		@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
		public ModelAndView detalhesMarca(@PathVariable("codigo") long codigo) {
			Marca marca = mr.findByCodigo(codigo);
			ModelAndView mv = new ModelAndView("marca/detalhesMarca");
			mv.addObject("marca", marca);

			Iterable<Carro> carros = cr.findByMarca(marca);
			mv.addObject("carros", carros);

			return mv;

		}
		
		// DELETA MARCA
		@RequestMapping("/deletarMarca")
		public String deletarMarca(long codigo) {
			Marca marca = mr.findByCodigo(codigo);
			mr.delete(marca);
			return "redirect:/marcas";
		}
		
		// ADICIONAR CANDIDATO
		@RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
		public String detalhesMarcaPost(@PathVariable("codigo") long codigo, @Valid Carro carro,
				BindingResult result, RedirectAttributes attributes) {

			if (result.hasErrors()) {
				attributes.addFlashAttribute("mensagem", "Verifique os campos");
				return "redirect:/{codigo}";
			}

			// placa duplicado
			if (cr.findByPlaca(carro.getPlaca()) != null) {
				attributes.addFlashAttribute("mensagem_erro", "Placa duplicado");
				return "redirect:/{codigo}";
			}

			Marca marca = mr.findByCodigo(codigo);
			carro.setMarca(marca);
			cr.save(carro);
			attributes.addFlashAttribute("mensagem", "Carro adionado com sucesso!");
			return "redirect:/{codigo}";
		}
		
		// DELETA CANDIDATO PELA PLACA
		@RequestMapping("/deletarCarro")
		public String deletarCarro(String placa) {
			Carro carro = cr.findByPlaca(placa);
			Marca marca = carro.getMarca();
			String codigo = "" + marca.getCodigo();

			cr.delete(carro);

			return "redirect:/" + codigo;

		}
		// ATUALIZA MARCA
		// EDITAR MARCA
		@RequestMapping(value = "/editar-marca", method = RequestMethod.GET)
		public ModelAndView editarMarca(long codigo) {
			Marca marca = mr.findByCodigo(codigo);
			ModelAndView mv = new ModelAndView("marca/update-marca");
			mv.addObject("marca", marca);
			return mv;
		}

		// UPDATE MARCA
		@RequestMapping(value = "/editar-marca", method = RequestMethod.POST)
		public String updateMarca(@Valid Marca marca, BindingResult result, RedirectAttributes attributes) {
			mr.save(marca);
			attributes.addFlashAttribute("success", "Marca alterada com sucesso!");

			long codigoLong = marca.getCodigo();
			String codigo = "" + codigoLong;
			return "redirect:/" + codigo;
		}

		
}
