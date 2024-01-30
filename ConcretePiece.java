public abstract class ConcretePiece implements Piece {

    //private Position position;
    public Player pieceOwner;

    @Override
    public Player getOwner() {
        return pieceOwner;
    }

    @Override
    public String getType() {
        return null;
    }

    /*public Position getPosition(){return position;}
    public void setPosition(Position newPos){position = newPos;}*/

}
