package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import org.junit.Test;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.characters.Character;
import model.characters.Direction;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Collectible;
import model.collectibles.Supply;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class Tests {

	String characterPath = "model.characters.Character";
	String collectiblePath = "model.collectibles.Collectible";
	String vaccinePath = "model.collectibles.Vaccine";
	String supplyPath = "model.collectibles.Supply";
	String fighterPath = "model.characters.Fighter";

	private String charCell = "model.world.CharacterCell";
	private String cellPath = "model.world.Cell";
	private String collectibleCellPath = "model.world.CollectibleCell";
	private String trapCellPath = "model.world.TrapCell";
	private String enumDirectionPath = "model.characters.Direction";

	String gamePath = "engine.Game";
	String medicPath = "model.characters.Medic";
	String explorerPath = "model.characters.Explorer";
	String heroPath = "model.characters.Hero";

	String gameActionExceptionPath = "exceptions.GameActionException";
	String invalidTargetExceptionPath = "exceptions.InvalidTargetException";
	String movementExceptionPath = "exceptions.MovementException";
	String noAvailableResourcesExceptionPath = "exceptions.NoAvailableResourcesException";
	String notEnoughActionsExceptionPath = "exceptions.NotEnoughActionsException";

	String zombiePath = "model.characters.Zombie";
	String entanglerPath = "model.collectibles.Entangler";
	String idolPath = "model.collectibles.Idol";
	String armorPath = "model.collectibles.Armor";
	String itemPath = "model.collectibles.Item";

	@Test(timeout = 100)
	public void testItemClass() {
		Class itemClass = null;
		try {
			itemClass = Class.forName(itemPath);
		} catch (ClassNotFoundException e) {
			fail("There should be an Item class in the package model.collectibles");
		}
		assertTrue("The Item class should be abstract",
				Modifier.isAbstract(itemClass.getModifiers()));
		assertTrue("The Item class should implement the Collectible interface",
				Collectible.class.isAssignableFrom(itemClass));
	}

	@Test(timeout = 100)
	public void testEntanglerClass() {
		Class itemClass = null;
		try {
			itemClass = Class.forName(itemPath);
		} catch (ClassNotFoundException e) {
			fail("There should be an Item class in the package model.collectibles");
		}
		Class entanglerClass = null;
		try {
			entanglerClass = Class.forName(entanglerPath);
		} catch (ClassNotFoundException e) {
			fail("There should be an Entangler class in the package model.collectibles");
		}
		assertTrue("The Entangler class should be a subclass of Item",
				itemClass.isAssignableFrom(entanglerClass));
	}

	@Test(timeout = 100)
	public void testIdolClass() {
		Class itemClass = null;
		try {
			itemClass = Class.forName(itemPath);
		} catch (ClassNotFoundException e) {
			fail("There should be an Item class in the package model.collectibles");
		}
		Class idolClass = null;
		try {
			idolClass = Class.forName(idolPath);
		} catch (ClassNotFoundException e) {
			fail("There should be an Idol class in the package model.collectibles");
		}
		assertTrue("The Idol class should be a subclass of Item",
				itemClass.isAssignableFrom(idolClass));
	}

	@Test(timeout = 100)
	public void testArmorClass() {
		Class itemClass = null;
		try {
			itemClass = Class.forName(itemPath);
		} catch (ClassNotFoundException e) {
			fail("There should be an Item class in the package model.collectibles");
		}
		Class armorClass = null;
		try {
			armorClass = Class.forName(armorPath);
		} catch (ClassNotFoundException e) {
			fail("There should be an Armor class in the package model.collectibles");
		}
		assertTrue("The Armor class should be a subclass of Item",
				itemClass.isAssignableFrom(armorClass));
	}

	@Test(timeout = 100)
	public void testArmorConstructorInitialization() {
		Class armorClass = null;
		try {
			armorClass = Class.forName(armorPath);
		} catch (ClassNotFoundException e) {
			fail("There should be an Armor class in the package model.collectibles");
		}
		Constructor[] constructors = armorClass.getDeclaredConstructors();
		Constructor constructor = null;
		for (Constructor c : constructors) {
			if (c.getParameterCount() == 0) {
				constructor = c;
				break;
			}
		}
		if (constructor == null) {
			fail("The Armor class should have a default constructor");
		}
		constructor.setAccessible(true);
		Object armor = null;
		try {
			armor = constructor.newInstance();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			fail("The Armor class should have a default constructor");
		}
		Field protectionField = null;
		try {
			protectionField = armorClass.getDeclaredField("protection");
		} catch (NoSuchFieldException | SecurityException e) {
			fail("The Armor class should have a field named protection");
		}
		protectionField.setAccessible(true);
		int protection = 0;
		try {
			protection = (int) protectionField.get(armor);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			fail("The protection field should be accessible");
		}
		assertEquals("The protection field should be initialized to 30", 30,
				protection);
	}

	@Test(timeout = 100)
	public void testArmorAttributeProtection() {
		Class armorClass = null;
		try {
			armorClass = Class.forName(armorPath);
		} catch (ClassNotFoundException e) {
			fail("There should be an Armor class in the package model.collectibles");
		}
		Field protectionField = null;
		try {
			protectionField = armorClass.getDeclaredField("protection");
		} catch (NoSuchFieldException | SecurityException e) {
			fail("The Armor class should have a field named protection");
		}

		assertTrue("The Armor protection should be of type int",
				protectionField.getGenericType() == int.class);
		assertTrue("The protection attribute should be private",
				Modifier.isPrivate(protectionField.getModifiers()));
		protectionField.setAccessible(true);
		Method protectionSetter = null;
		try {
			protectionSetter = armorClass.getDeclaredMethod("setProtection",
					int.class);
		} catch (NoSuchMethodException | SecurityException e) {
			fail("The protection attribute is a WRITE attribute");
		}
		Method protectionGetter = null;
		try {
			protectionGetter = armorClass.getDeclaredMethod("getProtection");
		} catch (NoSuchMethodException | SecurityException e) {
			fail("The protection attribute is a READ attribute");
		}
	}

	@Test(timeout = 100)
	public void testHeroAttributeItem() {
		Class itemClass = null;
		try {
			itemClass = Class.forName(itemPath);
		} catch (ClassNotFoundException e) {
			fail("There should be an Item class in the package model.collectibles");
		}
		Field itemField = null;
		try {
			itemField = Hero.class.getDeclaredField("item");
		} catch (NoSuchFieldException | SecurityException e) {
			fail("The Hero class should have a field named item");
		}
		assertTrue("The Hero item field should be of type Item",
				itemField.getGenericType() == itemClass);

		assertTrue("The item attribute should be private",
				Modifier.isPrivate(itemField.getModifiers()));
		itemField.setAccessible(true);
		Method itemSetter = null;
		try {
			itemSetter = Hero.class.getDeclaredMethod("setItem", itemClass);
		} catch (NoSuchMethodException | SecurityException e) {
			fail("The item attribute is a WRITE attribute");
		}
		Method itemGetter = null;
		try {
			itemGetter = Hero.class.getDeclaredMethod("getItem");
		} catch (NoSuchMethodException | SecurityException e) {
			fail("The item attribute is a READ attribute");
		}
	}

	private void initializeGame() {
		Hero hero = new Medic("John", 100, 20, 3);
		Game.availableHeroes.add(hero);
		Game.startGame(hero);
	}

	private void clearGame() {
		Game.availableHeroes.clear();
		Game.heroes.clear();
		Game.zombies.clear();
		for (int i = 0; i < Game.map.length; i++) {
			for (int j = 0; j < Game.map[i].length; j++) {
				Game.map[i][j] = new CharacterCell(null);
			}
		}
	}

	@Test(timeout = 100)
	public void testNewGameHasItems() {
		initializeGame();
		int numIdols = 1;
		int numEntangler = 2;
		int numArmor = 5;

		int idols = 0;
		int entanglers = 0;
		int armors = 0;

		Class armorClass = null;
		try {
			armorClass = Class.forName(armorPath);
		} catch (ClassNotFoundException e) {
			fail("There should be an Armor class in the package model.collectibles");
		}
		Class idolClass = null;
		try {
			idolClass = Class.forName(idolPath);
		} catch (ClassNotFoundException e) {
			fail("There should be an Idol class in the package model.collectibles");
		}
		Class entanglerClass = null;
		try {
			entanglerClass = Class.forName(entanglerPath);
		} catch (ClassNotFoundException e) {
			fail("There should be an Entangler class in the package model.collectibles");
		}

		for (int i = 0; i < Game.map.length; i++) {
			for (int j = 0; j < Game.map[i].length; j++) {
				if (Game.map[i][j] instanceof CollectibleCell) {
					CollectibleCell cell = (CollectibleCell) Game.map[i][j];
					if (idolClass.isAssignableFrom(cell.getCollectible()
							.getClass())) {
						idols++;
					} else if (entanglerClass.isAssignableFrom(cell
							.getCollectible().getClass())) {
						entanglers++;
					} else if (armorClass.isAssignableFrom(cell
							.getCollectible().getClass())) {
						armors++;
					}
				}
			}
		}
		assertEquals("There should be " + numIdols
				+ " idol in the map at the start of the Game", numIdols, idols);
		assertEquals("There should be " + numEntangler
				+ " entanglers in the map at the start of the Game",
				numEntangler, entanglers);
		assertEquals("There should be " + numArmor
				+ " armors in the map at the start of the Game", numArmor,
				armors);
	}

	@Test(timeout = 100)
	public void testHeroPicksUpArmor() throws MovementException,
			NotEnoughActionsException {
		initializeGame();
		clearGame();
		Class armorClass = null;
		try {
			armorClass = Class.forName(armorPath);
		} catch (ClassNotFoundException e) {
			fail("There should be an Armor class in the package model.collectibles");
		}
		Constructor armorConstructor = null;
		try {
			armorConstructor = armorClass.getDeclaredConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			fail("The Armor class should have an empty constructor");
		}
		Object armor = null;
		try {
			armor = armorConstructor.newInstance();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			fail("The Armor class should have an empty constructor");
		}
		CollectibleCell cell = new CollectibleCell((Collectible) armor);
		Hero hero = new Medic("John", 100, 20, 3);
		Game.map[0][0] = new CharacterCell(hero);
		hero.setLocation(new Point(0, 0));
		Game.map[0][1] = cell;
		hero.move(Direction.RIGHT);
		Field itemField = null;
		try {
			itemField = Hero.class.getDeclaredField("item");
		} catch (NoSuchFieldException | SecurityException e) {
			fail("The Hero class should have a field named item");
		}
		itemField.setAccessible(true);
		Object item = null;
		try {
			item = itemField.get(hero);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			fail("The item attribute should be private");
		}
		assertEquals("The hero should have picked up the armor", armor, item);
	}

	@Test(timeout = 100)
	public void testHeroPicksUpIdol() throws MovementException,
			NotEnoughActionsException {
		initializeGame();
		clearGame();
		Class idolClass = null;
		try {
			idolClass = Class.forName(idolPath);
		} catch (ClassNotFoundException e) {
			fail("There should be an Idol class in the package model.collectibles");
		}
		Constructor idolConstructor = null;
		try {
			idolConstructor = idolClass.getDeclaredConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			fail("The Idol class should have an empty constructor");
		}
		Object idol = null;
		try {
			idol = idolConstructor.newInstance();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			fail("The Idol class should have an empty constructor");
		}
		CollectibleCell cell = new CollectibleCell((Collectible) idol);
		Hero hero = new Medic("John", 100, 20, 3);
		Game.map[0][0] = new CharacterCell(hero);
		hero.setLocation(new Point(0, 0));
		Game.map[0][1] = cell;
		hero.move(Direction.RIGHT);
		Field itemField = null;
		try {
			itemField = Hero.class.getDeclaredField("item");
		} catch (NoSuchFieldException | SecurityException e) {
			fail("The Hero class should have a field named item");
		}
		itemField.setAccessible(true);
		Object item = null;
		try {
			item = itemField.get(hero);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			fail("The item attribute should be private");
		}
		assertEquals("The hero should have picked up the idol", idol, item);
	}

	@Test(timeout = 100)
	public void testHeroPicksUpEntangler() throws MovementException,
			NotEnoughActionsException {
		initializeGame();
		clearGame();
		Class entanglerClass = null;
		try {
			entanglerClass = Class.forName(entanglerPath);
		} catch (ClassNotFoundException e) {
			fail("There should be an Entangler class in the package model.collectibles");
		}
		Constructor entanglerConstructor = null;
		try {
			entanglerConstructor = entanglerClass.getDeclaredConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			fail("The Entangler class should have an empty constructor");
		}
		Object entangler = null;
		try {
			entangler = entanglerConstructor.newInstance();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			fail("The Entangler class should have an empty constructor");
		}
		CollectibleCell cell = new CollectibleCell((Collectible) entangler);
		Hero hero = new Medic("John", 100, 20, 3);
		Game.map[0][0] = new CharacterCell(hero);
		hero.setLocation(new Point(0, 0));
		Game.map[0][1] = cell;
		hero.move(Direction.RIGHT);
		Field itemField = null;
		try {
			itemField = Hero.class.getDeclaredField("item");
		} catch (NoSuchFieldException | SecurityException e) {
			fail("The Hero class should have a field named item");
		}
		itemField.setAccessible(true);
		Object item = null;
		try {
			item = itemField.get(hero);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			fail("The item attribute should be private");
		}
		assertEquals("The hero should have picked up the entangler", entangler,
				item);
	}

	@Test(timeout = 100)
	public void testIdolBehaviour() throws IllegalArgumentException,
			IllegalAccessException, NotEnoughActionsException,
			InvalidTargetException {
		initializeGame();
		clearGame();
		Class idolClass = null;
		try {
			idolClass = Class.forName(idolPath);
		} catch (ClassNotFoundException e) {
			fail("There should be an Idol class in the package model.collectibles");
		}
		Constructor idolConstructor = null;
		try {
			idolConstructor = idolClass.getDeclaredConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			fail("The Idol class should have an empty constructor");
		}
		Object idol = null;
		try {
			idol = idolConstructor.newInstance();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			fail("The Idol class should have an empty constructor");
		}
		Hero hero = new Medic("John", 100, 20, 3);
		Game.map[0][0] = new CharacterCell(hero);
		hero.setLocation(new Point(0, 0));
		Game.heroes.add(hero);
		Zombie zombie = new Zombie();
		Game.map[0][1] = new CharacterCell(zombie);
		zombie.setLocation(new Point(0, 1));
		Game.zombies.add(zombie);
		Field itemField = null;
		try {
			itemField = Hero.class.getDeclaredField("item");
		} catch (NoSuchFieldException | SecurityException e) {
			fail("The Hero class should have a field named item");
		}
		itemField.setAccessible(true);
		itemField.set(hero, idol);
		hero.setCurrentHp(10);
		Game.endTurn();
		assertEquals("The hero should have been revived with maximum HP", hero.getMaxHp(),
				hero.getCurrentHp());
		assertEquals("The hero should lose the idol after being revived", null,
				itemField.get(hero));
		assertTrue(
				"The hero should not be removed from the heroes ArrayList if he has an Idol before dying.",
				Game.heroes.contains(hero));
		Game.zombies.remove(Game.zombies.size() - 1);
		hero.setCurrentHp(10);
		Game.endTurn();
		assertEquals(
				"The hero shouldn't be revived, and should be removed from the map, if they have no Idol.",
				null, ((CharacterCell) Game.map[0][0]).getCharacter());
	}

	@Test(timeout = 100)
	public void testEntanglerBehaviour() throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			NotEnoughActionsException, InvalidTargetException {
		initializeGame();
		clearGame();
		Class entanglerClass = null;
		try {
			entanglerClass = Class.forName(entanglerPath);
		} catch (ClassNotFoundException e) {
			fail("There should be an Entangler class in the package model.collectibles");
		}
		Constructor entanglerConstructor = null;
		try {
			entanglerConstructor = entanglerClass.getDeclaredConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			fail("The Entangler class should have an empty constructor");
		}
		Object entangler = null;
		try {
			entangler = entanglerConstructor.newInstance();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			fail("The Entangler class should have an empty constructor");
		}
		Hero hero1 = new Medic("John", 100, 20, 3);
		Game.map[0][0] = new CharacterCell(hero1);
		hero1.setLocation(new Point(0, 0));
		Game.heroes.add(hero1);
		// Generate a random point within the map that isn't (0, 0)
		Point randomPoint = new Point(
				(int) (Math.random() * (Game.map.length - 1)),
				(int) (Math.random() * (Game.map[0].length - 1)));
		while (randomPoint.equals(new Point(0, 0))) {
			randomPoint = new Point(
					(int) (Math.random() * (Game.map.length - 1)),
					(int) (Math.random() * (Game.map[0].length - 1)));
		}
		Hero hero2 = new Medic("John", 100, 20, 3);
		Game.map[randomPoint.x][randomPoint.y] = new CharacterCell(hero2);
		hero2.setLocation(randomPoint);
		Game.heroes.add(hero2);
		Field itemField = null;
		try {
			itemField = Hero.class.getDeclaredField("item");
		} catch (NoSuchFieldException | SecurityException e) {
			fail("The Hero class should have a field named item");
		}
		itemField.setAccessible(true);
		itemField.set(hero1, entangler);
		hero1.setTarget(hero2);
		Method swap = null;
		try {
			swap = Hero.class.getDeclaredMethod("swapLocations");
		} catch (NoSuchMethodException | SecurityException e) {
			fail("The Hero class should have a method named swapLocations");
		}
		swap.setAccessible(true);
		try {
			swap.invoke(hero1);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			fail("An " + e.getClass().getSimpleName()
					+ " occured, while trying to call swapLocations");
		}
		assertEquals(
				"The target location attribute should be updated after swapping.",
				new Point(0, 0), hero2.getLocation());
		assertEquals(
				"The hero location attribute should be updated after swapping.",
				randomPoint, hero1.getLocation());
		assertEquals(
				"The map should be updated after the heroes swap positions. Hero not in the correct position",
				hero1, ((CharacterCell) Game.map[randomPoint.x][randomPoint.y])
						.getCharacter());
		assertEquals(
				"The map should be updated after the heroes swap positions. Target not in the correct position.",
				hero2, ((CharacterCell) Game.map[0][0]).getCharacter());
		assertEquals(
				"The hero should lose the entangler after swapping locations",
				null, itemField.get(hero1));
		hero1.setTarget(hero2);
		try {
			swap.invoke(hero1);
			fail("The hero should not be able to swap locations if they don't have an entangler");
		} catch (Exception e) {
			assertEquals(
					"Wrong exception type, when swapping with no entangler",
					NoAvailableResourcesException.class, e.getCause()
							.getClass());
		}
		Game.endTurn();
		itemField.set(hero1, entangler);
		hero1.setTarget(Game.zombies.get(0));
		try {
			swap.invoke(hero1);
			fail("The hero should not be able to swap locations if the target is not a hero");
		} catch (Exception e) {
			assertEquals("Wrong exception type, when swapping with a zombie",
					InvalidTargetException.class, e.getCause().getClass());
		}
	}

	@Test(timeout = 100)
	public void testArmorBehaviour() throws IllegalArgumentException,
			IllegalAccessException, NotEnoughActionsException,
			InvalidTargetException {
		initializeGame();
		clearGame();
		Class armorClass = null;
		try {
			armorClass = Class.forName(armorPath);
		} catch (ClassNotFoundException e) {
			fail("There should be an Armor class in the package model.collectibles");
		}
		Constructor armorConstructor = null;
		try {
			armorConstructor = armorClass.getDeclaredConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			fail("The Armor class should have an empty constructor");
		}
		Object armor = null;
		try {
			armor = armorConstructor.newInstance();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			fail("The Armor class should have an empty constructor");
		}
		Hero hero = new Medic("John", 100, 20, 3);
		Game.map[0][0] = new CharacterCell(hero);
		hero.setLocation(new Point(0, 0));
		Game.heroes.add(hero);
		Zombie zombie = new Zombie();
		Game.map[0][1] = new CharacterCell(zombie);
		zombie.setLocation(new Point(0, 1));
		Game.zombies.add(zombie);
		Field itemField = null;
		try {
			itemField = Hero.class.getDeclaredField("item");
		} catch (NoSuchFieldException | SecurityException e) {
			fail("The Hero class should have a field named item");
		}
		itemField.setAccessible(true);
		itemField.set(hero, armor);
		Game.endTurn();
		Field protectionField = null;
		try {
			protectionField = armorClass.getDeclaredField("protection");
		} catch (NoSuchFieldException | SecurityException e) {
			fail("The Armor class should have a field named protection");
		}
		protectionField.setAccessible(true);
		int protection = (int) protectionField.get(armor);
		assertEquals(
				"The hero should have lost armor protection when attacked while having an armor item.",
				20, protection);
		Game.zombies.remove(Game.zombies.size() - 1);
		protectionField.set(armor, 5);
		Game.endTurn();
		protection = (int) protectionField.get(armor);
		assertEquals(
				"The armor protection should be set to 0 when attacked with a damage greater than the remaining protection of the armor.",
				0, protectionField.get(armor));
		assertEquals(
				"The hero should have lost health points when attacked with a damage greater than the remaining protection of the armor.",
				95, hero.getCurrentHp());
		assertEquals("The hero should lose the armor after it is destroyed",
				null, itemField.get(hero));
		Game.zombies.remove(Game.zombies.size() - 1);
		Game.endTurn();
		assertEquals(
				"The hero should have lost health points when attacked without an armor",
				85, hero.getCurrentHp());
	}

	@Test(timeout = 100)
	public void testArmorBehaviourWhenHealed() throws IllegalArgumentException,
			IllegalAccessException, NoAvailableResourcesException,
			InvalidTargetException {
		initializeGame();
		clearGame();
		Class armorClass = null;
		try {
			armorClass = Class.forName(armorPath);
		} catch (ClassNotFoundException e) {
			fail("There should be an Armor class in the package model.collectibles");
		}
		Constructor armorConstructor = null;
		try {
			armorConstructor = armorClass.getDeclaredConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			fail("The Armor class should have an empty constructor");
		}
		Object armor = null;
		try {
			armor = armorConstructor.newInstance();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			fail("The Armor class should have an empty constructor");
		}
		Hero hero1 = new Medic("John", 100, 20, 3);
		Game.map[0][0] = new CharacterCell(hero1);
		hero1.setLocation(new Point(0, 0));
		Game.heroes.add(hero1);
		Hero hero2 = new Medic("Joe", 100, 20, 3);
		Game.map[0][1] = new CharacterCell(hero2);
		hero2.setLocation(new Point(0, 1));
		Game.heroes.add(hero2);
		hero1.setCurrentHp(90);
		Field protectionField = null;
		try {
			protectionField = armorClass.getDeclaredField("protection");
		} catch (NoSuchFieldException | SecurityException e) {
			fail("The Armor class should have a field named protection");
		}
		protectionField.setAccessible(true);
		protectionField.set(armor, 10);
		Field itemField = null;
		try {
			itemField = Hero.class.getDeclaredField("item");
		} catch (NoSuchFieldException | SecurityException e) {
			fail("The Hero class should have a field named item");
		}
		itemField.setAccessible(true);
		itemField.set(hero1, armor);
		hero2.getSupplyInventory().add(new Supply());
		hero2.setTarget(hero1);
		hero2.useSpecial();
		assertEquals("The hero should have gained health points when healed",
				100, hero1.getCurrentHp());
		assertEquals(
				"The hero shouldn't have lost nor gained armor points when healed",
				10, protectionField.get(armor));
	}
}