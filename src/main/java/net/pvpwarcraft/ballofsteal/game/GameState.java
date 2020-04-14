package net.pvpwarcraft.ballofsteal.game;

public enum GameState {

    LOBBY,
    IN_GAME,
    DEATHMATCH,
    END;

    private static GameState gameState;

    public static void setState(GameState state){
        gameState = state;
    }

    public static boolean isState(GameState state){
        return gameState == state;
    }

}
