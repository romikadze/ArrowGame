package flyingarrow.game;

import com.badlogic.gdx.Game;

import screens.MenuScreen;

public class MainClass extends Game {

    @Override
    public void create() {

        setScreen(new MenuScreen(this));

    }

}
