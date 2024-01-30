public class Pawn extends ConcretePiece{


    public Pawn(Player pieceOwner){
        this.pieceOwner = pieceOwner;

    }

    @Override
    public Player getOwner() {
        return pieceOwner;
    }

    @Override
    public String getType() {
        if (this.getOwner().isPlayerOne())
            return "♙";
        else return "♟";
    }
}
