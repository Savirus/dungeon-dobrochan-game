/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dobrochan.dungeon.gamestates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Admin
 */
public class GamePoolState extends BasicGameState
{
	private int id;

	public GamePoolState(int id)
	{
		this.id = id;
	}

	@Override
	public int getID()
	{
		return id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException
	{

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
	{

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{

	}

}
