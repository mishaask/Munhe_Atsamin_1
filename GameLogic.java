import java.util.ArrayList;
import java.util.Stack;

public class GameLogic  implements PlayableLogic{

    private final int BOARDSIZE = 11;
    private ConcretePiece[][] PieceMap;
    private ArrayList<ConcretePiece> Pieces = new ArrayList<>();
    private Stack<Move> moveHistory = new Stack<>();//move format: (x,y)>(x,y)
    private final ConcretePlayer player1Def;
    private final ConcretePlayer player2Att;
    private boolean isPlayer2sTurn;
    private boolean isGameFinished;

    //private Position[][] PosMap;


    public GameLogic(){
       // PosMap = new Position[BOARDSIZE][BOARDSIZE];
        //PieceMap = new ConcretePiece[BOARDSIZE][BOARDSIZE]; //happens when reset()
        this.player1Def = new ConcretePlayer(true);
        this.player2Att = new ConcretePlayer(false);
        isPlayer2sTurn = true;
        //isGameFinished = false;
        reset();
    }
    @Override
    public Piece getPieceAtPosition(Position position) {
        return PieceMap[position.getX()][position.getY()];
    }

    @Override
    public Player getFirstPlayer() {
        return player1Def;
    }

    @Override
    public Player getSecondPlayer() {
        return player2Att;
    }

    @Override
    public boolean isGameFinished() {
        return isGameFinished;
    }

    @Override
    public boolean isSecondPlayerTurn() {
        return isPlayer2sTurn;
    }

    @Override
    public void reset() {
        PieceMap = new ConcretePiece[BOARDSIZE][BOARDSIZE];
        isPlayer2sTurn = true;
        isGameFinished = false;
        Pieces.clear();

        //place playerOnes pieces
        for (int i = 3; i < 8; i++) {
            PieceMap[i][0] = new Pawn(player2Att);
            PieceMap[i][10] = new Pawn(player2Att);
            PieceMap[0][i] = new Pawn(player2Att);
            PieceMap[10][i] = new Pawn(player2Att);
            Pieces.add(PieceMap[i][0]);
            Pieces.add(PieceMap[i][10]);
            Pieces.add(PieceMap[0][i]);
            Pieces.add(PieceMap[10][i]);
        }
        PieceMap[5][1] = new Pawn(player2Att);
        PieceMap[9][5] = new Pawn(player2Att);
        PieceMap[5][9] = new Pawn(player2Att);
        PieceMap[1][5] = new Pawn(player2Att);
        Pieces.add(PieceMap[5][1]);
        Pieces.add(PieceMap[9][5]);
        Pieces.add(PieceMap[5][9]);
        Pieces.add(PieceMap[1][5]);


        //PlayerTwos pieces
        PieceMap[5][5] = new King(player1Def);
        PieceMap[5][3] = new Pawn(player1Def);
        PieceMap[5][4] = new Pawn(player1Def);
        PieceMap[5][6] = new Pawn(player1Def);
        PieceMap[4][6] = new Pawn(player1Def);
        PieceMap[4][4] = new Pawn(player1Def);
        PieceMap[4][5] = new Pawn(player1Def);
        Pieces.add(PieceMap[5][5]);
        Pieces.add(PieceMap[5][3]);
        Pieces.add(PieceMap[5][4]);
        Pieces.add(PieceMap[5][6]);
        Pieces.add(PieceMap[4][6]);
        Pieces.add(PieceMap[4][4]);
        Pieces.add(PieceMap[4][5]);


       // PieceMap[4][6] = new Pawn(playerTwoD);
        PieceMap[3][5] = new Pawn(player1Def);
        PieceMap[6][4] = new Pawn(player1Def);
        PieceMap[6][5] = new Pawn(player1Def);
        PieceMap[6][6] = new Pawn(player1Def);
        PieceMap[7][5] = new Pawn(player1Def);
        PieceMap[5][7] = new Pawn(player1Def);
        Pieces.add(PieceMap[3][5]);
        Pieces.add(PieceMap[6][4]);
        Pieces.add(PieceMap[6][5]);
        Pieces.add(PieceMap[6][6]);
        Pieces.add(PieceMap[7][5]);
        Pieces.add(PieceMap[5][7]);

    }

    @Override
    public void undoLastMove() {

    }

    @Override
    public int getBoardSize() {
        return BOARDSIZE;
    }

    @Override
    public boolean move(Position a, Position b) {
        if (PieceMap[b.getX()][b.getY()]!=null||(a.getX()!=b.getX()&&a.getY()!=b.getY())||PieceInWay(a,b)||!(0<=b.getX()&&b.getX()<=10)||!(0<=a.getX()&&a.getX()<=10)||PieceMap[a.getX()][a.getY()].getOwner().isPlayerOne() == isPlayer2sTurn||(((b.getY()==0 && b.getX() == 0)||(b.getY()==0 && b.getX() == 10)||(b.getY()==10 && b.getX() == 0)||(b.getY()==10 && b.getX() == 10))&&!PieceMap[a.getX()][a.getY()].getType().equals("♔")))
        return false;

        else{
        PieceMap[b.getX()][b.getY()] = PieceMap[a.getX()][a.getY()];
        PieceMap[a.getX()][a.getY()] = null;


        KillIfKillingMove(PieceMap[b.getX()][b.getY()],b);
        CheckIfGameFinished(b);
        isPlayer2sTurn = !isPlayer2sTurn;
        moveHistory.add(new Move(a,b));/////////////////
        return true;}
    }

    private boolean PieceInWay(Position a, Position b){
        if(a.getX() == b.getX()){
            int max;
            int min;
            if(a.getY() > b.getY()){
                max=a.getY();
                min=b.getY();
            }else{
                max=b.getY();
                min=a.getY();
            }

            for(int i = min+1;i<max;i++){
                if(PieceMap[a.getX()][i]!=null)
                    return true;
            }
        }

        else if(a.getY() == b.getY()){
            int max;
            int min;
            if(a.getX() > b.getX()){
                max=a.getX();
                min=b.getX();
            }else{
                max=b.getX();
                min=a.getX();
            }

            for(int i = min+1;i<max;i++){
                if(PieceMap[i][a.getY()]!=null)
                    return true;
            }
        }

        return false;
    }
private void KillIfKillingMove(ConcretePiece piece,Position point){
        if(piece.getType().equals("♔"))
            return;
        //check if squeezed between kill
        if(point.getX()+1<=10&&point.getX()+2<=10){
        if(PieceMap[point.getX()+1][point.getY()]!=null&&PieceMap[point.getX()+2][point.getY()]!=null){
        if(!PieceMap[point.getX()+1][point.getY()].getType().equals("♔")&&PieceMap[point.getX()+1][point.getY()].getOwner().isPlayerOne()!=piece.getOwner().isPlayerOne()&&PieceMap[point.getX()+2][point.getY()].getOwner().isPlayerOne() == piece.getOwner().isPlayerOne()){
            Pieces.remove(PieceMap[point.getX()+1][point.getY()]);
            PieceMap[point.getX()+1][point.getY()] = null;
        }}}
    if(point.getX()-1>=0&&point.getX()-2>=0){
        if(PieceMap[point.getX()-1][point.getY()]!=null&&PieceMap[point.getX()-2][point.getY()]!=null){
        if(!PieceMap[point.getX()-1][point.getY()].getType().equals("♔")&&PieceMap[point.getX()-1][point.getY()].getOwner().isPlayerOne()!=piece.getOwner().isPlayerOne()&&PieceMap[point.getX()-2][point.getY()].getOwner().isPlayerOne() == piece.getOwner().isPlayerOne()){
            Pieces.remove(PieceMap[point.getX()-1][point.getY()]);
            PieceMap[point.getX()-1][point.getY()] = null;
        }}
    }
    if(point.getY()+1<=10&&point.getY()+2<=10){
        if(PieceMap[point.getX()][point.getY()+1]!=null&&PieceMap[point.getX()][point.getY()+2]!=null){
        if(!PieceMap[point.getX()][point.getY()+1].getType().equals("♔")&&PieceMap[point.getX()][point.getY()+1].getOwner().isPlayerOne()!=piece.getOwner().isPlayerOne()&&PieceMap[point.getX()][point.getY()+2].getOwner().isPlayerOne() == piece.getOwner().isPlayerOne()){
            Pieces.remove(PieceMap[point.getX()][point.getY()+1]);
            PieceMap[point.getX()][point.getY()+1] = null;
        }}
    }
    if(point.getY()-1>=0&&point.getY()-2>=0){
        if(PieceMap[point.getX()][point.getY()-1]!=null&&PieceMap[point.getX()][point.getY()-2]!=null){
        if(!PieceMap[point.getX()][point.getY()-1].getType().equals("♔")&&PieceMap[point.getX()][point.getY()-1].getOwner().isPlayerOne()!=piece.getOwner().isPlayerOne()&&PieceMap[point.getX()][point.getY()-2].getOwner().isPlayerOne() == piece.getOwner().isPlayerOne()){
            Pieces.remove(PieceMap[point.getX()][point.getY()-1]);
            PieceMap[point.getX()][point.getY()-1] = null;
        }}
    }
//check if up to a WALL kill
    if(point.getX()+1<=10&&PieceMap[point.getX()+1][point.getY()]!=null&&point.getX()+2>10){
        if(!PieceMap[point.getX()+1][point.getY()].getType().equals("♔")&&PieceMap[point.getX()+1][point.getY()].getOwner().isPlayerOne()!= piece.getOwner().isPlayerOne()){
            Pieces.remove(PieceMap[point.getX()+1][point.getY()]);
            PieceMap[point.getX()+1][point.getY()] = null;
        }
    }

    if(point.getX()-1>=0&&PieceMap[point.getX()-1][point.getY()]!=null&&point.getX()-2<0){
        if(!PieceMap[point.getX()-1][point.getY()].getType().equals("♔")&&PieceMap[point.getX()-1][point.getY()].getOwner().isPlayerOne()!= piece.getOwner().isPlayerOne()) {
            Pieces.remove(PieceMap[point.getX() - 1][point.getY()]);
            PieceMap[point.getX() - 1][point.getY()] = null;
        }
    }
    if(point.getY()+1<=10&&PieceMap[point.getX()][point.getY()+1]!=null&&point.getY()+2>10){
        if(!PieceMap[point.getX()][point.getY()+1].getType().equals("♔")&&PieceMap[point.getX()][point.getY()+1].getOwner().isPlayerOne()!= piece.getOwner().isPlayerOne()) {
            Pieces.remove(PieceMap[point.getX()][point.getY() + 1]);
            PieceMap[point.getX()][point.getY() + 1] = null;
        }
    }
    if(point.getY()-1>=0&&PieceMap[point.getX()][point.getY()-1]!=null&&point.getY()-2<0){
        if(!PieceMap[point.getX()][point.getY()-1].getType().equals("♔")&&PieceMap[point.getX()][point.getY()-1].getOwner().isPlayerOne()!= piece.getOwner().isPlayerOne()) {
            Pieces.remove(PieceMap[point.getX()][point.getY() - 1]);
            PieceMap[point.getX()][point.getY() - 1] = null;
        }
    }
    //check if up to a CORNER kill
    if(point.getX()-1==1 && point.getX()-2==0){
        if(PieceMap[point.getX()-1][point.getY()]!=null&& (point.getY() == 0||point.getY() == 10)){
            if(!PieceMap[point.getX()-1][point.getY()].getType().equals("♔")&&PieceMap[point.getX()-1][point.getY()].getOwner().isPlayerOne()!=piece.getOwner().isPlayerOne()){
                Pieces.remove(PieceMap[point.getX()-1][point.getY()]);
                PieceMap[point.getX()-1][point.getY()] = null;
            }
        }
    }
    if(point.getX()+1==9 && point.getX()+2==10){
        if(PieceMap[point.getX()+1][point.getY()]!=null && (point.getY() == 0||point.getY() == 10)){
            if(!PieceMap[point.getX()+1][point.getY()].getType().equals("♔")&&PieceMap[point.getX()+1][point.getY()].getOwner().isPlayerOne()!=piece.getOwner().isPlayerOne()){
                Pieces.remove(PieceMap[point.getX()+1][point.getY()]);
                PieceMap[point.getX()+1][point.getY()] = null;
            }
        }
    }
    if(point.getY()-1==1&&point.getY()-2 == 0){
        if(PieceMap[point.getX()][point.getY()-1]!=null&& (point.getX() == 0||point.getX() == 10)){
            if(!PieceMap[point.getX()][point.getY()-1].getType().equals("♔")&&PieceMap[point.getX()][point.getY()-1].getOwner().isPlayerOne()!=piece.getOwner().isPlayerOne()){
                Pieces.remove(PieceMap[point.getX()][point.getY()-1]);
                PieceMap[point.getX()][point.getY()-1] = null;
            }
        }
    }
    if(point.getY()+1<=10&&point.getY()+2 == 10){
        if(PieceMap[point.getX()][point.getY()+1]!=null && (point.getX() == 0||point.getX() == 10)){
            if(!PieceMap[point.getX()][point.getY()+1].getType().equals("♔")&&PieceMap[point.getX()][point.getY()+1].getOwner().isPlayerOne()!=piece.getOwner().isPlayerOne()){
                Pieces.remove(PieceMap[point.getX()][point.getY()+1]);
                PieceMap[point.getX()][point.getY()+1] = null;
            }
        }
    }

    }

    private void CheckIfGameFinished(Position b){
        //check if king is in corner
        if((((b.getY()==0 && b.getX() == 0)||(b.getY()==0 && b.getX() == 10)||(b.getY()==10 && b.getX() == 0)||(b.getY()==10 && b.getX() == 10))&&PieceMap[b.getX()][b.getY()].getType().equals("♔"))){
            isGameFinished = true;
            player1Def.Win();}

         /*if((b.getX()+1 >10||(PieceMap[b.getX()+1][b.getY()] != null &&!PieceMap[b.getX()+1][b.getY()].getOwner().isPlayerOne()))&&(b.getY()-1<0||(PieceMap[b.getX()][b.getY()-1] != null&&!PieceMap[b.getX()][b.getY()-1].getOwner().isPlayerOne()))&& (b.getX()-1<0||(PieceMap[b.getX()-1][b.getY()] != null&&!PieceMap[b.getX()-1][b.getY()].getOwner().isPlayerOne()))&&(b.getY()+1>10||(PieceMap[b.getX()][b.getY()+1]!= null&&!PieceMap[b.getX()][b.getY()+1].getOwner().isPlayerOne()))){
            isGameFinished = true;
            player2Att.Win();
        }*/

        // check if king is adjacent to the piece attacker moves
        if(isPlayer2sTurn){
        if(b.getX()+1 <=10 &&PieceMap[b.getX()+1][b.getY()] != null&&PieceMap[b.getX()+1][b.getY()].getType().equals("♔")){
            if(CheckIfKingSurrounded(b.getX()+1,b.getY())){
                isGameFinished = true;
                player2Att.Win();
            }
        }
        if(b.getY()+1<= 10&&PieceMap[b.getX()][b.getY()+1] != null&&PieceMap[b.getX()][b.getY()+1].getType().equals("♔")){
            if(CheckIfKingSurrounded(b.getX(),b.getY()+1)){
                isGameFinished = true;
                player2Att.Win();
            }
        }
        if(b.getX()-1 >= 0&&PieceMap[b.getX()-1][b.getY()] != null&&PieceMap[b.getX()-1][b.getY()].getType().equals("♔")){
            if(CheckIfKingSurrounded(b.getX()-1,b.getY())){
                isGameFinished = true;
                player2Att.Win();
            }
        }
        if(b.getY()-1 >= 0&&PieceMap[b.getX()][b.getY()-1] != null&&PieceMap[b.getX()][b.getY()-1].getType().equals("♔")){
            if(CheckIfKingSurrounded(b.getX(),b.getY()-1)){
                isGameFinished = true;
                player2Att.Win();
            }
        }}

        if(NoMoreAttPieces()){
            isGameFinished = true;
            player1Def.Win();
        }
    }

    private boolean CheckIfKingSurrounded(int x, int y){
        if(!PieceMap[x][y].getType().equals("♔"))
            return false;
        boolean surrounded = false;
        if(x +1 >10||(PieceMap[x +1][y] != null &&!PieceMap[x+1][y].getOwner().isPlayerOne())&&(y-1<0||(PieceMap[x][y-1] != null&&!PieceMap[x][y-1].getOwner().isPlayerOne()))&& (x-1<0||(PieceMap[x-1][y] != null&&!PieceMap[x-1][y].getOwner().isPlayerOne()))&&(y+1>10||(PieceMap[x][y+1]!= null&&!PieceMap[x][y+1].getOwner().isPlayerOne())))
            surrounded = true;
        return surrounded;
    }
    private boolean NoMoreAttPieces(){
        for (ConcretePiece piece : Pieces)
            if (piece.getOwner() == player2Att)
                return false;
        return true;
    }
    public class Move{
        Position startPos;
        Position endPos;
        ConcretePiece deletedPiece;
        public Move(Position a, Position b,ConcretePiece deletedPiece){
            this.startPos = a;
            this.endPos = b;
            this.deletedPiece = deletedPiece;

        }
        public Move(Position a, Position b){
            this.startPos = a;
            this.endPos = b;
            deletedPiece = null;

        }
    }
}
