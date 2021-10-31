package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


import DB.DatabaseTest;
import Toast.Toast;
import flyingarrow.game.MainClass;


public class LoginPage implements Screen {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture loginScreenTexture;
    private Sprite loginScreenSprite;
    private String login = "";
    private String password = "";
    private boolean isAuthorizationSuccessful = false;
    private boolean reset = false;
    private DatabaseTest db;
    BitmapFont font;
    Toast toast;


    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 1600);
        loginScreenTexture = new Texture("login_screen.png");
        loginScreenSprite = new Sprite(loginScreenTexture);
        loginScreenSprite.setOrigin(0, 0);
        loginScreenSprite.setPosition(0, 0);

        font = new BitmapFont();
        Toast.ToastFactory toastFactory = new Toast.ToastFactory.Builder().font(font).build();
        toast = toastFactory.create("Login already taken", Toast.Length.LONG);

        db = new DatabaseTest();
    }

    private void authorize(boolean isLogin) {
        //boolean isLogin = login.equals("");
        Input.TextInputListener authorizeListener;

        if (isLogin) {
            authorizeListener = new Input.TextInputListener() {
                @Override
                public void input(String input) {
                    login = input;
                    authorize(false);
                }

                @Override
                public void canceled() {

                }
            };
            Gdx.input.getTextInput(authorizeListener, "Your login: ", "", "login");
        } else {
            authorizeListener = new Input.TextInputListener() {
                @Override
                public void input(String input) {
                    password = input;
                    if (db.readFromDB(login).equals(password))
                        isAuthorizationSuccessful = true;
                }

                @Override
                public void canceled() {

                }
            };
            Gdx.input.getTextInput(authorizeListener, "Your password: ", "", "password");
        }
    }

    private void register(boolean isLogin) {
        Input.TextInputListener authorizeListener;

        if (isLogin) {
            authorizeListener = new Input.TextInputListener() {
                @Override
                public void input(String input) {
                    login = input;
                    register(false);
                }

                @Override
                public void canceled() {

                }
            };
            Gdx.input.getTextInput(authorizeListener, "Your login: ", "", "login");
        } else {
            authorizeListener = new Input.TextInputListener() {
                @Override
                public void input(String input) {
                    password = input;
                    if (db.readFromDB(login).equals("LoginNotFound")) {
                        db.writeToDB(login, password);
                    } else {
                        Gdx.app.log("TAG", "Login taken");
                    }
                }

                @Override
                public void canceled() {

                }
            };
            Gdx.input.getTextInput(authorizeListener, "Your password: ", "", "password");
        }
    }


    public void isTouched() {
        //db.clearTable();
        Vector3 pos = new Vector3();
        if (Gdx.input.justTouched()) {
            pos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(pos);
            if (pos.x >= 170 && pos.x <= 470 && pos.y >= 720 && pos.y <= 820) {
                isAuthorizationSuccessful = false;
                login = "";
                password = "";
                authorize(true);


            } else if (pos.x >= 480 && pos.x <= 780 && pos.y >= 720 && pos.y <= 820) {
                login = "";
                password = "";
                register(true);

            } else if (pos.x >= 0 && pos.x <= 20 && pos.y >= 0 && pos.y <= 20) {
                reset = true;

            }else if (pos.x >= 940 && pos.x <= 960 && pos.y >= 1580 && pos.y <= 1600 && reset) {
                reset = false;
                db.clearTable();
            }
        }

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        isTouched();
        if (isAuthorizationSuccessful) {
            MainClass.mainClass.setScreen(new MenuScreen());
        }

        toast.render(Gdx.graphics.getDeltaTime());

        batch.begin();


        loginScreenSprite.draw(batch);
        batch.end();


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
        loginScreenTexture.dispose();
        db.dispose();
    }


}
