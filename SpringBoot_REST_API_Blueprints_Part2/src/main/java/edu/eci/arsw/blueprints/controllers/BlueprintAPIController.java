/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
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
    public ResponseEntity<?> getAllBlueprint() throws ResourceNotFoundException, BlueprintNotFoundException{
        Set<Blueprint> blueprints = bps.getAllBlueprints();
        if (blueprints.isEmpty()) {
            throw new ResourceNotFoundException();
        } else {
            return new ResponseEntity<>(blueprints, HttpStatus.OK);
        }
    
    }

        
    @GetMapping(value = "/{author}")
    public ResponseEntity<?> getBlueprintByAuthor(@PathVariable("author") String author) throws ResourceNotFoundException, BlueprintNotFoundException {
        Set<Blueprint> blueprints = bps.getBlueprintsByAuthor(author);
        if (blueprints.isEmpty()) {
            throw new ResourceNotFoundException();
        } else {
            return new ResponseEntity<>(blueprints, HttpStatus.OK);
        }
    }

}
