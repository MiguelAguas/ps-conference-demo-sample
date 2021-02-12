package com.pruralsight.conferencedemo.controllers;

import com.pruralsight.conferencedemo.models.Session;
import com.pruralsight.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionsController {
    @Autowired
    private SessionRepository sessionRepository;

    //  i´m going to create a list endpoint
    // this will return all of the sessions when called
    // so i need to create a method that looks like this
    @GetMapping
    public List<Session> list() {
        return sessionRepository.findAll();
    }

    //the next REST endpoint I want to add is the ability
    // to get a specific session by ID
    //endpoint que recupera una sesion específica según su Id
    @GetMapping // con esta anotación le decimos que es un verbo GET
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id){
        return sessionRepository.getOne(id);
    }

    //endpoint que crea una nueva sesion
    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public Session create(@RequestBody final Session session){
        return sessionRepository.saveAndFlush(session);
    }

    //endpoint que borra un registro
    @RequestMapping(value="{id}", method = RequestMethod.DELETE)
    public Session delete(@PathVariable Long id){
        //also need to check for children records before deleting
         sessionRepository.deleteById(id);
        return null;
    }

    @RequestMapping(value="{id}", method = RequestMethod.PUT)
    public Session update(@PathVariable Long id, @RequestBody Session session){
        //
        //
        Session existingSession = sessionRepository.getOne(id);
        BeanUtils.copyProperties(session, existingSession, "session_id");
        return sessionRepository.saveAndFlush(existingSession);
    }


}
