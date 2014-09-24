package com.hubclub.hubjump.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Transform;
import com.hubclub.hubjump.characters.Ninja.State;
import com.hubclub.hubjump.worldenviroment.Enviroment;

public class NinjaContactListener implements ContactListener {
	Ninja ninja;
	Enviroment env;
	
	public NinjaContactListener (Ninja ninja , Enviroment env){
		this.ninja= ninja;
		this.env = env;
	}
	
	@Override
	public void beginContact(Contact contact) {
		if (ninja.state == State.HANGING){
			contact.setEnabled(false);
			System.out.println("CONTACT LISTENER: WALL COLISION IGNORED");
		}
		
	//	if (contact.getFixtureA().getBody().getUserData() != null)
		if (contact.getFixtureA().getBody().getUserData().equals(ninja))
			ninja.startContact();
	//	if (contact.getFixtureB().getBody().getUserData() != null)
		if (contact.getFixtureB().getBody().getUserData().equals(ninja))
			ninja.startContact();
			
		
		if (contact.getFixtureA().getDensity() == 4){
			verify( contact.getFixtureA() , contact.getFixtureB() );
		}
		if (contact.getFixtureB().getDensity() == 4){
			verify( contact.getFixtureB() , contact.getFixtureA() );
		}
		
		
	}

	@Override
	public void endContact(Contact contact) {
	/*	if (contact.getFixtureA().getBody().getUserData() != null)
			if (contact.getFixtureA().getBody().getUserData().equals(ninja))
				ninja.endContact();
		if (contact.getFixtureB().getBody().getUserData() != null)
			if (contact.getFixtureB().getBody().getUserData().equals(ninja))
				ninja.endContact();*/
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	
	}
	
	//this code pulls the position and height of the window, and then checks if it should break
	void verify(Fixture window, Fixture ninja){
		Transform transform = window.getBody().getTransform();
		PolygonShape shape = (PolygonShape) window.getShape();
		Vector2 vec = new Vector2();
		Vector2 temp = new Vector2();
		float height;
		
		shape.getVertex(1, vec);
		shape.getVertex(2, temp);
		height = temp.y - vec.y;
			
		shape.getVertex(3, vec);
		transform.mul(vec);
			
		// now we have vec (the position of the window) and the height of the window
			
		if ( inBetween( vec.y-height , this.ninja.getFeetPos() , vec.y) ||
			 inBetween( vec.y-height , this.ninja.getArmsPos() , vec.y)	){
			env.queueFixtureDeletion( window, ninja.getBody().getLinearVelocity() );
			env.stopContactListener();
			Gdx.input.setInputProcessor(null);
		}
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
	boolean inBetween(float a, float b, float c){
		//System.out.println(a + " " + b + " " + c);
		if (a<b && b<c)
			return true;
		return false;
	}

}
