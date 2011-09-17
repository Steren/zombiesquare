package controllers;

import play.*;
import play.data.validation.Required;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
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