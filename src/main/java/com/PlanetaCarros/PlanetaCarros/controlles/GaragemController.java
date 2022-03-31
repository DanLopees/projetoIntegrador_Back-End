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
import com.PlanetaCarros.PlanetaCarros.models.Vendedor;
import com.PlanetaCarros.PlanetaCarros.repository.GaragemRepository;
import com.PlanetaCarros.PlanetaCarros.repository.VendedorRepository;
import com.PlanetaCarros.PlanetaCarros.models.Garagem;

	@Controller
	public class GaragemController {

		@Autowired
		private GaragemRepository gr;

		@Autowired
		private VendedorRepository vr;

		// Formulario de Cadastro Da Garagem
		@RequestMapping(value="/cadastrarGaragem", method = RequestMethod.GET)
		public String form() {
			return "garagem/formGaragem";
		}

		// POST que Cadastra Garagem
		@RequestMapping(value = "/cadastrarGaragem", method = RequestMethod.POST)
		public String form(@Valid Garagem garagem, BindingResult result, RedirectAttributes attributes) {

			if (result.hasErrors()) {
				attributes.addFlashAttribute("mensagem", "Verifique os campos");
				return "redirect:/cadastrarGaragem";
			}

			gr.save(garagem);
			attributes.addFlashAttribute("mensagem", "Garagem cadastrado com sucesso!");
			return "redirect:/cadastrarGaragem";
		}

		//Lista as Garagens
		@RequestMapping("/garagem")
		public ModelAndView listaGaragem() {
			ModelAndView mv = new ModelAndView("garagem/listaGaragem");
			Iterable<Garagem> garagens = gr.findAll();
			mv.addObject("garagens", garagens);
			return mv;
		}

		// Lista Vendedores e os detalhes das Garagens
		@RequestMapping(value = "/vendedores/{id}", method = RequestMethod.GET)
		public ModelAndView vendedores(@PathVariable("id") long id) {
			Garagem garagem = gr.findById(id);
			ModelAndView mv = new ModelAndView("garagem/vendedores");
			mv.addObject("garagens", garagem);

			// Lista de Vendedores baseado no id da garagem
			Iterable<Vendedor> vendedores = vr.findByGaragem(garagem);
			mv.addObject("vendedores", vendedores);

			return mv;

		}
		
		// Adiciona Vendedores
		@RequestMapping(value="/vendedores/{id}", method = RequestMethod.POST)
		public String detalhesGaragemPost(@PathVariable("id") long id, Vendedor vendedores, BindingResult result,
				RedirectAttributes attributes) {
			
			if(result.hasErrors()) {
				attributes.addFlashAttribute("mensagem", "Verifique os campos!");
				return "redirect:/vendedores/{id}";
			}
			
			if(vr.findByCpf(vendedores.getCpf()) != null) {
				attributes.addFlashAttribute("mensagem_erro", "CPF duplicado");
				return "redirect:/vendedores/{id}";
			}
			
			Garagem garagem = gr.findById(id);
			vendedores.setGaragem(garagem);
			vr.save(vendedores);
			attributes.addFlashAttribute("mensagem", "Vendedor adicionado com sucesso");
			return "redirect:/vendedores/{id}";
		}
		
		//Deleta Garagem
		@RequestMapping("/deletarGaragem")
		public String deletarGaragem(long id) {
			Garagem garagem = gr.findById(id);
			gr.delete(garagem);
			return "redirect:/garagem";
			}
		
		
		
		// Formulario de Edião das garagens
		@RequestMapping(value="/editar-garagem", method = RequestMethod.GET)
		public ModelAndView editarGaragem(long id) {
			Garagem garagem = gr.findById(id);
			ModelAndView mv = new ModelAndView("garagem/update-garagem");
			mv.addObject("garagem", garagem);
			return mv;
		}
		
		// Atualiza Garagens
		@RequestMapping(value = "/editar-garagem", method = RequestMethod.POST)
		public String updateGaragem(@Valid Garagem garagem,  BindingResult result, RedirectAttributes attributes){
			
			gr.save(garagem);
			attributes.addFlashAttribute("success", "Garagem alterado com sucesso!");
			
			long idLong = garagem.getId();
			String id = "" + idLong;
			return "redirect:/vendedores/" + id;
			
			
		}
		// Deleta Vendedor
		@RequestMapping("/deletarVendedor")
		public String deletarVendedor(String cpf) {
			Vendedor vendedor = vr.findByCpf(cpf);
			
			Garagem garagem = vendedor.getGaragem();
			String id = "" + garagem.getId();
			
			vr.delete(vendedor);
			return "redirect:/vendedores/" + id;
		}
}
