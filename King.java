public class King extends ConcretePiece{

    public King (Player Player2){
        this.pieceOwner = Player2;
    }

    @Override
    public String getType(){
        return "♔";
    }

    @Override
    public Player getOwner() {
        return pieceOwner;
    }

}
