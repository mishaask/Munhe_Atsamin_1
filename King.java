public class King extends ConcretePiece{

    public King (Player Player2, Position position){

        this.pieceOwner = Player2;
        this.position = position;
        this.setPosition(position);
        this.pieceString = "K";
    }

    @Override
    public String getType(){
        return "♔";
    }
    @Override
    public String getString(){
        return "K";
    }

    @Override
    public Player getOwner() {
        return pieceOwner;
    }

}
