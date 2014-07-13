package com.hubclub.Hubjump.characters;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class NinjaContactListener implements ContactListener {
	Ninja ninja;
	public NinjaContactListener (Ninja ninja){
		this.ninja= ninja;
	}
	@Override
	public void beginContact(Contact contact) {
		if (contact.getFixtureA().getBody().getUserData() != null)
			if (contact.getFixtureA().getBody().getUserData().equals(ninja))
				ninja.startContact();
		if (contact.getFixtureB().getBody().getUserData() != null)
			if (contact.getFixtureB().getBody().getUserData().equals(ninja))
				ninja.startContact();
		
	}

	@Override
	public void endContact(Contact contact) {
		if (contact.getFixtureA().getBody().getUserData() != null)
			if (contact.getFixtureA().getBody().getUserData().equals(ninja))
				ninja.endContact();
		if (contact.getFixtureB().getBody().getUserData() != null)
			if (contact.getFixtureB().getBody().getUserData().equals(ninja))
				ninja.endContact();
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
