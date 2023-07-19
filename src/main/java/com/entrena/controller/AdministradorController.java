package com.entrena.controller;


import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import org.springframework.web.multipart.MultipartFile;
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
	public String registrar(Model model,@ModelAttribute Administrador adminf,RedirectAttributes redirect,@RequestParam(name = "img") MultipartFile imag ){
	
		try {
			/*Estado esta= new Estado();
			esta.setEstado(0);
			admin.setEstado(esta);*/

			int cod=adminf.getCodigoAd();
			
			if(cod==0) {
				if(imag.isEmpty()) {					
					adminf.setNom_img("default.png");
					seradm.guardar(adminf);
				}else {	
					saveimage(imag,adminf);	
				}		
				seradm.guardar(adminf);
				redirect.addFlashAttribute("MENSAJE","Registro exitoso");
			}else {
				if(imag.isEmpty()) {									
					seradm.guardar(adminf);
				}else {	
					saveimage(imag,adminf);
				}
				
				redirect.addFlashAttribute("MENSAJE","Actualizado exitoso");
			}
			
		} catch (Exception e) {
			System.out.println("error al grabar"+e.getMessage());
		}

		return "redirect:/admin/lista";
	}
	
	public void saveimage(MultipartFile imag, Administrador adminf) throws IOException {
		
		
		String nomArchivo = imag.getOriginalFilename();
		//necesito los archivos de la img pero en byte(ya que las imagnes tienes byte)
		byte[] bytes=imag.getBytes();
		//
		String ruta="C://ZClinica//DatosImg//";
		//generar archivo
		Files.write(Paths.get(ruta+nomArchivo), bytes);	
		adminf.setNom_img(nomArchivo);
		seradm.guardar(adminf);		
		
	}
	
	
	/* @RequestMapping("/actualizar")
	public String formActu(Model model,@RequestParam("cody") int cod) {
		
		 //buscar para actualizar
		Administrador admi= seradm.BuscarAdmin(cod);
		
		List<Ciudad> lista= serCiu.listCiuda();
		
		model.addAttribute("admin",admi);
		model.addAttribute("lisCiu",lista);
		
		return "PaginaRegistro";
	} */
	
	
	@RequestMapping("/actualizar/{id}")
	public String formActu(Model model,@PathVariable(value="id") int cod) {
		
		Administrador admi= seradm.BuscarAdmin(cod);
		
		
		List<Ciudad> lista= serCiu.listCiuda();
		
		model.addAttribute("admin",admi);
		model.addAttribute("lisCiu",lista);
		
		return "PaginaRegistro";
	}  
	
	@RequestMapping("/eliminar")
	public String formelimi(Model model,@RequestParam("codig") int cod,RedirectAttributes redirect) {
		
		redirect.addFlashAttribute("MENSAJE","Eliminado exitoso");
		int estado=0;
		seradm.eliminar(estado,cod);
		
		System.out.println("segundo commit a entrena2");

		return "redirect:/admin/lista";
	}


}
