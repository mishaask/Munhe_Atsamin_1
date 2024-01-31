import java.util.ArrayList;
import java.util.HashSet;

public class Position {

    private final int x;

    private final int y;


    ArrayList<ConcretePiece> piecesSet = new ArrayList<>();

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Position(int x, int y,ConcretePiece Piece){
        this.x = x;
        this.y = y;
    }
    
    public HashSet<ConcretePiece> getPiecesSet(){HashSet<ConcretePiece> hashSet = new HashSet<>(piecesSet);
        return hashSet;}
    public void addPiece(ConcretePiece piece){
        piecesSet.add(piece);
    }
    public void removePiece(){
        if(piecesSet.size()>1)
        piecesSet.removeLast();
    }
    public int getX(){
        return x;
    }


    public int getY(){
        return y;
    }
    @Override
    public String toString(){
        return "("+x+", "+y+")";
    }
    public String getPiecesToString(){
        return piecesSet.size()+" pieces";
    }
}
