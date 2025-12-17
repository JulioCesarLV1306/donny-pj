package com.ncpp.asistenteexpedientes.asistente.controller;

import com.ncpp.asistenteexpedientes.asistente.entity.Bitacora;
import com.ncpp.asistenteexpedientes.asistente.payload.request.RequestBitacora;
import com.ncpp.asistenteexpedientes.asistente.service.impl.BitacoraServiceImpl;
import com.ncpp.asistenteexpedientes.util.Constants;
import com.ncpp.asistenteexpedientes.util.LogDony;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", methods = { RequestMethod.PUT })
@RestController
@RequestMapping(Constants.VERSION_API+"/bitacora")
public class BitacoraController {
    BitacoraServiceImpl bitacoraService;
    public BitacoraController(){
        bitacoraService = new BitacoraServiceImpl();
    }

    @PutMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    public int error(@RequestBody RequestBitacora requestBitacora){
        try{

            Bitacora bitacora = new Bitacora();
            bitacora.setCodigoAccion("ERROR_"+requestBitacora.getCodigo().replace(" ", "_"));
            bitacora.setDescripcionAccion("SE DETECTO UN ERROR CON CODIGO "+requestBitacora.getCodigo());
            try {
                bitacora.setDniSece(requestBitacora.getPersona().getDni());
                bitacora.setNombreSece(requestBitacora.getPersona().getNombre());
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
                LogDony.write(this.getClass().getName()+" - ERROR: "+e);
            }

            try {
                bitacora.setIpModulo(requestBitacora.getModulo().getPcIp());
                bitacora.setUsuarioModulo(requestBitacora.getModulo().getPcUsuario());
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
                LogDony.write(this.getClass().getName()+" - ERROR: "+e);
            }
            bitacoraService.create(bitacora);

            return HttpStatus.OK.value();
        } catch(Exception e){
            e.printStackTrace();
            LogDony.write(this.getClass().getName()+" - ERROR: "+e);
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
       
    }

    @PutMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public int create(@RequestBody RequestBitacora requestBitacora){
        try{

            Bitacora bitacora = new Bitacora();
            bitacora.setCodigoAccion(requestBitacora.getCodigo());
            bitacora.setDescripcionAccion(requestBitacora.getDescripcion());
            try {
                bitacora.setDniSece(requestBitacora.getPersona().getDni());
                bitacora.setNombreSece(requestBitacora.getPersona().getNombre());
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
                LogDony.write(this.getClass().getName()+" - ERROR: "+e);
            }

            try {
                bitacora.setIpModulo(requestBitacora.getModulo().getPcIp());
                bitacora.setUsuarioModulo(requestBitacora.getModulo().getPcUsuario());
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
                LogDony.write(this.getClass().getName()+" - ERROR: "+e);
            }
            bitacoraService.create(bitacora);

            return HttpStatus.OK.value();
        } catch(Exception e){
            e.printStackTrace();
            LogDony.write(this.getClass().getName()+" - ERROR: "+e);
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
       
    }

    
}
