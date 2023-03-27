package com.yjlee.restapidemo.common;

import com.yjlee.restapidemo.index.IndexController;
import org.springframework.hateoas.EntityModel;
import org.springframework.validation.Errors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ErrorResource extends EntityModel<Errors> {

    public ErrorResource(Errors errors) {
        super(errors);
        add(linkTo(methodOn(IndexController.class).index()).withRel("profile"));
    }
}
