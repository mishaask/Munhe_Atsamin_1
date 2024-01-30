public class Position {

    private final int x;

    private final int y;

    private ConcretePiece Piece;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
        this.Piece = null;
    }
    public Position(int x, int y,ConcretePiece Piece){
        this.x = x;
        this.y = y;
        this.Piece = Piece;
    }

    public int getX(){
        return x;
    }


    public int getY(){
        return y;
    }

    public ConcretePiece getPiece(){return Piece;}

    public void setPiece(ConcretePiece Piece) {this.Piece = Piece;}

    public void removePiece() {this.Piece = null;}

}
