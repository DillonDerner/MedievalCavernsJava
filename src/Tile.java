import enums.TileType;

/**
 * Created by macbook on 4/8/18.
 */
public class Tile {

    private TileType tileType;
    private boolean hasBeenVisited = false;

    public Tile(TileType tileType) {
        this.tileType = tileType;
    }

    public TileType getTileType() {
        return tileType;
    }


    public boolean hasBeenVisited() {
        return hasBeenVisited;
    }

    public void setVisited(boolean hasBeenVisited) {
        this.hasBeenVisited = hasBeenVisited;
    }

    public void about() {
        System.out.println(tileType.name());
    }
}
