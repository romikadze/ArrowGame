package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import flyingarrow.game.MainClass;


public class MenuScreen implements Screen {

    Sprite menuScreen;
    SpriteBatch batch;
    OrthographicCamera camera;
    Texture menuScreenTexture;


    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 1600);
        menuScreenTexture = new Texture("menu_screen.png");
        menuScreen = new Sprite(menuScreenTexture);
        menuScreen.setOrigin(0, 0);
        menuScreen.setPosition(0, 0);
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        menuScreen.draw(batch);
        batch.end();


        isTouched();

    }

    public void isTouched() {
        Vector3 pos = new Vector3();
        if (Gdx.input.justTouched()) {
            pos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(pos);
            if (pos.x >= 330 && pos.x <= 630 && pos.y >= 890 && pos.y <= 970) {
                MainClass.mainClass.setScreen(new GameScreen());
            } else if (pos.x >= 330 && pos.x <= 630 && pos.y >= 680 && pos.y <= 760) {
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void resize(int width, int height) {

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
        batch.dispose();
        menuScreenTexture.dispose();
    }
}
