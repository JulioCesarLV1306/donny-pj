package com.ncpp.asistenteexpedientes.asistente.controller;


import com.ncpp.asistenteexpedientes.asistente.entity.Bitacora;
import com.ncpp.asistenteexpedientes.asistente.entity.Encuesta;
import com.ncpp.asistenteexpedientes.asistente.service.BitacoraService;
import com.ncpp.asistenteexpedientes.asistente.service.EncuestaService;
import com.ncpp.asistenteexpedientes.asistente.service.impl.BitacoraServiceImpl;
import com.ncpp.asistenteexpedientes.asistente.service.impl.EncuestaServiceImpl;
import com.ncpp.asistenteexpedientes.util.Constants;
import com.ncpp.asistenteexpedientes.util.InternalServerException;
import com.ncpp.asistenteexpedientes.util.LogDony;
import com.ncpp.asistenteexpedientes.util.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST })
@RestController
@RequestMapping(Constants.VERSION_API+"/encuesta")
public class EncuestaController {

    EncuestaService encuestaService;
    BitacoraService bitacoraService;
    public EncuestaController(){
        encuestaService=new EncuestaServiceImpl();
        bitacoraService = new BitacoraServiceImpl();
    }

    @PostMapping(value = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void crear(@RequestBody Encuesta encuesta,@RequestParam String ipModulo,@RequestParam String usuarioModulo){
        try {
            encuestaService.create(encuesta); 
            Bitacora bitacora = new Bitacora();
            bitacora.setCodigoAccion("ENCUESTA");
            bitacora.setDescripcionAccion(encuesta.getNombreSece()+" REGISTRO ENCUESTA CON CALIFICACION "+encuesta.getCalificacion());
            bitacora.setDniSece(encuesta.getDniSece());
            bitacora.setNombreSece(encuesta.getNombreSece());
            bitacora.setIpModulo(ipModulo);
            bitacora.setUsuarioModulo(usuarioModulo);
            bitacoraService.create(bitacora);
        } catch (Exception e) {
            e.printStackTrace();
            LogDony.write(this.getClass().getName()+" - ERROR: "+e);
        }
    }

    @GetMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Encuesta>  buscar(@RequestParam String dni){
        Encuesta encuesta=null;
        try {
            encuesta=encuestaService.findByDni(dni);
        } catch (Exception e) {
            System.out.println(e);	
            e.printStackTrace();	
            LogDony.write(this.getClass().getName()+" - ERROR: "+e);	
            throw new InternalServerException();  
        }
        if(encuesta==null) {  throw new NotFoundException();   }
        return new ResponseEntity<Encuesta>(encuesta,HttpStatus.OK);
    }
    
}
