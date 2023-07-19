package com.entrena;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigImage  implements WebMvcConfigurer{
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		         //alias para acceder ala carpeta ("/DatosImg/**")
		registry.addResourceHandler("/DatosImg/**").addResourceLocations("file:/C:/ZClinica/DatosImg/");
	}
	

}
