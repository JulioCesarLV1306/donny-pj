package com.ncpp.asistenteexpedientes.sece.controller;

import com.ncpp.asistenteexpedientes.asistente.entity.Bitacora;
import com.ncpp.asistenteexpedientes.asistente.service.impl.BitacoraServiceImpl;
import com.ncpp.asistenteexpedientes.sece.entity.Persona;
import com.ncpp.asistenteexpedientes.sece.service.PersonaService;
import com.ncpp.asistenteexpedientes.util.Constants;
import com.ncpp.asistenteexpedientes.util.InternalServerException;
import com.ncpp.asistenteexpedientes.util.LogDony;
import com.ncpp.asistenteexpedientes.util.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET , RequestMethod.POST })
@RestController
@RequestMapping(Constants.VERSION_API+"/persona")
public class PersonaController {

    @Autowired
    PersonaService personaService;

    @GetMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Persona> find(@RequestParam String dni,@RequestParam String ipModulo, @RequestParam String usuarioModulo){
        Persona persona = new Persona();
        try {
            persona=personaService.buscarPorDni(dni);
        } catch (Exception e) {
            System.out.println(e);			
            e.printStackTrace();
            LogDony.write(this.getClass().getName()+" - ERROR: "+e);
            throw new InternalServerException();  
        }
        Bitacora bitacora = new Bitacora();
        BitacoraServiceImpl bitacoraService= new BitacoraServiceImpl();
        bitacora.setDniSece(dni);
        if(persona!=null){
            bitacora.setCodigoAccion("LOGIN_PERSONA");
            bitacora.setNombreSece(persona.getNombre());
            bitacora.setUsuarioModulo(usuarioModulo);
            bitacora.setIpModulo(ipModulo);
            bitacora.setDescripcionAccion("LA PERSONA "+persona.getNombre()+" ("+persona.getDni()+") INGRESO AL ASISTENTE CON EXITO");
            bitacoraService.create(bitacora);
        }else{
            bitacora.setCodigoAccion("FAIL_LOGIN_PERSONA");
            bitacora.setUsuarioModulo(usuarioModulo);
            bitacora.setIpModulo(ipModulo);
            bitacora.setDescripcionAccion("EL DNI "+dni+" INTENTO INGRESAR AL ASISTENTE SIN EXITO");
            bitacoraService.create(bitacora);
            throw new NotFoundException();  
        } 
        
        return new ResponseEntity<Persona>(persona,HttpStatus.OK);
    }



    
}
