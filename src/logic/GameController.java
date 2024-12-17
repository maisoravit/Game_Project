package logic;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import logic.actions.Action;
import logic.characters.BaseCharacter;
import logic.characters.isekai.Kona;
import logic.characters.isekai.Lafy;
import logic.characters.isekai.Mewten;
import logic.characters.isekai.Reirin;
import logic.effects.Effect;
import logic.effects.TriggerEvent;
import logic.levels.LevelManager;
import logic.players.Player;
import logic.skillCards.SkillCard;
import logic.team.Team;
import manager.SoundManager;
import router.Config;
import router.Router;
import ui.battle_scene.BattleSceneController;
import ui.battle_scene.components.*;

import java.util.ArrayList;
import java.util.Random;

public class GameController {
    private static GameController instance;
    private BattleBoard battleBoard;
    private AnchorPane battleSceneAnchorPane;
    private BattleSceneController battleSceneController;
    private PlayerControlBarController playerControlBarController;
    private Team playerTeam;
    private Team enemyTeam;
    private Player player;
    private boolean isGameOver;
    private boolean isWin;
    private Phase currentPhase;
    private Action currentAction;
    private int round;
    private Timer timer;
    private Thread thread;

    private GameController() {}

    public void initGame() {
        isWin = false;
        isGameOver = false;
        round = 0;
        initPlayer();
        initPlayerTeam();
        initEnemyTeam();
        SoundManager.getInstance().playBGM(SoundManager.BGM.BATTLE_SCENE);
        timer = new Timer();
        play();
    }

    private void initPlayer() {
        player = new Player();
    }

    private void initPlayerTeam() {
        playerTeam = new Team("Player");
        playerTeam.addCharacter(new Mewten(), Team.Line.FRONT);
        playerTeam.addCharacter(new Reirin(), Team.Line.MID);
        playerTeam.addCharacter(new Kona(), Team.Line.MID);
        playerTeam.addCharacter(new Lafy(), Team.Line.REAR);
    }

    private void initEnemyTeam() {
        enemyTeam = LevelManager.getInstance().getLevel().getEnemyTeam();
    }

    public void setBattleBoard(BattleBoard battleBoard) {
        this.battleBoard = battleBoard;
        battleBoard.getController().getBatTeam1().getController().setTeam(playerTeam);
        battleBoard.getController().getBatTeam2().getController().setTeam(enemyTeam);
    }

    public BattleBoard getBattleBoard() {
        return battleBoard;
    }

    public BattleSceneController getBattleSceneController() {
        return battleSceneController;
    }

    public void setBattleSceneController(BattleSceneController battleSceneController) {
        this.battleSceneController = battleSceneController;
        battleSceneController.removeCurrentNextButton();
    }

    public void play() {
        manageTeam();
    }

    public void manageTeam() {
        currentPhase = Phase.manageTeam;
        // manage team
    }

    public void startPhase() {
        thread = new Thread(() -> {
            try {
                System.out.println("=======================");
                System.out.println("Round " + ++round);
                currentPhase = Phase.start;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        battleSceneController.updatePhaseDisplay("Your Turn", "Start Phase");
                    }
                });

                playerTeam.getMembers().forEach((character) -> character.setActionThisTurn(false));

                // effect system
                reduceEffectDuration();
                Thread.sleep(500);
                runEffectByEvent(TriggerEvent.NEW_TURN);

                if (playerTeam.getMembers().isEmpty()) {
                    isGameOver = true;
                    gameOver();
                } else {
                    player.setCurrentMana(player.getCurrentMana() + 10);
                    Thread.sleep(1000);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // handle later
                            // set player mana bar according to current mana
                            playerControlBarController.updateManaBar(player.getCurrentMana());
                        }
                    });
                    Thread.sleep(500);
                    drawPhase();
                }
            } catch (InterruptedException e) {
                System.out.println("error! start phase thread is interrupted");
            }
        });
        thread.start();
    }

    public void drawPhase() {
        thread = new Thread(() -> {
            try {
                currentPhase = Phase.draw;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        battleSceneController.removeCurrentNextButton();
                        battleSceneController.updatePhaseDisplay("Your Turn", "Draw Phase");
                    }
                });
                Thread.sleep(1000);
                // random skill card
                randomSkillCard();
                Thread.sleep(200);
                actionPhase();

            } catch (InterruptedException e) {
                System.out.println("error! draw phase thread is interrupted");
            }
        });
        thread.start();
    }

    public void actionPhase() {
        thread = new Thread(() -> {
            try {
                timer.setDuration(45000);
                timer.start(() -> {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            battleSceneController.setPercentTimerRect(timer.getPercentage());
                            if (Router.getCurrentAppScene() != Config.AppScene.BATTLE) {
                                timer.stop();
                            }
                        }
                    });
                }, () -> {
                    enemyTurn();
                });
                currentPhase = Phase.action;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        battleSceneController.removeCurrentNextButton();
                        battleSceneController.updatePhaseDisplay("Your Turn", "Action Phase");
                        battleSceneController.setEndTurnButton(Phase.enemyTurn);
                    }
                });
            } catch (Exception err) {
                System.out.println("error! some exception has occured in action phase");
            }
        });
        thread.start();
    }

    public void enemyTurn() {
        timer.stop();
        thread = new Thread(() -> {
            try {
                currentPhase = Phase.enemyTurn;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        battleSceneController.removeCurrentNextButton();
                        battleSceneController.updatePhaseDisplay("Enemy Turn", "Enemy Phase");
                    }
                });
                enemyTeam.getMembers().forEach((character) -> character.setActionThisTurn(false));
                reduceEffectDuration();
                Thread.sleep(500);
                runEffectByEvent(TriggerEvent.NEW_TURN);
                Thread.sleep(1000);

                // enemy combat logic
                Random random = new Random();
                ArrayList<BaseCharacter> selectedCharacters = new ArrayList<>();
                ArrayList<BaseCharacter> unselectedCharacters = new ArrayList<>();
                enemyTeam.getMembers().forEach(character -> unselectedCharacters.add(character));
                BaseCharacter selected1 = unselectedCharacters.get(random.nextInt(unselectedCharacters.size()));
                selectedCharacters.add(selected1);
                unselectedCharacters.remove(selected1);
                if (!unselectedCharacters.isEmpty()) {
                    BaseCharacter selected2 = unselectedCharacters.get(random.nextInt(unselectedCharacters.size()));
                    selectedCharacters.add(selected2);
                    unselectedCharacters.remove(selected2);
                }

                selectedCharacters.forEach(character -> {
                    try {
                        character.performAction(character.getActionList().get(
                                character.getActionPattern().get(character.getActionPatternIndex() % character.getActionPattern().size())
                        ));
                        character.setActionPatternIndex(character.getActionPatternIndex() + 1);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("error! thread interrupted during enemy do action");
                        throw new RuntimeException(e);
                    }
                });

                // go to player turn again
                GameController.getInstance().startPhase();
            } catch (InterruptedException e) {
                System.out.println("error! enemyTurn thread is interrupted");
            }
        });
        thread.start();
    }

    public void gameOver() {
        System.out.println("Game Over!!");
        battleSceneController.setGameEndPopUp(false);
    }

    public void gameWin() {
        System.out.println("Win!!");
        battleSceneController.setGameEndPopUp(true);
    }

    public void showTargetCharacters() {
        if (currentPhase == Phase.action) {
            battleBoard.getController().getAllCards().forEach(card -> {
                if (!card.getController().getCharacter().isTargetable()) {
                    card.getController().fillBlack();
                }
            });
        }
    }

    public void setCharactersColorToNormal() {
        getBattleBoard().getController().getAllCards().forEach(card -> {
            card.getController().fillNormalColor();
        });
    }

    public boolean runEffectByEvent(TriggerEvent triggerEvent) {
        boolean hasAny = false;
        if (triggerEvent == TriggerEvent.NEW_TURN) {
            if (currentPhase == Phase.start) {
                for (int i = 0; i < getPlayerTeam().getMembers().size(); i++) {
                    BaseCharacter character = getPlayerTeam().getMembers().get(i);
                    for (int j = 0; j < character.getStatusEffect().size(); j++) {
                        Effect effect = character.getStatusEffect().get(j);
                        if (effect.getTriggerEvent() == TriggerEvent.NEW_TURN) {
                            effect.activate();
                            hasAny = true;
                        }
                    }
                }
            } else if (currentPhase == Phase.enemyTurn) {
                for (int i = 0; i < getEnemyTeam().getMembers().size(); i++) {
                    BaseCharacter character = getEnemyTeam().getMembers().get(i);
                    for (int j = 0; j < character.getStatusEffect().size(); j++) {
                        Effect effect = character.getStatusEffect().get(j);
                        if (effect.getTriggerEvent() == TriggerEvent.NEW_TURN) {
                            effect.activate();
                            hasAny = true;
                        }
                    }
                }
            }
        }
        return hasAny;
    }

    public boolean runEffectByEvent(TriggerEvent triggerEvent, BaseCharacter character) {
        boolean hasAny = false;
        for (int i = 0; i < character.getStatusEffect().size(); i++) {
            Effect effect = character.getStatusEffect().get(i);
            if (effect.getTriggerEvent() == triggerEvent) {
                effect.activate();
                hasAny = true;
            }
        }
        return hasAny;
    }

    public void reduceEffectDuration() {
        if (currentPhase == Phase.start) {
            for (int i = 0; i < getPlayerTeam().getMembers().size(); i++) {
                BaseCharacter character = getPlayerTeam().getMembers().get(i);
                for (int j = 0; j < character.getStatusEffect().size(); j++) {
                    character.getStatusEffect().get(j).runDownDuration();
                }
            }
        } else if (currentPhase == Phase.enemyTurn) {
            for (int i = 0; i < getEnemyTeam().getMembers().size(); i++) {
                BaseCharacter character = getEnemyTeam().getMembers().get(i);
                for (int j = 0; j < character.getStatusEffect().size(); j++) {
                    character.getStatusEffect().get(j).runDownDuration();
                }
            }
        }
    }

    public void randomSkillCard() {
        ArrayList<Action> skillActions = new ArrayList<>();
        for (int i = 0; i < getPlayerTeam().getMembers().size(); i++) {
            skillActions.add(getPlayerTeam().getMembers().get(i).getActionList().get(1));
        }
        Random random = new Random();
        SkillCard skillCard = new SkillCard(skillActions.get(random.nextInt(skillActions.size())));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                playerControlBarController.setSkillCard(skillCard);
            }
        });
    }

    public static void createInstance() {
        if (instance == null) {
            instance = new GameController();
        }
    }

    public static GameController getInstance() {
        return instance;
    }

    public Team getPlayerTeam() {
        return playerTeam;
    }

    public void setPlayerTeam(Team playerTeam) {
        this.playerTeam = playerTeam;
    }

    public Team getEnemyTeam() {
        return enemyTeam;
    }

    public void setEnemyTeam(Team enemyTeam) {
        this.enemyTeam = enemyTeam;
    }

    public static boolean isEnemyTeam(BaseCharacter character) {
        return getInstance().enemyTeam.getMembers().contains(character);
    }

    public static boolean isEnemyTeam(Team team) {
        return getInstance().enemyTeam.equals(team) || getInstance().enemyTeam.getTeamName().equals(team.getTeamName());
    }

    public static boolean isEnemyTeam(BattleTeam battleTeam) {
        return getInstance().getBattleBoard().getController().getBatTeam2().equals(battleTeam);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(Phase currentPhase) {
        this.currentPhase = currentPhase;
    }

    public Action getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(Action currentAction) {
        this.currentAction = currentAction;
    }

    public PlayerControlBarController getPlayerControlBarController() {
        return playerControlBarController;
    }

    public void setPlayerControlBarController(PlayerControlBarController playerControlBarController) {
        this.playerControlBarController = playerControlBarController;
    }

    public void stopThread() {
        if (thread != null) {
            while (thread.isAlive() && !thread.isInterrupted()) {
                System.out.printf("Thread %s is still alive\n", thread.getName());
                thread.interrupt();
            }
        }
    }
}
