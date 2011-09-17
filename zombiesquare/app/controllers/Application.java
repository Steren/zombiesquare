package controllers;

import parameters.Parameters;
import play.*;
import play.data.validation.Required;
import play.mvc.*;
import requests.AuthenticationRequest;
import requests.HTTPRequestPoster;
import java.util.*;

import models.Player;

import json.model.AccessToken;
import json.model.foursquareAPI.FourSquareUser;

import com.google.gson.Gson;

public class Application extends Controller {

    public static void index() {
    	renderArgs.put("fourSquareConnectUrl", Parameters.fourSquareConnectUrl());
        render();
    }
    
    public static void authenticate(String code) {
    	Gson gson = new Gson();
    	AccessToken access_token = gson.fromJson(
    			HTTPRequestPoster.sendGetRequest(Parameters.accessTokenRequestUrl(), Parameters.accessTokenRequestUrlParameters(code))
    			,AccessToken.class);
    	renderArgs.put("token", access_token.getToken());
    	
    	FourSquareUser user = AuthenticationRequest.getFourSquareUserByToken(access_token.getToken());
    	renderArgs.put("firstName", user.getFirstName());
    	render();
    }

    public static void players() {
        Collection<Player> players = Player.findAll();
        render(players);
    }

    public static void test(@Required String email) {
        if(validation.hasErrors()) {
        	return;
        }
        new Player(email).insert();
        players();
    }
    
}