package com.ncpp.asistenteexpedientes.asistente.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", methods = { RequestMethod.GET })
@RestController
@RequestMapping("/")
public class UtilitarioController {

    @GetMapping(value = "memoria", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> memory(@RequestParam String key){
        if("donysanta123".equals(key)){
            Runtime runtime = Runtime.getRuntime();
            String reporte=
            "Memoria máxima: " + toKB(runtime.maxMemory()) + "KB ("+toMB(runtime.maxMemory())+"MB)\n"+
            "Memoria total: " + toKB(runtime.totalMemory()) + "KB ("+toMB(runtime.totalMemory())+"MB)\n"+
            "Memoria libre: " + toKB(runtime.freeMemory()) + "KB ("+toMB(runtime.freeMemory())+"MB)\n"+
            "Memoria usada: " + toKB(runtime.totalMemory()-runtime.freeMemory()) + "KB ("+toMB(runtime.totalMemory()-runtime.freeMemory())+"MB)";
            return new ResponseEntity<String>(reporte,HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("KEY INCORRECTA",HttpStatus.OK);
        }
      

       
    }

    private long toMB(long byts){
       return toKB(byts)/1024;
    }
    private long toKB(long byts){
        return byts/1024;
    }
    
}
