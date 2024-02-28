/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

    @Autowired
    //@Qualifier("Service")
    BlueprintsServices bps;

    @GetMapping()
    public ResponseEntity<?> getAllBlueprint() throws ResourceNotFoundException{
        try{
            return new ResponseEntity<>(bps.getAllBlueprints(), HttpStatus.OK);
        }catch(BlueprintNotFoundException ex){
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND);
        }
    }

        
    @GetMapping(value = "/{author}")
    public ResponseEntity<?> getBlueprintByAuthor(@PathVariable("author") String author) throws ResourceNotFoundException, BlueprintNotFoundException {
        Set<Blueprint> blueprints = bps.getBlueprintsByAuthor(author);
        if (blueprints.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return new ResponseEntity<>(blueprints, HttpStatus.OK);
    }

    @GetMapping(value="/{author}/{name}")
    public ResponseEntity<?>  getBlueprintByAuthorAndName(@PathVariable("author") String author,@PathVariable("name") String name )throws ResourceNotFoundException, BlueprintNotFoundException{
        try {
            return new ResponseEntity<>(bps.getBlueprint(author,name), HttpStatus.OK);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<?> postBlueprint(@RequestBody Blueprint blueprint) throws BlueprintNotFoundException{
        try {
            bps.addNewBlueprint(blueprint);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintPersistenceException ex) {
            //Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("No se registro",HttpStatus.FORBIDDEN);
        }

    }

    @PutMapping(value = "/{author}/{name}")
    public ResponseEntity<?> putBlueprint(@PathVariable String author, @PathVariable String name, @RequestBody Blueprint blueprint){
        Blueprint blueprintNew = null;
        try {
            blueprintNew = bps.getBlueprint(author, name);
            blueprintNew.setAuthor(blueprint.getAuthor());
            blueprintNew.setName(blueprint.getName());
            blueprintNew.setPoints(blueprint.getPoints());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>("Espera",HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

