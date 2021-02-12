package com.pruralsight.conferencedemo.controllers;

import com.pruralsight.conferencedemo.models.Speaker;
import com.pruralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakersController {
    @Autowired
    private SpeakerRepository speakerRepository;

    //  i´m going to create a list endpoint
    // this will return all of the sessions when called
    // so i need to create a method that looks like this
    @GetMapping
    public List<Speaker> list() {
        return speakerRepository.findAll();
    }

    //the next REST endpoint I want to add is the ability
    // to get a specific session by ID
    //endpoint que recupera una sesion específica según su Id
    @GetMapping // con esta anotación le decimos que es un verbo GET
    @RequestMapping("{id}")
    public Speaker get(@PathVariable Long id){
        return speakerRepository.getOne(id);
    }

    //endpoint que crea un nuevo speaker
    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public Speaker create(@RequestBody final Speaker speaker){
        return speakerRepository.saveAndFlush(speaker);
    }

    //endpoint que borra un registro
    @RequestMapping(value="{id}", method = RequestMethod.DELETE)
    public Speaker delete(@PathVariable Long id){
        //also need to check for children records before deleting
        speakerRepository.deleteById(id);
        return null;
    }
    @RequestMapping(value="{id}", method = RequestMethod.PUT)
    public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker){
        //
        //
        Speaker existingSpeaker = speakerRepository.getOne(id);
        BeanUtils.copyProperties(speaker, existingSpeaker, "speaker_id");
        return speakerRepository.saveAndFlush(existingSpeaker);
    }
}
