import java.util.ArrayList;
public abstract class ConcretePiece implements Piece {

    public Position position;
    public Player pieceOwner;
    public String pieceString;
    protected int number;
    private final ArrayList<Position> Moves = new ArrayList<>();

    @Override
    public Player getOwner() {
        return pieceOwner;
    }
    public Position getPos() {
        return position;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {this.number = number;}
    public void removeLastMove(){
        Moves.remove(Moves.size()-1);
    }

    @Override
    public String getType() {
        return null;
    }
    public String getString(){return pieceString;}

    public Position getPosition(){return position;}
    public void setPosition(Position newPos){
        position = newPos;
        Moves.add(newPos);
    }
    public ArrayList<Position> getMoves(){
        return Moves;
    }

}
