
package ru.dobrochan.dungeon.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;
import ru.dobrochan.dungeon.Settings;
import ru.dobrochan.dungeon.content.ResourceManager;
import ru.dobrochan.dungeon.core.renderobjects.CellBacklightRenderObject;
import ru.dobrochan.dungeon.core.renderobjects.RenderObject;

/**
 * Представление игрового поля.
 *
 * @author SkinnyMan
 */
public class GameFieldView extends AbstractComponent
{
	private class CursorBacklight
	{
		private CellBacklightRenderObject rObj;
		GameFieldView field;

		public CursorBacklight(GameFieldView field)
		{
			this.field = field;
			rObj = new CellBacklightRenderObject();
			field.addRenderObject(rObj);
		}

		public void update(Cell cell)
		{
			rObj.setCell(cell);
		}

	}

	private static final int gridOffsetX = 98;
	private static final int gridOffsetY = 153;

	private int coordX;
	private int coordY;

	private int width;
	private int height;

	private int widthInCells = Settings.FIELD_WIDTH;
	private int heightInCells = Settings.FIELD_HEIGHT;

	private int cellWidth;
	private int cellHeight;
	private int cellCountX = 35;
	private int cellCountY = 16;

	private Image background;

	private GameField gameField;
	private IEntityContainer entitysContainer;

	private ArrayList<RenderObject> renderObjects;

	private CursorBacklight cursorBacklight;

	public GameFieldView(GUIContext context)
	{
		super(context);
		try
		{
			Cursor cursor = ResourceManager.getInstance().getCursor("CURSOR_MAIN");
			try
			{
				Mouse.setNativeCursor(cursor);
			}
			catch (LWJGLException ex)
			{
				Logger.getLogger(GameFieldView.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		catch (Exception ex)
		{
			Logger.getLogger(GameFieldView.class.getName()).log(Level.SEVERE, null, ex);
		}


		width = 1085;
		height = 496;
		cellWidth = 31;
		cellHeight = 31;
		renderObjects = new ArrayList<RenderObject>();
		try
		{
			background = new Image(width, height);
		}
		catch (SlickException ex)
		{
			Logger.getLogger(GameFieldView.class.getName()).log(Level.SEVERE, null, ex);
		}
		cursorBacklight = new CursorBacklight(this);
	}

	public void rebuildBackground()
	{
		FieldBackgroundBuilder builder = new FieldBackgroundBuilder();
		builder.setGameField(gameField);
		background = builder.buildBackground();
	}

	@Override
	public void setLocation(int x, int y)
	{
		coordX = gridOffsetX;
		coordY = gridOffsetY;
	}

	@Override
	public int getX() { return coordX; }

	@Override
	public int getY() { return coordY; }

	@Override
	public int getWidth() { return width; }

	@Override
	public int getHeight() { return height; }

	public int getCellWidth() { return cellWidth; }

	public int getCellHeight() { return cellHeight; }

	/**
	 * Добавляет RenderObject в процесс отрисовки.
	 *
	 * @param renderObject
	 */
	public void addRenderObject(RenderObject renderObject)
	{
		renderObject.setGameFieldView(this);
		renderObject.stateChanged();
		renderObjects.add(renderObject);
		Collections.sort(renderObjects, new RenderObjectComparator());
	}

	private class RenderObjectComparator implements Comparator<RenderObject>
	{
		@Override
		public int compare(RenderObject t, RenderObject t1)
		{
			return t.getLayer() - t1.getLayer();
		}
	}

	/**
	 * Удаляет RenderObject из процесса отрисовки.
	 *
	 * @param renderObject
	 */
	public void removeRenderObject(RenderObject renderObject)
	{
		renderObjects.remove(renderObject);
	}

	/**
	 * Вызывает обновление RenderObject'ов игрового поля.
	 *
	 * @param delta
	 */
	public void update(int delta)
	{
		for (int i = 0; i < renderObjects.size(); i++)
		{
			renderObjects.get(i).update(delta);
		}
//		for (RenderObject rObj : renderObjects)
//		{
//			rObj.update(delta);
//		}
	}

	/**
	 * Вызывает перерисовку игрового поля.
	 *
	 * @param container
	 * @param g
	 * @throws SlickException
	 */
	@Override
	public void render(GUIContext container, Graphics g) throws SlickException
	{
		g.drawImage(background, 0, 0);
		for (RenderObject rObj : renderObjects)
		{
			rObj.render(g);
		}

	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy)
	{
		Cell c = getCellByXY(newx, newy);
		if (c != null)
		{
			EntitiesHalper helper = new EntitiesHalper(entitysContainer);
			IEntity entityFromXY = helper.getEntityFromXY(c.X, c.Y);
			if (entityFromXY != null)
			{
				int s = 0;
				System.out.println("CX=" + c.X + " CY=" + c.Y);
			}
		}
		cursorBacklight.update(c);
	}

	@Override
	public void mousePressed(int button, int x, int y)
	{
		Cell c = getCellByXY(x, y);
		System.out.println(c.X + " " + c.Y);
	}



	public Cell getCellByXY(int x, int y)
	{
		int cx = (x - coordX)/cellWidth;
		int cy = (y - coordY)/cellHeight;
		if (cx < 0 || cx >= cellCountX || cy < 0 || cy >= cellCountY)
			return null;
		return new Cell(cx, cy);
	}

	public IEntityContainer getEntitysContainer() { return entitysContainer; }

	public void setEntitysContainer(IEntityContainer entitysContainer) {
		this.entitysContainer = entitysContainer; }

	public GameField getGameField() { return gameField; }

	public void setGameField(GameField gameField) { this.gameField = gameField; }

}
