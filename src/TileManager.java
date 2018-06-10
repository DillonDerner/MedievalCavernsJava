import common.enums.TileType;
import helpers.PrintHelpers;
import mobs.Monster;

/**
 * Created by macbook on 4/8/18.
 */
public class TileManager {
    private PrintHelpers printHelpers;
    private BattleManager battleManager;
    private MonsterBag monsterBag;

    public TileManager() {
        printHelpers = new PrintHelpers();
        battleManager = new BattleManager();
        monsterBag = new MonsterBag();
    }

    public void runPlayerOnTile(Player player, Tile tile) {
        if(tile.getTileType() == TileType.START)
            runStart(player);
        if(tile.getTileType() == TileType.TOWN)
            runStart(player);
        if(tile.getTileType() == TileType.MONSTER)
            runMonster(player);
        if(tile.getTileType() == TileType.EMPTY)
            runEmpty(player);
        if(tile.getTileType() == TileType.LOOT)
            runLoot(player);
        if(tile.getTileType() == TileType.FORREST)
            runForrest(player);
        if(tile.getTileType() == TileType.POND)
            runPond(player);
        if(tile.getTileType() == TileType.END)
            runEnd(player);
    }

    private void runStart(Player player) { // also town
        printHelpers.println("The start of the map");
    }

    private void runMonster(Player player) {
        printHelpers.println("Eeep! A Monster!");
        // if player wants to fight
        Monster monster = monsterBag.grab(player.getFloor());
        player.printStats();
        monster.printStats();
        battleManager.fight(player, monster);
    }

    private void runEmpty(Player player) {
        printHelpers.println("There is nothing here...");
    }

    private void runLoot(Player player) {
        printHelpers.println("LOOOT!!!");
    }

    private void runForrest(Player player) {
        printHelpers.println("A forrest? maybe i could forage some resources.");
    }

    private void runPond(Player player) {
        printHelpers.println("A Pond! If i had a fishing pole, i could try to fish");
    }

    private void runEnd(Player player) {
        printHelpers.println("Floor Boss");
    }

}
