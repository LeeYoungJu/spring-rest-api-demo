package com.yjlee.restapidemo.event;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class EventResource extends EntityModel<Event> {

    public EventResource(Event event) {
        super(event);
        add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
    }
}
