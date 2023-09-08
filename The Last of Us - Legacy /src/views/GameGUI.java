package views;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.characters.Direction;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.geometry.Pos;
import javafx.event.EventHandler;

public class GameGUI {

    private static Scene scene;
    Stage primaryStage;
    public static Hero currentHero;
    public static BorderPane borderPane = new BorderPane();
    public static Scene getScene() {
		return scene;
	}
    
    public static VBox heroesInformation(Hero hero){
		VBox root2 = new VBox();
		Text t = new Text("Hero Name: " + hero.getName());
		Text t1 = new Text("");
		Text t2 = new Text("Hero Current Helth Points: " + hero.getCurrentHp());
		Text t22 = new Text("Hero Max Helth Points: " + hero.getMaxHp());
		Text t3 = new Text("Hero Attack Damage: " + hero.getAttackDmg());
		Text t4 = new Text("Hero Available Action Points: " + hero.getActionsAvailable());
		Text t44 = new Text("Hero Max Action Points: " + hero.getMaxActions());
		Text t5 = new Text("The amount of Supplies: " + hero.getSupplyInventory().size());
		Text t6 = new Text("The amount of Vaccines: " + hero.getVaccineInventory().size());
		Text t7 = new Text("Target: " + (hero.getTarget()==null?"null":hero.getTarget().getName()));
		if(hero instanceof Fighter) {
			t1 = new Text("Hero Type: Fighter");
		}else if(hero instanceof Medic) {
			t1 = new Text("Hero Type: Medic");
		}else if(hero instanceof Explorer) {
			t1 = new Text("Hero Type: Explorer");
		}
		GridPane grid = new GridPane();
		grid.add(t1, 0, 0);
		root2.getChildren().addAll(t, t1, t2,t22, t3, t4,t44, t5, t6,t7);
		return root2;
	}
    
    public GridPane MapUpdated() {
    	GridPane gridPane = new GridPane();
    	Button B = new Button();
    	for(int i = 0; i < 15; i++) {
        	for(int j = 0; j < 15; j++) {
        		B = new Button("    ");
        		if(Game.map[i][j].isVisible()) {
        			if(Game.map[i][j] instanceof CharacterCell) {
        				int x=i,y=j;
        				B.setOnMouseClicked(e->{
        					if(e.getClickCount()==2) {
        						if(((CharacterCell)Game.map[x][y]).getCharacter() instanceof Hero)
        							currentHero=(Hero) ((CharacterCell)Game.map[x][y]).getCharacter();
        					}else {
        						currentHero.setTarget(((CharacterCell)Game.map[x][y]).getCharacter());
        					}
        					borderPane.setCenter(heroesInformation(currentHero));
        				});
        				if(((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie) {
        					B.setText("Z");
        				}else if (((CharacterCell)Game.map[i][j]).getCharacter() instanceof Hero){
        					B.setText(((CharacterCell)Game.map[i][j]).getCharacter().getName());
        				}
        			}else if(Game.map[i][j] instanceof CollectibleCell) {
        				if(((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine) {
        					B.setText("V");
        				}else if(((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Supply) {
        					B.setText("S");
        				}
        			}
        		}
        		gridPane.add(B, j, Game.map.length-1-i);
        	}
    	}
    	return gridPane;
    }

    public GameGUI(Stage primaryStage) {
    	this.primaryStage = primaryStage;
    	VBox root = new VBox();
    	Button up = new Button("UP");
    	VBox vbox = heroesInformation(currentHero);
		borderPane.setCenter(vbox);
    	up.setOnMouseClicked(event1->{
    		try {
    			currentHero.move(Direction.UP);
    		} catch (MovementException e) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("MovementException");
    			alert.setHeaderText(null);
    			alert.setContentText("You can not move to an occuppied cell or out of the map.");
    			alert.show();
    		} catch (NotEnoughActionsException e) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("NotEnoughActionsException");
    			alert.setHeaderText(null);
    			alert.setContentText("Ooooooops, No Enough Actions for This Hero. Try another one");
    			alert.show();
    		}
    		borderPane.setBottom(MapUpdated());
    		borderPane.setCenter(heroesInformation(currentHero));
    		if(Game.checkWin()) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("The Last of Us - Legacy");
    			alert.setHeaderText(null);
    			alert.setContentText("GONGRATULATIONS, YOU WON THE GAME.");
    			alert.show();
    			primaryStage.setScene(null);
    		}else if(Game.checkGameOver()) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("The Last of Us - Legacy");
    			alert.setHeaderText(null);
    			alert.setContentText("GAME OVER, YOU LOST.");
    			alert.show();
    			primaryStage.setScene(null);
    		}
    	});
    	
    	Button down = new Button("DOWN");
    	down.setOnMouseClicked(event1->{
    		try {
    			currentHero.move(Direction.DOWN);
    		} catch (MovementException e) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("MovementException");
    			alert.setHeaderText(null);
    			alert.setContentText("You can not move to an occuppied cell or out of the map.");
    			alert.show();
    		} catch (NotEnoughActionsException e) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("NotEnoughActionsException");
    			alert.setHeaderText(null);
    			alert.setContentText("Ooooooops, No Enough Actions for This Hero. Try another one");
    			alert.show();
    		}
    		borderPane.setBottom(MapUpdated());
    		borderPane.setCenter(heroesInformation(currentHero));
    		if(Game.checkWin()) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("The Last of Us - Legacy");
    			alert.setHeaderText(null);
    			alert.setContentText("GONGRATULATIONS, YOU WON THE GAME.");
    			alert.show();
    			primaryStage.setScene(null);
    		}else if(Game.checkGameOver()) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("The Last of Us - Legacy");
    			alert.setHeaderText(null);
    			alert.setContentText("GAME OVER, YOU LOST.");
    			alert.show();
    			primaryStage.setScene(null);
    		}
    	});
    	
    	Button left = new Button("LEFT");
    	Button right = new Button("RIGHT");
        Button B2 = new Button("Attack");
        Button B3 = new Button("Use Special");
        Button B4 = new Button("Cure");
        Button B5 = new Button("End Turn");
        
        left.setOnMouseClicked(event1->{
        	try {
        		currentHero.move(Direction.LEFT);
        	} catch (MovementException e) {
        		Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("MovementException");
    			alert.setHeaderText(null);
    			alert.setContentText("You can not move to an occuppied cell or out of the map.");
    			alert.show();
        	} catch (NotEnoughActionsException e) {
        		Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("NotEnoughActionsException");
    			alert.setHeaderText(null);
    			alert.setContentText("Ooooooops, No Enough Actions for This Hero. Try another one");
    			alert.show();
        	}
        	borderPane.setBottom(MapUpdated());
        	borderPane.setCenter(heroesInformation(currentHero));
        	if(Game.checkWin()) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("The Last of Us - Legacy");
    			alert.setHeaderText(null);
    			alert.setContentText("GONGRATULATIONS, YOU WON THE GAME.");
    			alert.show();
    			primaryStage.setScene(null);
    		}else if(Game.checkGameOver()) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("The Last of Us - Legacy");
    			alert.setHeaderText(null);
    			alert.setContentText("GAME OVER, YOU LOST.");
    			alert.show();
    			primaryStage.setScene(null);
    		}
        });
        
        right.setOnMouseClicked(event1->{
        	try {
        		currentHero.move(Direction.RIGHT);
        	} catch (MovementException e) {
        		Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("MovementException");
    			alert.setHeaderText(null);
    			alert.setContentText("You can not move to an occuppied cell or out of the map.");
    			alert.show();
        	} catch (NotEnoughActionsException e) {
        		Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("NotEnoughActionsException");
    			alert.setHeaderText(null);
    			alert.setContentText("Ooooooops, No Enough Actions for This Hero. Try another one");
    			alert.show();
        	}
        	borderPane.setBottom(MapUpdated());
        	borderPane.setCenter(heroesInformation(currentHero));
        	if(Game.checkWin()) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("The Last of Us - Legacy");
    			alert.setHeaderText(null);
    			alert.setContentText("GONGRATULATIONS, YOU WON THE GAME.");
    			alert.show();
    			primaryStage.setScene(null);
    		}else if(Game.checkGameOver()) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("The Last of Us - Legacy");
    			alert.setHeaderText(null);
    			alert.setContentText("GAME OVER, YOU LOST.");
    			alert.show();
    			primaryStage.setScene(null);
    		}
        });
        
        B2.setOnMouseEntered(event1->{
        	try {
				currentHero.attack();
			} catch (NotEnoughActionsException e) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("NotEnoughActionsException");
    			alert.setHeaderText(null);
    			alert.setContentText("Ooooooops, No Enough Actions for This Hero. Try another one");
    			alert.show();
			} catch (InvalidTargetException e) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("InvalidTargetException");
    			alert.setHeaderText(null);
    			alert.setContentText("Are you trying to attack a cell that does not contain a Zombie? Sorry, you are attacking the wrong target.");
    			alert.show();
			}
        	
        	borderPane.setBottom(MapUpdated());
        	borderPane.setCenter(heroesInformation(currentHero));
        	if(Game.checkWin()) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("The Last of Us - Legacy");
    			alert.setHeaderText(null);
    			alert.setContentText("GONGRATULATIONS, YOU WON THE GAME.");
    			alert.show();
    			primaryStage.setScene(null);
    		}else if(Game.checkGameOver()) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("The Last of Us - Legacy");
    			alert.setHeaderText(null);
    			alert.setContentText("GAME OVER, YOU LOST.");
    			alert.show();
    			primaryStage.setScene(null);
    		}
        });
        
        B3.setOnMouseClicked(event1->{
        	try {
        		currentHero.useSpecial();
        	} catch (NoAvailableResourcesException e) {
        		Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("NoAvailableResourcesException");
    			alert.setHeaderText(null);
    			alert.setContentText("Are you trying to use your special action? Check your supplies.");
    			alert.show();
        	} catch (InvalidTargetException e) {
        		Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("InvalidTargetException");
    			alert.setHeaderText(null);
    			alert.setContentText("You selected the wrong target.");
    			alert.show();
        	}
        	borderPane.setBottom(MapUpdated());
        	borderPane.setCenter(heroesInformation(currentHero));
        	if(Game.checkWin()) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("The Last of Us - Legacy");
    			alert.setHeaderText(null);
    			alert.setContentText("GONGRATULATIONS, YOU WON THE GAME.");
    			alert.show();
    			primaryStage.setScene(null);
    		}else if(Game.checkGameOver()) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("The Last of Us - Legacy");
    			alert.setHeaderText(null);
    			alert.setContentText("GAME OVER, YOU LOST.");
    			alert.show();
    			primaryStage.setScene(null);
    		}
        });
        
        B4.setOnMouseClicked(event1->{
        	try {
        		currentHero.cure();
        	} catch (NoAvailableResourcesException e) {
        		Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("NoAvailableResourcesException");
    			alert.setHeaderText(null);
    			alert.setContentText("lalalalalalalalalalalal");
    			alert.show();
        	} catch (InvalidTargetException e) {
        		Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("NoAvailableResourcesException");
    			alert.setHeaderText(null);
    			alert.setContentText("Are you trying to use your cure a zombie? Check your vaccines.");
    			alert.show();
        	} catch (NotEnoughActionsException e) {
        		Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("NotEnoughActionsException");
    			alert.setHeaderText(null);
    			alert.setContentText("Ooooooops, No Enough Actions for This Hero. Try another one");
    			alert.show();
        	}
        	borderPane.setBottom(MapUpdated());
        	borderPane.setCenter(heroesInformation(currentHero));
        	if(Game.checkWin()) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("The Last of Us - Legacy");
    			alert.setHeaderText(null);
    			alert.setContentText("GONGRATULATIONS, YOU WON THE GAME.");
    			alert.show();
    			primaryStage.setScene(null);
    		}else if(Game.checkGameOver()) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("The Last of Us - Legacy");
    			alert.setHeaderText(null);
    			alert.setContentText("GAME OVER, YOU LOST.");
    			alert.show();
    			primaryStage.setScene(null);
    		}
        });
        
        root.getChildren().addAll(up, down, left, right, B2, B3, B4, B5);
        borderPane.setRight(root);
        GridPane gridPane = new GridPane();
        Button B = new Button();
        B.setPrefSize(50, 50);
        for(int i = 0; i < 15; i++) {
        	for(int j = 0; j < 15; j++) {
        		B = new Button("       ");
        		if(Game.map[i][j].isVisible()) {
        			if(Game.map[i][j] instanceof CharacterCell) {
        				int x=i,y=j;
        				B.setOnMouseClicked(e->{
        					if(e.getClickCount()==2) {
        						if(((CharacterCell)Game.map[x][y]).getCharacter() instanceof Hero)
        							currentHero=(Hero) ((CharacterCell)Game.map[x][y]).getCharacter();
        					}else {
        						currentHero.setTarget(((CharacterCell)Game.map[x][y]).getCharacter());
        					}
        					borderPane.setCenter(heroesInformation(currentHero));
        				});
        				if(((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie) {
        					B.setText("Z");
        				}else if (((CharacterCell)Game.map[i][j]).getCharacter() instanceof Hero){
        					B.setText(((CharacterCell)Game.map[i][j]).getCharacter().getName());
        				}
        			}else if(Game.map[i][j] instanceof CollectibleCell) {
        				if(((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine) {
        					B.setText("V");
        				}else if(((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Supply) {
        					B.setText("S");
        				}
        			}
        		}
        		gridPane.add(B, j, Game.map.length-1-i);
        	}
        }
        
        B5.setOnMouseClicked(event->{
        	try {
				Game.endTurn();
			} catch (NotEnoughActionsException e) {
			} catch (InvalidTargetException e) {
			}
        	borderPane.setBottom(MapUpdated());
        	for(int i = 0; i < Game.heroes.size(); i++) {
        		borderPane.setCenter(heroesInformation(Game.heroes.get(i)));
        	}
        	if(Game.checkWin()) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("The Last of Us - Legacy");
    			alert.setHeaderText(null);
    			alert.setContentText("GONGRATULATIONS, YOU WON THE GAME.");
    			alert.show();
    			primaryStage.setScene(null);
    		}else if(Game.checkGameOver()) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("The Last of Us - Legacy");
    			alert.setHeaderText(null);
    			alert.setContentText("GAME OVER, YOU LOST.");
    			alert.show();
    			primaryStage.setScene(null);
    		}
        });
        
        borderPane.setBottom(gridPane);
        B2.setOnMouseClicked(event -> {
        	
       });
        scene = new Scene(borderPane, 700, 700);
		primaryStage.setScene(scene);
        primaryStage.setTitle("The Last Of Us - Legacy");
        primaryStage.show();
    }
 
}