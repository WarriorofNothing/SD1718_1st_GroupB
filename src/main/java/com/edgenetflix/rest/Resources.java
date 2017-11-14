package com.edgenetflix.rest;

import com.edgenetflix.rest.db.Leecher;
import com.edgenetflix.rest.db.Tracker;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/")
public class Resources {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String start(){
        return "<h1>Welcome to the Portal!</h1>"
                + "<form action='http://localhost:8080/home/'><input type='submit' value='Get in'></form>";
    }

    @GET
    @Path("/home")
    @Produces(MediaType.TEXT_HTML)
    public String home(){
        return "Nothing to see here. Sorry!";
    }

    @GET
    @Path("/home/download")
    @Produces(MediaType.TEXT_PLAIN)
    public String download() {

        return "200";
    }
    @GET
    @Path("/home/download/metainfo")
    @Produces(MediaType.TEXT_PLAIN)
    public String metainfo(@QueryParam("file") String file,
                           @QueryParam("id") String peer_id,
                           @QueryParam("ip") String ip,
                           @QueryParam("port") int port) throws InterruptedException{

        Tracker.getInstance().indexLeecher(file, new Leecher(peer_id, ip, port));

        return Tracker.getInstance().getSeedMetadata(file);
    }

    @GET
    @Path("/home/download/chunk")
    @Produces(MediaType.TEXT_PLAIN)
    public String chunk(@QueryParam("file") String file,
                        @QueryParam("id") String client_id,
                        @QueryParam("index") int nseq) {

        return Tracker.getInstance().getChunkInfo(file,client_id,nseq);
    }

    @GET
    @Path("/home/download/ack")
    @Produces(MediaType.TEXT_PLAIN)
    public String ack(@QueryParam("file") String file,
                      @QueryParam("id") String client_id,
                      @QueryParam("index") int nseq,
                      @QueryParam("completed") boolean completed){

        if(completed){
            Tracker.getInstance().removeLeecher(file, client_id);
        }
        else{
            Tracker.getInstance().updateLeecher(file, client_id, nseq);
        }

        return "OK";
    }

    @GET
    @Path("/home/list")
    @Produces(MediaType.TEXT_PLAIN)
    public String list(){
        return Tracker.getInstance().seederList();
    }

}
