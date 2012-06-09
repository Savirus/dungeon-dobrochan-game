
package ru.dobrochan.dungeon.core.renderobjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import ru.dobrochan.dungeon.content.ContentManager;
import ru.dobrochan.dungeon.content.ContentPaths;

/**
 *
 * @author SkinnyMan
 */
public class SubstrateRenderObject extends EntityRenderObject
{
	private static final Image image = ContentManager.getInstance().
			getImage(ContentPaths.COLORS_LIGHT_BACK, "Mono");

	private Color color;

	public SubstrateRenderObject(Color color)
	{
		this.color = color;
	}

	@Override
	public void stateChanged()
	{
		super.stateChanged();
	}

	@Override
	public void render(Graphics g)
	{
		g.drawImage(image, getOwnerX(), getOwnerY(), color);
	}

	@Override
	public void update(int delta)
	{

	}

}
