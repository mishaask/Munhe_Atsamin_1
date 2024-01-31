public class Pawn extends ConcretePiece{

    public Pawn(Player pieceOwner,Position position,int number){

        this.pieceOwner = pieceOwner;
        this.position = position;

    }
    public Pawn(Player pieceOwner,Position position){

        this.pieceOwner = pieceOwner;
        this.position = position;
        this.setPosition(position);
        if (this.getOwner().isPlayerOne())
            {this.pieceString= "D";}
        else {this.pieceString= "A";}
    }

    @Override
    public Player getOwner() {
        return pieceOwner;
    }
    public Position getPos() {
        return position;
    }
    @Override
    public String getString() {
    return pieceString;
    }

    @Override
    public String getType() {
        if (this.getOwner().isPlayerOne())
            return "♙";
        else return "♟";
    }
}
