/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        // Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        // Blueprint bp=new Blueprint("_authorname_", "_bpname_ ",pts);
        // blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }



    /**
     * Obtiene un plano arquitectónico específico dado el nombre del autor y el nombre del plano.
     * 
     * @param author El nombre del autor del plano arquitectónico.
     * @param bprintname El nombre del plano arquitectónico.
     * @return El plano arquitectónico correspondiente al autor y nombre dados.
     * @throws BlueprintNotFoundException Si no se encuentra ningún plano con el autor y nombre especificados.
     */
    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }





    /**
     * Obtiene todos los planos arquitectónicos creados por un autor específico.
     * 
     * @param author El nombre del autor de los planos arquitectónicos a buscar.
     * @return Un conjunto de planos arquitectónicos creados por el autor especificado.
     * @throws BlueprintNotFoundException Si no se encuentra ningún plano para el autor especificado.
     */
    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> bp = new HashSet<>();
        Set<Tuple<String, String>> keys = blueprints.keySet();

        for(Tuple<String, String> tuple : keys){
            if(tuple.getElem1().equals(author)){
                bp.add(blueprints.get(tuple));
            }
        }

        return bp;
    }



    /**
     * Obtiene todos los planos arquitectónicos registrados en el sistema.
     * 
     * @return Un conjunto de todos los planos arquitectónicos registrados.
     * @throws BlueprintNotFoundException Si no se encuentran planos arquitectónicos registrados en el sistema.
     */
    @Override
    public Set<Blueprint> getAllBlueprint() throws BlueprintNotFoundException {
        Set<Blueprint> bp = new HashSet<>();
        Set<Tuple<String, String>> keys = blueprints.keySet();

        for(Tuple<String, String> tuple : keys){
            bp.add(blueprints.get(tuple));
        }

        return bp;
    }

    
    
}
