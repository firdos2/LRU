package com.LRU.Rest.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.LRU.Rest.Service.LRUService;
@Path("/Lru")
@Produces(MediaType.APPLICATION_JSON)
public class LRUController {

    private final Validator validator;
    LRUService lruService = new LRUService(4);
    public LRUController(Validator validator) {
        this.validator = validator;
    }

    @GET
    @Path("/{key}")
    public Response getValueByKey(@PathParam("key") Integer key) {
        int valueByKey = lruService.getValueByKey(key);
        if (valueByKey != -1)
            return Response.ok(valueByKey).build();
        else {
            return Response.ok(-1).build();
        }
    }

    @POST
    @Path("/setData/{key}/{value}")
    public Response setValueByKey(@PathParam("key") Integer key,@PathParam("value") Integer value) {
        lruService.setValueByKey(key,value);
        return Response.ok(key).build();
    }

}