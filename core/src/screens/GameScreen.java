package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.utils.Box2DBuild;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.codeandweb.physicseditor.PhysicsShapeCache;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import flyingarrow.game.MainClass;
import objects.Arrow;
import objects.Ground;

public class GameScreen implements Screen {


    final float METERS_TO_PIXELS = 10;
    final float WIDTH = 96 * METERS_TO_PIXELS;
    final float HEIGHT = 160 * METERS_TO_PIXELS;
    final float SCREEN_SCALE = ((float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth()) / (200f / 120);
    Music backgroundMusic;
    SpriteBatch batch;
    World world;
    Box2DDebugRenderer debugRenderer;

    OrthographicCamera camera;
    ExtendViewport viewport;

    PhysicsShapeCache physicsShapeCache;
    Ground ground;
    Arrow arrow;


    @Override
    public void show() {
        ground = new Ground();
        arrow = new Arrow();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);
        batch = new SpriteBatch();
        world = new World(new Vector2(0, -12), true);
        debugRenderer = new Box2DDebugRenderer();
        viewport = new ExtendViewport(WIDTH, HEIGHT, WIDTH * 2, HEIGHT * 2, camera);
        physicsShapeCache = new PhysicsShapeCache("bodies.xml");
        arrow.createBody(world);
        ground.init(world);
        ground.createFirstGround();

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("BackgroundMusic.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.2f);
        backgroundMusic.play();

    }

    @Override
    public void render(float delta) {
        world.step(1 / 60f, 6, 2);

        Gdx.gl.glClearColor(0.38f, 0.86f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        arrow.render();
        updateCamera();

        batch.begin();
        drawSprites();
        batch.end();

        if (Gdx.input.justTouched()) {
            arrow.force();
        }

        ground.replaceGround((arrow.getArrowBody().getPosition().y));
        lose();

        //debugRenderer.render(world, camera.combined);// отключить dispose world

    }

    public void drawSprites() {
        ground.drawGround(batch);
        arrow.drawArrow(batch);
    }

    public synchronized void lose() {
        if (camera.position.y / METERS_TO_PIXELS > arrow.getArrowBody().getPosition().y + 85 * SCREEN_SCALE + arrow.getCurrentArrowHeight()) {
            MainClass.mainClass.setScreen(new MenuScreen());
            backgroundMusic.stop();
        }
    }

    public void updateCamera() {
        if (camera.position.y < arrow.getArrowBody().getPosition().y * METERS_TO_PIXELS + arrow.getCurrentArrowHeight() / 2)
            camera.position.set(camera.position.x, arrow.getArrowBody().getPosition().y * METERS_TO_PIXELS + arrow.getCurrentArrowHeight() / 2, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();//Вылетает при дебаггинге
        batch.dispose();
        physicsShapeCache.dispose();
        ground.dispose();
        arrow.dispose();
        backgroundMusic.dispose();
    }
}
