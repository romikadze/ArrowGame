package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.codeandweb.physicseditor.PhysicsShapeCache;

import java.util.ArrayList;
import java.util.Collections;

public class Ground {
    ArrayList<Body> groundLR = new ArrayList<>();
    ArrayList<Body> platformsL = new ArrayList<>();
    ArrayList<Body> platformsR = new ArrayList<>();
    Texture mapTexture = new Texture("map.png");
    Texture mapLRTexture1 = new Texture("mapLR1.png");
    Texture mapLRTexture2 = new Texture("mapLR2.png");
    Texture mapLRTexture3 = new Texture("mapLR3.png");
    Texture platformLTexture = new Texture("platformL.png");
    Texture platformRTexture = new Texture("platformR.png");
    SpriteBatch batch;
    Body ground, groundLR1, groundLR2, groundLR3, platformL1, platformL2, platformL3, platformR1, platformR2, platformR3;
    Sprite[] groundLRSprite;
    Sprite groundSprite;
    Sprite platformLSprite;
    Sprite platformRSprite;
    PhysicsShapeCache physicsShapeCache;
    public final float SCALE = 0.2f;
    public final float metersToPixels = 10;
    double platformLY, platformRY;


    public void replaceGround(float y) {

        if (y > groundLR.get(1).getPosition().y + 80) {
            Collections.rotate(groundLR, 1);
            groundLR.get(0).setTransform(0, groundLR.get(2).getPosition().y + 320, 0);
            generatePlatformsY();
            Collections.rotate(platformsL, 1);
            Collections.rotate(platformsR, 1);
            platformsL.get(0).setTransform(8, (float) (platformLY) + groundLR.get(2).getPosition().y + 160, 0);
            platformsR.get(0).setTransform(38f, (float) (platformRY) + groundLR.get(2).getPosition().y + 160, 0);
        }

    }

    public void createFirstGround() {
        ground.setTransform(0, 0, 0);
        groundLR1.setTransform(0, 160, 0);
        groundLR2.setTransform(0, 320, 0);
        groundLR3.setTransform(0, 480, 0);
        groundLR.add(groundLR3);
        groundLR.add(groundLR2);
        groundLR.add(groundLR1);
        createPlatforms();
    }

    public void createPlatforms() {
        generatePlatformsY();
        platformL1.setTransform(8, (float) (32 + platformLY * 0.8), 0);
        platformR1.setTransform(38f, (float) (32 + platformRY * 0.8), 0);
        generatePlatformsY();
        platformL2.setTransform(8, (float) (160 + platformLY), 0);
        platformR2.setTransform(38f, (float) (160 + platformRY), 0);
        generatePlatformsY();
        platformL3.setTransform(8, (float) (320 + platformLY), 0);
        platformR3.setTransform(38f, (float) (320 + platformRY), 0);
        platformsR.add(platformR3);
        platformsR.add(platformR2);
        platformsR.add(platformR1);
        platformsL.add(platformL3);
        platformsL.add(platformL2);
        platformsL.add(platformL1);
    }

    public void drawGround(SpriteBatch batch) {
        groundSprite.setOrigin(0, 0);
        groundSprite.setScale(2);
        groundSprite.draw(batch);


        groundLRSprite[0].setOrigin(0, 0);
        groundLRSprite[0].setScale(2);
        groundLRSprite[1].setOrigin(0, 0);
        groundLRSprite[1].setScale(2);
        groundLRSprite[2].setOrigin(0, 0);
        groundLRSprite[2].setScale(2);


        groundLRSprite[0].setPosition(groundLR1.getPosition().x * metersToPixels, groundLR1.getPosition().y * metersToPixels);
        groundLRSprite[0].draw(batch);

        groundLRSprite[1].setPosition(groundLR2.getPosition().x * metersToPixels, groundLR2.getPosition().y * metersToPixels);
        groundLRSprite[1].draw(batch);

        groundLRSprite[2].setPosition(groundLR3.getPosition().x * metersToPixels, groundLR3.getPosition().y * metersToPixels);
        groundLRSprite[2].draw(batch);


        platformLSprite.setOrigin(0, 0);
        platformRSprite.setOrigin(0, 0);
        platformLSprite.setScale(2);
        platformRSprite.setScale(2);

        platformLSprite.setPosition(platformL1.getPosition().x * metersToPixels, platformL1.getPosition().y * metersToPixels);
        platformRSprite.setPosition(platformR1.getPosition().x * metersToPixels, platformR1.getPosition().y * metersToPixels);
        platformLSprite.draw(batch);
        platformRSprite.draw(batch);

        platformLSprite.setPosition(platformL2.getPosition().x * metersToPixels, platformL2.getPosition().y * metersToPixels);
        platformRSprite.setPosition(platformR2.getPosition().x * metersToPixels, platformR2.getPosition().y * metersToPixels);
        platformLSprite.draw(batch);
        platformRSprite.draw(batch);

        platformLSprite.setPosition(platformL3.getPosition().x * metersToPixels, platformL3.getPosition().y * metersToPixels);
        platformRSprite.setPosition(platformR3.getPosition().x * metersToPixels, platformR3.getPosition().y * metersToPixels);
        platformLSprite.draw(batch);
        platformRSprite.draw(batch);
    }

    public void init(World world) {
        batch = new SpriteBatch();
        physicsShapeCache = new PhysicsShapeCache("bodies.xml");
        groundSprite = new Sprite(mapTexture);
        groundLRSprite = new Sprite[3];
        groundLRSprite[0] = new Sprite(mapLRTexture1);
        groundLRSprite[1] = new Sprite(mapLRTexture2);
        groundLRSprite[2] = new Sprite(mapLRTexture3);
        platformLSprite = new Sprite(platformLTexture);
        platformRSprite = new Sprite(platformRTexture);
        ground = physicsShapeCache.createBody("map", world, SCALE, SCALE);
        groundLR1 = physicsShapeCache.createBody("mapLR", world, SCALE, SCALE);
        groundLR2 = physicsShapeCache.createBody("mapLR", world, SCALE, SCALE);
        groundLR3 = physicsShapeCache.createBody("mapLR", world, SCALE, SCALE);
        platformL1 = physicsShapeCache.createBody("platformL", world, SCALE, SCALE);
        platformL2 = physicsShapeCache.createBody("platformL", world, SCALE, SCALE);
        platformL3 = physicsShapeCache.createBody("platformL", world, SCALE, SCALE);
        platformR1 = physicsShapeCache.createBody("platformR", world, SCALE, SCALE);
        platformR2 = physicsShapeCache.createBody("platformR", world, SCALE, SCALE);
        platformR3 = physicsShapeCache.createBody("platformR", world, SCALE, SCALE);
    }

    public void generatePlatformsY() {
        do {
            platformLY = Math.random() * 80;
            platformRY = Math.random() * 80;
        }
        while (Math.abs(platformLY - platformRY) < 30);
    }

    public void dispose() {
        mapTexture.dispose();
        mapLRTexture1.dispose();
        mapLRTexture2.dispose();
        mapLRTexture3.dispose();
        platformLTexture.dispose();
        platformRTexture.dispose();
        physicsShapeCache.dispose();
    }
}
