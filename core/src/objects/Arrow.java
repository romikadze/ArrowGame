package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.codeandweb.physicseditor.PhysicsShapeCache;


public class Arrow {
    final float SCALE = 0.075f;
    PhysicsShapeCache physicsShapeCache;
    Body arrowBody;
    Texture arrowTexture = new Texture("arrow.png");
    Sprite arrowSprite = new Sprite(arrowTexture);
    Sound arrowSound = Gdx.audio.newSound(Gdx.files.internal("ArrowSound.mp3"));

    public Arrow() {
        arrowSprite.setSize(arrowSprite.getWidth() * 0.75f, arrowSprite.getHeight() * 0.75f);
        arrowSprite.setOrigin(0, 0);
    }

    public void render() {
        angleFalling();
    }

    public void force() {
        float FORCE_SCALE = 100000;
        arrowBody.applyForceToCenter(FORCE_SCALE * (float) Math.cos(arrowBody.getAngle()),
                FORCE_SCALE * (float) Math.sin(arrowBody.getAngle()), false);
        arrowSound.play();
    }

    public void drawArrow(SpriteBatch batch) {
        arrowSprite.setPosition(arrowBody.getPosition().x * 10, arrowBody.getPosition().y * 10);
        arrowSprite.setRotation((float) Math.toDegrees(arrowBody.getAngle()));
        arrowSprite.draw(batch);
    }

    public void createBody(World world) {
        physicsShapeCache = new PhysicsShapeCache("bodies.xml");
        arrowBody = physicsShapeCache.createBody("arrow", world, SCALE, SCALE);
        arrowBody.setTransform((float) (20 + Math.random() * 10), (float) (20 + Math.random() * 10), (float) Math.random());
        arrowBody.setUserData("arrowBody");
    }

    public void angleFalling() {
        float angle;
        angle = (float) Math.toDegrees(arrowBody.getAngle());
        angle = Math.abs(angle);
        angle %= 360;
        if (angle > 90 && angle < 270)
            arrowBody.applyAngularImpulse(0.12f, true);
        else
            arrowBody.applyAngularImpulse(-0.12f, true);
    }

    public Body getArrowBody() {
        return arrowBody;
    }

    public float getCurrentArrowHeight() {
        return (float) (getArrowLength() * Math.abs(Math.sin(getArrowBody().getAngle())));
    }

    public float getArrowLength() {
        return arrowSprite.getWidth();
    }

    public void dispose() {
        arrowTexture.dispose();
        physicsShapeCache.dispose();
        arrowSound.dispose();
    }
}
