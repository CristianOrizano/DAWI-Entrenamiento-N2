package com.entrena.controller;


import java.lang.ProcessBuilder.Redirect;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entrena.entity.Administrador;
import com.entrena.entity.Ciudad;
import com.entrena.entity.Estado;
import com.entrena.service.AdministradorService;
import com.entrena.service.CiudadService;

@Controller
@RequestMapping("/admin")
public class AdministradorController {
	
	@Autowired
	private AdministradorService seradm;
	
	@Autowired
	private CiudadService serCiu;
	

	
	@RequestMapping("/lista")
	public String ListarAdmi(Model model) {
		List<Administrador> lista= seradm.listadoActivos(1);
		model.addAttribute("listaAd",lista);
		
		return "Principal";
	}
	
	@RequestMapping("/formAdm")
	public String formulario(Model model) {
		
		List<Ciudad> lista= serCiu.listCiuda();
		Administrador admi= new Administrador();
		
		Estado esta = new Estado();
		esta.setEstado(1);
		admi.setEstado(esta);


		model.addAttribute("admin",admi);
		model.addAttribute("lisCiu",lista);
		
		return "PaginaRegistro";
	}
	
	@RequestMapping("/registrar")
	public String registrar(Model model,@ModelAttribute Administrador adminf,RedirectAttributes redirect){
	
		try {
			/*Estado esta= new Estado();
			esta.setEstado(0);
			admin.setEstado(esta);*/

			int cod=adminf.getCodigoAd();
			seradm.guardar(adminf);
			if(cod==0) {
				
				redirect.addFlashAttribute("MENSAJE","Registro exitoso");
			}else {
				
				redirect.addFlashAttribute("MENSAJE","Actualizado exitoso");
			}
			
		} catch (Exception e) {
			System.out.println("error al grabar"+e.getMessage());
		}

		return "redirect:/admin/lista";
	}
	
	 @RequestMapping("/actualizar")
	public String formActu(Model model,@RequestParam("cody") int cod) {
		
		 //buscar para actualizar
		Administrador admi= seradm.BuscarAdmin(cod);
		
		List<Ciudad> lista= serCiu.listCiuda();
		
		model.addAttribute("admin",admi);
		model.addAttribute("lisCiu",lista);
		
		return "PaginaRegistro";
	} 
	
	
	/*@RequestMapping("/actualizar/{id}")
	public String formActu(Model model,@PathVariable(value="id") int cod) {
		
		Administrador admi= seradm.BuscarAdmin(cod);
		
		
		List<Ciudad> lista= serCiu.listCiuda();
		
		model.addAttribute("admin",admi);
		model.addAttribute("lisCiu",lista);
		
		return "PaginaRegistro";
	}  */
	
	@RequestMapping("/eliminar")
	public String formelimi(Model model,@RequestParam("codig") int cod,RedirectAttributes redirect) {
		
		redirect.addFlashAttribute("MENSAJE","Eliminado exitoso");
		int estado=0;
		seradm.eliminar(estado,cod);
		
		System.out.println("segundo commit a entrena2");

		return "redirect:/admin/lista";
	}


}
