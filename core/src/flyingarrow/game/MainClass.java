package flyingarrow.game;

import com.badlogic.gdx.Game;

import screens.LoginScreen;

public class MainClass extends Game {

    public static MainClass mainClass;
    @Override
    public void create() {
        mainClass = this;
        setScreen(new LoginScreen());

    }

}
