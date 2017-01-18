package info.demmonic.hdrs.media;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.utils.Disposable;
import info.demmonic.hdrs.Rt3;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by chris on 1/5/17.
 */
@RequiredArgsConstructor
@Getter
public class SpriteHelper implements Disposable {

    private final Pixmap pixmap;

    private final Texture texture;

    private final Sprite sprite;

    public SpriteHelper(Pixmap pixmap) {
        this.pixmap = pixmap;
        this.texture = new Texture(new PixmapTextureData(pixmap, pixmap.getFormat(), false, false));
        this.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.sprite = new Sprite(texture);
    }

    public void draw(int x, int y) {
        draw(Rt3.batch, x, y);
    }

    public void draw(SpriteBatch batch, int x, int y) {
        draw(batch, x, y, 1f, false, true);
    }

    public void draw(SpriteBatch batch, int x, int y, float alpha) {
        draw(batch, x, y, alpha, false, true);
    }

    public void draw(SpriteBatch batch, int x, int y, float alpha, boolean flipX, boolean flipY) {
        if (sprite.getX() != x || sprite.getY() != y) {
            sprite.setPosition(x, y);
        }

        sprite.setFlip(flipX, flipY);
        sprite.setAlpha(alpha);
        sprite.draw(batch);

        batch.flush();
    }

    @Override
    public void dispose() {
        pixmap.dispose();
        texture.dispose();
    }
}
