import java.util.*;

public class Game{

	String[] lobby = new String[10];
	String[] team1 = new String[5];
	String[] team2 = new String[5];
	int lobbyCount = 0;

	public static void main(String[] args){
		Game newGame = new Game();
		newGame.addPlayer("Chris");
		newGame.addPlayer("vernon");
		newGame.addPlayer("Darwin");
		newGame.addPlayer("Takuma");
		newGame.addPlayer("Anu");
		newGame.addPlayer("jaay");
		newGame.addPlayer("gordon");
		newGame.addPlayer("john");
		newGame.addPlayer("ewan");
		newGame.addPlayer("naoya");
		System.out.println(newGame.getInfo(newGame));
	}

	public Game(){
	}

	/*public Game(String[] thisLobby, String[] thisTeam1, String[] thisTeam2){
		this.lobby = thisLobby;
		this.team1 = thisTeam1;
		this.team2 = thisTeam2;
	}*/

	public void addPlayer(String player){
		this.lobby[lobbyCount] = player;
		lobbyCount++;
	}

	public String getLobby(){
		String lobbyString = Arrays.toString(this.lobby);
		return lobbyString;
	}

	public String getTeam1(){
		String team1String = Arrays.toString(this.team1);
		return team1String;
	}

	public String getTeam2(){
		String team2String = Arrays.toString(this.team2);
		return team2String;
	}

	public String getInfo(Game newGame){
		String newGameLobby = newGame.getLobby();
		String newGameTeam1 = newGame.getTeam1();
		String newGameTeam2 = newGame.getTeam2();
		return "Lobby: " + newGameLobby + "\nTeam1: " + newGameTeam1 + "\nTeam2: " + newGameTeam2;
	}

}