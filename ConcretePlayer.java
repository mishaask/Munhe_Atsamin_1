public class ConcretePlayer implements Player{
    int Wins;
    boolean isPlayerOne; //player One is Defender and plays on the second turn
    public ConcretePlayer (boolean isPlayerOne){
        this.isPlayerOne = isPlayerOne;
        this.Wins = getWins();
    }
    @Override
    public boolean isPlayerOne() {
        return isPlayerOne;
    }

    @Override
    public int getWins() {
        return Wins;
    }
   /* public void resetWins() {
        Wins = 0;
    }*/

    public void Win(){
        Wins++;
    }
}
