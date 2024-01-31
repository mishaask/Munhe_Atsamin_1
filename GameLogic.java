import java.util.ArrayList;
import java.util.Stack;
import java.util.Comparator;


public class GameLogic  implements PlayableLogic{

    private final int BOARDSIZE = 11;
    private ConcretePiece[][] PieceMap;
    private Position[][] PosMap;
    private ArrayList<ConcretePiece> Pieces = new ArrayList<>();
    private ArrayList<Position> ReleventPositions = new ArrayList<>();
    private Stack<Move> moveStack = new Stack<>();
    private final ConcretePlayer player1Def;
    private final ConcretePlayer player2Att;
    private ConcretePlayer Winner;
    private ConcretePiece deletedPiece;
    private boolean isPlayer2sTurn;
    private boolean isGameFinished;



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
        PosMap = new Position[BOARDSIZE][BOARDSIZE];
        isPlayer2sTurn = true;
        isGameFinished = false;
        Pieces.clear();
        moveStack.empty();
        for(int x= 0; x < BOARDSIZE;x++){
            for(int y=0;y<BOARDSIZE;y++){
                PosMap[x][y] = new Position(y,x);
            }

        }

        //place playerOnes pieces
        for (int i = 3; i < 8; i++) {
            PieceMap[i][0] = new Pawn(player2Att,new Position(i,0));
            PieceMap[i][10] = new Pawn(player2Att,new Position(i,10));
            PieceMap[0][i] = new Pawn(player2Att,new Position(0,i));
            PieceMap[10][i] = new Pawn(player2Att,new Position(10,i));
            Pieces.add(PieceMap[i][0]);
            Pieces.add(PieceMap[i][10]);
            Pieces.add(PieceMap[0][i]);
            Pieces.add(PieceMap[10][i]);
        }
        PieceMap[5][1] = new Pawn(player2Att,new Position(5,1));
        PieceMap[9][5] = new Pawn(player2Att,new Position(9,5));
        PieceMap[5][9] = new Pawn(player2Att,new Position(5,9));
        PieceMap[1][5] = new Pawn(player2Att,new Position(1,5));
        Pieces.add(PieceMap[5][1]);
        Pieces.add(PieceMap[9][5]);
        Pieces.add(PieceMap[5][9]);
        Pieces.add(PieceMap[1][5]);


        //PlayerTwos pieces
        PieceMap[5][5] = new King(player1Def,new Position(5,5));
        PieceMap[5][3] = new Pawn(player1Def,new Position(5,3));
        PieceMap[5][4] = new Pawn(player1Def,new Position(5,4));
        PieceMap[5][6] = new Pawn(player1Def,new Position(5,6));
        PieceMap[4][6] = new Pawn(player1Def,new Position(4,6));
        PieceMap[4][4] = new Pawn(player1Def,new Position(4,4));
        PieceMap[4][5] = new Pawn(player1Def,new Position(4,5));
        Pieces.add(PieceMap[5][5]);
        Pieces.add(PieceMap[5][3]);
        Pieces.add(PieceMap[5][4]);
        Pieces.add(PieceMap[5][6]);
        Pieces.add(PieceMap[4][6]);
        Pieces.add(PieceMap[4][4]);
        Pieces.add(PieceMap[4][5]);


       // PieceMap[4][6] = new Pawn(playerTwoD);
        PieceMap[3][5] = new Pawn(player1Def,new Position(3,5));
        PieceMap[6][4] = new Pawn(player1Def,new Position(6,4));
        PieceMap[6][5] = new Pawn(player1Def,new Position(6,5));
        PieceMap[6][6] = new Pawn(player1Def,new Position(6,6));
        PieceMap[7][5] = new Pawn(player1Def,new Position(7,5));
        PieceMap[5][7] = new Pawn(player1Def,new Position(5,7));
        Pieces.add(PieceMap[3][5]);
        Pieces.add(PieceMap[6][4]);
        Pieces.add(PieceMap[6][5]);
        Pieces.add(PieceMap[6][6]);
        Pieces.add(PieceMap[7][5]);
        Pieces.add(PieceMap[5][7]);

        //number all pieces
        int attNum=1;
        int defNum=1;
        for(int y = 0; y < 11; y++){
            for(int x = 0; x < 11; x++){
                if(PieceMap[x][y]!=null&&PieceMap[x][y].getOwner().isPlayerOne()){
                    PieceMap[x][y].setNumber(defNum);
                    defNum++;
                }
                if(PieceMap[x][y]!=null&&!PieceMap[x][y].getOwner().isPlayerOne()){
                    PieceMap[x][y].setNumber(attNum);
                    attNum++;
                }
            }
        }

    }

    @Override
    public void undoLastMove() {
        if(moveStack.isEmpty()){
            return;
        }
        Move move = moveStack.pop();
        Position posToGoBackTo = move.startPos;
        Position startPos = move.endPos;
        ConcretePiece pieceToMove = PieceMap[startPos.getX()][startPos.getY()];
        pieceToMove.removeLastMove();
        PieceMap[posToGoBackTo.getX()][posToGoBackTo.getY()] = pieceToMove;
        PieceMap[startPos.getX()][startPos.getY()] = null;
        if(move.deletedPiece != null){
            ConcretePiece pieceToBringBack = move.deletedPiece;
            Position deadPiecesPosition;
            PieceMap[move.deletedPos.getX()][move.deletedPos.getY()] = pieceToBringBack;
        }
        PosMap[startPos.getX()][startPos.getY()].removePiece();
        isPlayer2sTurn = !isPlayer2sTurn;
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
            PieceMap[a.getX()][a.getY()].setPosition(b);
            PieceMap[b.getX()][b.getY()] = PieceMap[a.getX()][a.getY()];
            PieceMap[a.getX()][a.getY()] = null;
            PosMap[b.getY()][b.getX()].addPiece(PieceMap[b.getX()][b.getY()]);
            if(!ReleventPositions.contains( PosMap[b.getY()][b.getX()])){
                ReleventPositions.add( PosMap[b.getY()][b.getX()]);
            }


            KillIfKillingMove(PieceMap[b.getX()][b.getY()],b);
            CheckIfGameFinished(b);
            isPlayer2sTurn = !isPlayer2sTurn;

            if(deletedPiece == null) {
            moveStack.add(new Move(a, b));
            }else{
            Position deletedPos = deletedPiece.position;
            moveStack.add(new Move(a,b,deletedPiece,deletedPos));
            deletedPiece = null;}
            if(isGameFinished){
                GameIsFinished();
            }
        return true;
        }
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
            deletedPiece = PieceMap[point.getX()+1][point.getY()];
            Pieces.remove(PieceMap[point.getX()+1][point.getY()]);
            PieceMap[point.getX()+1][point.getY()] = null;
        }}}
    if(point.getX()-1>=0&&point.getX()-2>=0){
        if(PieceMap[point.getX()-1][point.getY()]!=null&&PieceMap[point.getX()-2][point.getY()]!=null){
        if(!PieceMap[point.getX()-1][point.getY()].getType().equals("♔")&&PieceMap[point.getX()-1][point.getY()].getOwner().isPlayerOne()!=piece.getOwner().isPlayerOne()&&PieceMap[point.getX()-2][point.getY()].getOwner().isPlayerOne() == piece.getOwner().isPlayerOne()){
            deletedPiece = PieceMap[point.getX()-1][point.getY()];
            Pieces.remove(PieceMap[point.getX()-1][point.getY()]);
            PieceMap[point.getX()-1][point.getY()] = null;
        }}
    }
    if(point.getY()+1<=10&&point.getY()+2<=10){
        if(PieceMap[point.getX()][point.getY()+1]!=null&&PieceMap[point.getX()][point.getY()+2]!=null){
        if(!PieceMap[point.getX()][point.getY()+1].getType().equals("♔")&&PieceMap[point.getX()][point.getY()+1].getOwner().isPlayerOne()!=piece.getOwner().isPlayerOne()&&PieceMap[point.getX()][point.getY()+2].getOwner().isPlayerOne() == piece.getOwner().isPlayerOne()){
            deletedPiece = PieceMap[point.getX()][point.getY()+1];
            Pieces.remove(PieceMap[point.getX()][point.getY()+1]);
            PieceMap[point.getX()][point.getY()+1] = null;
        }}
    }
    if(point.getY()-1>=0&&point.getY()-2>=0){
        if(PieceMap[point.getX()][point.getY()-1]!=null&&PieceMap[point.getX()][point.getY()-2]!=null){
        if(!PieceMap[point.getX()][point.getY()-1].getType().equals("♔")&&PieceMap[point.getX()][point.getY()-1].getOwner().isPlayerOne()!=piece.getOwner().isPlayerOne()&&PieceMap[point.getX()][point.getY()-2].getOwner().isPlayerOne() == piece.getOwner().isPlayerOne()){
            deletedPiece = PieceMap[point.getX()][point.getY()-1];
            Pieces.remove(PieceMap[point.getX()][point.getY()-1]);
            PieceMap[point.getX()][point.getY()-1] = null;
        }}
    }
//check if up to a WALL kill
    if(point.getX()+1<=10&&PieceMap[point.getX()+1][point.getY()]!=null&&point.getX()+2>10){
        if(!PieceMap[point.getX()+1][point.getY()].getType().equals("♔")&&PieceMap[point.getX()+1][point.getY()].getOwner().isPlayerOne()!= piece.getOwner().isPlayerOne()){
            deletedPiece = PieceMap[point.getX()+1][point.getY()];
            Pieces.remove(PieceMap[point.getX()+1][point.getY()]);
            PieceMap[point.getX()+1][point.getY()] = null;
        }
    }

    if(point.getX()-1>=0&&PieceMap[point.getX()-1][point.getY()]!=null&&point.getX()-2<0){
        if(!PieceMap[point.getX()-1][point.getY()].getType().equals("♔")&&PieceMap[point.getX()-1][point.getY()].getOwner().isPlayerOne()!= piece.getOwner().isPlayerOne()) {
            deletedPiece = PieceMap[point.getX()-1][point.getY()];
            Pieces.remove(PieceMap[point.getX() - 1][point.getY()]);
            PieceMap[point.getX() - 1][point.getY()] = null;
        }
    }
    if(point.getY()+1<=10&&PieceMap[point.getX()][point.getY()+1]!=null&&point.getY()+2>10){
        if(!PieceMap[point.getX()][point.getY()+1].getType().equals("♔")&&PieceMap[point.getX()][point.getY()+1].getOwner().isPlayerOne()!= piece.getOwner().isPlayerOne()) {
            deletedPiece = PieceMap[point.getX()][point.getY()+1];
            Pieces.remove(PieceMap[point.getX()][point.getY() + 1]);
            PieceMap[point.getX()][point.getY() + 1] = null;
        }
    }
    if(point.getY()-1>=0&&PieceMap[point.getX()][point.getY()-1]!=null&&point.getY()-2<0){
        if(!PieceMap[point.getX()][point.getY()-1].getType().equals("♔")&&PieceMap[point.getX()][point.getY()-1].getOwner().isPlayerOne()!= piece.getOwner().isPlayerOne()) {
            deletedPiece = PieceMap[point.getX()][point.getY()-1];
            Pieces.remove(PieceMap[point.getX()][point.getY() - 1]);
            PieceMap[point.getX()][point.getY() - 1] = null;
        }
    }
    //check if up to a CORNER kill
    if(point.getX()-1==1 && point.getX()-2==0){
        if(PieceMap[point.getX()-1][point.getY()]!=null&& (point.getY() == 0||point.getY() == 10)){
            if(!PieceMap[point.getX()-1][point.getY()].getType().equals("♔")&&PieceMap[point.getX()-1][point.getY()].getOwner().isPlayerOne()!=piece.getOwner().isPlayerOne()){
                deletedPiece = PieceMap[point.getX()-1][point.getY()];
                Pieces.remove(PieceMap[point.getX()-1][point.getY()]);
                PieceMap[point.getX()-1][point.getY()] = null;
            }
        }
    }
    if(point.getX()+1==9 && point.getX()+2==10){
        if(PieceMap[point.getX()+1][point.getY()]!=null && (point.getY() == 0||point.getY() == 10)){
            if(!PieceMap[point.getX()+1][point.getY()].getType().equals("♔")&&PieceMap[point.getX()+1][point.getY()].getOwner().isPlayerOne()!=piece.getOwner().isPlayerOne()){
                deletedPiece = PieceMap[point.getX()+1][point.getY()];
                Pieces.remove(PieceMap[point.getX()+1][point.getY()]);
                PieceMap[point.getX()+1][point.getY()] = null;
            }
        }
    }
    if(point.getY()-1==1&&point.getY()-2 == 0){
        if(PieceMap[point.getX()][point.getY()-1]!=null&& (point.getX() == 0||point.getX() == 10)){
            if(!PieceMap[point.getX()][point.getY()-1].getType().equals("♔")&&PieceMap[point.getX()][point.getY()-1].getOwner().isPlayerOne()!=piece.getOwner().isPlayerOne()){
                deletedPiece = PieceMap[point.getX()][point.getY()-1];
                Pieces.remove(PieceMap[point.getX()][point.getY()-1]);
                PieceMap[point.getX()][point.getY()-1] = null;
            }
        }
    }
    if(point.getY()+1<=10&&point.getY()+2 == 10){
        if(PieceMap[point.getX()][point.getY()+1]!=null && (point.getX() == 0||point.getX() == 10)){
            if(!PieceMap[point.getX()][point.getY()+1].getType().equals("♔")&&PieceMap[point.getX()][point.getY()+1].getOwner().isPlayerOne()!=piece.getOwner().isPlayerOne()){
                deletedPiece = PieceMap[point.getX()][point.getY()+1];
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
            player1Def.Win();
            Winner = player1Def;
        }

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
                Winner = player2Att;
            }
        }
        if(b.getY()+1<= 10&&PieceMap[b.getX()][b.getY()+1] != null&&PieceMap[b.getX()][b.getY()+1].getType().equals("♔")){
            if(CheckIfKingSurrounded(b.getX(),b.getY()+1)){
                isGameFinished = true;
                player2Att.Win();
                Winner = player2Att;
            }
        }
        if(b.getX()-1 >= 0&&PieceMap[b.getX()-1][b.getY()] != null&&PieceMap[b.getX()-1][b.getY()].getType().equals("♔")){
            if(CheckIfKingSurrounded(b.getX()-1,b.getY())){
                isGameFinished = true;
                player2Att.Win();
                Winner = player2Att;
            }
        }
        if(b.getY()-1 >= 0&&PieceMap[b.getX()][b.getY()-1] != null&&PieceMap[b.getX()][b.getY()-1].getType().equals("♔")){
            if(CheckIfKingSurrounded(b.getX(),b.getY()-1)){
                isGameFinished = true;
                player2Att.Win();
                Winner = player2Att;
            }
        }}

        if(NoMoreAttPieces()){
            isGameFinished = true;
            player1Def.Win();
            Winner = player1Def;
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

    private class Move{
        Position startPos;
        Position endPos;
        Position deletedPos;
        ConcretePiece deletedPiece;
        public Move(Position a, Position b,ConcretePiece deletedPiece,Position deletedPos){
            this.startPos = a;
            this.endPos = b;
            this.deletedPiece = deletedPiece;
            this.deletedPos = deletedPos;

        }
        public Move(Position a, Position b){
            this.startPos = a;
            this.endPos = b;
            deletedPiece = null;

        }
    }
    private class PosComparator implements Comparator<Position> {
        public int compare(Position pos1,Position pos2){
            if(pos1.getPiecesSet().size()==pos2.getPiecesSet().size()){
                if(pos1.getX() == pos2.getX()){
                    if(pos1.getY() == pos2.getY()){
                        return 0;
                    }else if(pos1.getY() > pos2.getY()){
                        return -1;
                    }else{
                        return 1;
                    }
                }else if(pos1.getX() > pos2.getX()){
                    return -1;
                }else{
                    return 1;
                }
            }else if(pos1.getPiecesSet().size()>pos2.getPiecesSet().size()){
                return-1;
            }else{
                return 1;
            }
        }
    }
    private class PieceComparator implements Comparator<ConcretePiece> {
        @Override
        public int compare(ConcretePiece piece1, ConcretePiece piece2) {

            if(piece1.getOwner() == piece2.getOwner()){
                if(piece1.getMoves().size() == piece2.getMoves().size()){
                    if(piece1.getNumber()==piece2.getNumber()){
                        return 0;
                    }else if(piece1.getNumber()>piece2.getNumber()){
                        return 1;
                    }else{
                        return -1;
                    }
                }else if(piece1.getMoves().size() > piece2.getMoves().size()){
                    return 1;
                }else{
                    return -1;
                }

            }else{
                if(piece1.getOwner() == Winner){
                    return -1;
                }else{
                    return 1;
                }
            }
        }
    }
    private void GameIsFinished() {
        //Q1
        ArrayList<ConcretePiece> PiecesHelper = new ArrayList<>(Pieces);
        PiecesHelper.sort(new PieceComparator());

        for (ConcretePiece piece : PiecesHelper) {

            System.out.print(piece.getString() + piece.getNumber() + ": [");

            for (int i = 0; i < piece.getMoves().size(); i++) {

                Position move = piece.getMoves().get(i);
                System.out.print("(" + move.getX() + ", " + move.getY() + ")");

                if (i < piece.getMoves().size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
        System.out.println("***************************************************************************");

        //Q4
        ArrayList<Position> PosHelper = new ArrayList<>(ReleventPositions);
        PosHelper.sort(new PosComparator());

        for (Position pos : PosHelper) {
            if(pos.getPiecesSet().size()>=2)
            System.out.println(pos.toString() +pos.getPiecesToString());
        }
    }
}
