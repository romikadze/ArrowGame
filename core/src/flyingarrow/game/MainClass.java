package flyingarrow.game;

import com.badlogic.gdx.Game;

import screens.LoginPage;
import screens.MenuScreen;

public class MainClass extends Game {

    public static MainClass mainClass;
    @Override
    public void create() {
        mainClass = this;
        setScreen(new LoginPage());

    }

}
