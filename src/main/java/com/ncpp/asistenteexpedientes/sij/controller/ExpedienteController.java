package com.ncpp.asistenteexpedientes.sij.controller;

import java.util.List;

import com.ncpp.asistenteexpedientes.asistente.entity.Bitacora;
import com.ncpp.asistenteexpedientes.asistente.service.impl.BitacoraServiceImpl;
import com.ncpp.asistenteexpedientes.asistente.service.impl.EstadisticasServiceImpl;
import com.ncpp.asistenteexpedientes.sij.entity.Expediente;
import com.ncpp.asistenteexpedientes.sij.payload.request.RequestBusquedaExpediente;
import com.ncpp.asistenteexpedientes.sij.payload.response.ResponseEleccionModel;
import com.ncpp.asistenteexpedientes.sij.service.impl.ExpedienteServiceImpl;
import com.ncpp.asistenteexpedientes.util.Constants;
import com.ncpp.asistenteexpedientes.util.InternalServerException;
import com.ncpp.asistenteexpedientes.util.LogDony;
import com.ncpp.asistenteexpedientes.util.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping(Constants.VERSION_API+"/expediente")
public class ExpedienteController {
    
    ExpedienteServiceImpl expedienteService;

    public ExpedienteController(){
        expedienteService = new ExpedienteServiceImpl();
    }

    @GetMapping(value = "/buscar",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Expediente>> buscar(@RequestParam int numero, @RequestParam int anio, 
    @RequestParam int cuaderno,  @RequestParam String especialidad, @RequestParam String dni,
    @RequestParam int id_tipo, @RequestParam String nombrePersona, @RequestParam String ipModulo,@RequestParam long idModulo,
     @RequestParam String usuarioModulo, Pageable pageable ){
        Page<Expediente> lista ; 
        try {
            if(anio< 2014 || numero <=0) throw new NotFoundException();  
            lista=expedienteService.buscar(new RequestBusquedaExpediente(numero, anio, cuaderno,especialidad, dni, id_tipo), pageable);
        } catch (Exception e) {
            System.out.println(e);		
            e.printStackTrace();	
            LogDony.write(this.getClass().getName()+" - ERROR: "+e);
            throw new InternalServerException();  
        }
        if(lista!=null){
            Bitacora bitacora = new Bitacora();
            BitacoraServiceImpl bitacoraService= new BitacoraServiceImpl();
            bitacora.setDniSece(dni);
            bitacora.setCodigoAccion("BUSQUEDA_EXPEDIENTE");
            bitacora.setNombreSece(nombrePersona);
            bitacora.setIpModulo(ipModulo);
            bitacora.setUsuarioModulo(usuarioModulo);
            bitacora.setDescripcionAccion("BUSCO EL EXPEDIENTE CON NUMERO "+numero+" AÑO "+anio+" CUADERNO "+cuaderno
            +" EN LA ESPECIALIDAD "+especialidad+ " Y OBTUVO "+lista.getTotalElements()+ " RESULTADOS");
            bitacoraService.create(bitacora);

            aumentarEspecialidad(idModulo, especialidad);

        }else{
            throw new NotFoundException();  
        } 
        return new ResponseEntity<Page<Expediente>>(lista,HttpStatus.OK);
    }

    @GetMapping(value = "/buscar/dni",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Expediente>> buscarDNI(@RequestParam String dni,  @RequestParam String especialidad, 
        @RequestParam int id_tipo, @RequestParam String nombrePersona, @RequestParam String ipModulo, @RequestParam long idModulo,
         @RequestParam String usuarioModulo, Pageable pageable ){
        Page<Expediente> lista ; 
        try {
            lista=expedienteService.buscarPorDNI(dni,especialidad, pageable);
        } catch (Exception e) {
            System.out.println(e);		
            e.printStackTrace();	
            LogDony.write(this.getClass().getName()+" - ERROR: "+e);
            throw new InternalServerException();  
        }
        if(lista!=null){
            Bitacora bitacora = new Bitacora();
            BitacoraServiceImpl bitacoraService= new BitacoraServiceImpl();
            bitacora.setDniSece(dni);
            bitacora.setCodigoAccion("BUSQUEDA_EXPEDIENTE");
            bitacora.setNombreSece(nombrePersona);
            bitacora.setIpModulo(ipModulo);
            bitacora.setUsuarioModulo(usuarioModulo);
            bitacora.setDescripcionAccion("LA PERSONA DE DNI "+dni
            +" BUSCO EN LA ESPECIALIDAD "+especialidad+ " Y OBTUVO "+lista.getNumberOfElements()+  " ELEMENTOS DE " + lista.getTotalElements() + " PAGINA "+ pageable.getPageNumber());
            bitacoraService.create(bitacora);

            aumentarEspecialidad(idModulo, especialidad);
        }else{
            throw new NotFoundException();  
        } 
        return new ResponseEntity<Page<Expediente>>(lista,HttpStatus.OK);
    }

    @GetMapping(value = "/contar",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResponseEleccionModel>> getConteos(@RequestParam String nUnico, @RequestParam int nIncidente ){
        List<ResponseEleccionModel> lista=null ; 
        try {
           lista=expedienteService.getConteos( nUnico, nIncidente);
        } catch (Exception e) {
            System.out.println(e);			
            LogDony.write(this.getClass().getName()+" - ERROR: "+e);
            throw new InternalServerException();  
        }
        if(lista==null) throw new NotFoundException();  


        return new ResponseEntity<List<ResponseEleccionModel>>(lista,HttpStatus.OK);
    }

    private void aumentarEspecialidad(long idModulo, String especialidad){
        EstadisticasServiceImpl estadisticasServiceImpl = new EstadisticasServiceImpl();
        switch (especialidad) {
            case "LA":
                estadisticasServiceImpl.aumentarLaboral(idModulo, 1);
                break;
            case "CI":
                estadisticasServiceImpl.aumentarCivil(idModulo, 1);
                break;
            case "FA":
                estadisticasServiceImpl.aumentarFamilia(idModulo, 1);
                break;
            case "PE":
                estadisticasServiceImpl.aumentarPenal(idModulo, 1);
                break;
            case "TODOS":
                estadisticasServiceImpl.aumentarLaboral(idModulo, 1);
                estadisticasServiceImpl.aumentarCivil(idModulo, 1);
                estadisticasServiceImpl.aumentarFamilia(idModulo, 1);
                estadisticasServiceImpl.aumentarPenal(idModulo, 1);
                break;
        }
    }
    
}
