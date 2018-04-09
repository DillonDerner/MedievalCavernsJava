import enums.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by macbook on 4/8/18.
 */
public class Floor {
    private String name;
    private int number; // also the difficulty
    private List<Tile> tiles;
    private int size = 50;

    public Floor(String name, int number) {
        this.name = name;
        this.number = number;
        makeTiles(number);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public Tile getTile(int tileLocation) {
        return tiles.get(tileLocation-1);
    }

//    public void setTiles(List<Tile> tiles) {
//        this.tiles = tiles;
//    }

    private void makeTiles(int difficulty) {
        List<Tile> tileList = new ArrayList<>();
        Tile start = new Tile(TileType.START);
        tileList.add(start);

        Random random = new Random();
        while ((tileList.size()) < size-1) {
            int randomNumber = Math.abs(random.nextInt() % 7 + difficulty); // 0-6

            if(randomNumber == 0)
                tileList.add(new Tile(TileType.EMPTY));
            else if(randomNumber == 1)
                tileList.add(new Tile(TileType.FORREST));
            else if(randomNumber == 2)
                tileList.add(new Tile(TileType.LOOT));
            else if(randomNumber == 3)
                tileList.add(new Tile(TileType.POND));
            else
                tileList.add(new Tile(TileType.MONSTER));
        }

        Tile end = new Tile(TileType.END);
        tileList.add(end);

        tiles = tileList;
    }

    public void about() {
        System.out.println("Welcome to " + name);
        System.out.println("Located on floor " + number);
        for (Tile tile : tiles) {
            tile.about();
        }
    }


}
