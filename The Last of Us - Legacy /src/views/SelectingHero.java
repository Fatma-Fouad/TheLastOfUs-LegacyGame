package views;

import java.awt.Label;
import java.io.IOException;
import javafx.scene.text.Text;
import engine.Game;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;

public class SelectingHero extends Application{
	
	Hero startHero;
	
	public static VBox heroesInformation(Hero hero){
		VBox root2 = new VBox();
		Text l = new Text("Hero Name: " + hero.getName());
		Text l1 = new Text("");
		Text l2 = new Text("Hero Max Helth Points: " + hero.getMaxHp());
		Text l3 = new Text("Hero Attack Damage: " + hero.getAttackDmg());
		Text l4 = new Text("Hero Max Action Points: " + hero.getMaxActions());
		if(hero instanceof Fighter) {
			l1 = new Text("Hero Type: Fighter");
		}else if(hero instanceof Medic) {
			l1 = new Text("Hero Type: Medic");
		}else if(hero instanceof Explorer) {
			l1 = new Text("Hero Type: Explorer");
		}
		GridPane grid = new GridPane();
		grid.add(l1, 0, 0);
		root2.getChildren().addAll(l, l1, l2, l3, l4);
		return root2;
	}
	
	public void start(Stage primaryStage) throws Exception{
		
		BorderPane borderPane = new BorderPane();
		GridPane root1 = new GridPane();
		for (int i = 0; i < Game.availableHeroes.size(); i++) {
			Hero hero = Game.availableHeroes.get(i);
			Button hn = new Button(Game.availableHeroes.get(i).getName());
			root1.add(hn, i+1, 0);
			hn.setOnMouseEntered(event->{
				VBox root2 = heroesInformation(hero);
				borderPane.setLeft(root2);
			});
			hn.setOnMouseClicked(event-> {
				startHero = hero;
			});
		}
		
		Button sg = new Button("Start Game");
		root1.getChildren().addAll(sg);
		borderPane.setBottom(root1);	
		sg.setOnMouseClicked(event -> {
			Game.startGame(startHero);
			primaryStage.setScene(new GameGUI(primaryStage).getScene());
      });
		Scene scene = new Scene(borderPane, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("The Last Of Us - Legacy");
        primaryStage.show();
    }
	
	public static void main(String[] args) throws IOException {
		Game.loadHeroes("Heroes.csv");
        launch(args);
    }
	
}
