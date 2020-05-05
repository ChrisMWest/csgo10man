import java.util.*;

public class Game{

	List<bigPlayer> lobby = new ArrayList<bigPlayer>();
	List<bigPlayer> team1 = new ArrayList<bigPlayer>();
	List<bigPlayer> team2 = new ArrayList<bigPlayer>();

	public static void main(String[] args) throws notCaptainException{
		Game newGame = new Game();
		newGame.addPlayer("Chris", false);
		newGame.addPlayer("vernon", false);
		newGame.addPlayer("Darwin", false);
		newGame.addPlayer("Takuma", false);
		newGame.addPlayer("Anu", false);
		newGame.addPlayer("jaay", false);
		newGame.addPlayer("gordon", false);
		newGame.addPlayer("john", false);
		newGame.addPlayer("ewan", false);
		newGame.addPlayer("naoya", false);
		//newGame.removePlayer();
		/*System.out.println("lobby before: " + newGame.lobby.toString());
		System.out.println("team1 before: " + newGame.team1.toString());
		System.out.println("team2 before: " + newGame.team2.toString());*/
		newGame.chooseCaptains(newGame.lobby);
		/*System.out.println("team1 after picking captains: " + newGame.team1.toString());
		System.out.println("team2 after picking captains: " + newGame.team2.toString());*/
		newGame.captainChoice(newGame.team1.get(0), newGame.team2.get(0), newGame.lobby.get(0), newGame.lobby);
		//newGame.addPlayerToTeam(newGame.lobby.get(0), newGame.team1);
		/*System.out.println("lobby after: " + newGame.lobby.toString());
		System.out.println("team1 after: " + newGame.team1.toString());
		System.out.println("team2 after: " + newGame.team2.toString());*/
		for(int i = 0; i < 5; i++){
			System.out.println("Team 1:" + newGame.team1.get(i).getName());
			System.out.println("Team 2:" + newGame.team2.get(i).getName());
			/*System.out.println(newGame.lobby.get(i).isCaptain);
			System.out.println(newGame.lobby.get(i).toString());*/
		}
	}

	public Game(){
	}

	/*public Game(String[] thisLobby, String[] thisTeam1, String[] thisTeam2){
		this.lobby = thisLobby;
		this.team1 = thisTeam1;
		this.team2 = thisTeam2;
	}*/

	public void addPlayer(String playerName, boolean captain){
		bigPlayer newPlayer = new bigPlayer(playerName, captain);
		lobby.add(newPlayer);
	}

	public void removePlayer(bigPlayer player, List<bigPlayer> placement){
		int placementLength = placement.size();
		int i = 0;
		while(i < placementLength){
			if(placement.get(i).name.equals(player.name)){
				placement.remove(i);
				break;
			}
			i++;
		}
	}

	public void captainChoice(bigPlayer captain1, bigPlayer captain2, bigPlayer player, List<bigPlayer> lobby) throws notCaptainException{
		if(captain1.isCaptain == false){
			throw new notCaptainException("this person is not a captain");
		}
		if(captain2.isCaptain == false){
			throw new notCaptainException("this person is not a captain");
		}
		boolean captain1Choice = true;
		while(!this.lobby.isEmpty()){
			/*Random random = new Random();
			random = */
			if(captain1Choice == true){
				addPlayerToTeam(lobby.get(0), team1);
				removePlayer(lobby.get(0), lobby);
				captain1Choice = false;
			}else{
				addPlayerToTeam(lobby.get(0), team2);
				removePlayer(lobby.get(0), lobby);
				captain1Choice = true;
			}
		}
	}

	public void addPlayerToTeam(bigPlayer player, List<bigPlayer> team){
		team.add(player);
	}

	public void chooseCaptains(List<bigPlayer> lobby){
		Random random = new Random();
		int first, second = 0;
		first = random.nextInt(10);
		lobby.get(first).isCaptain = true;
		addPlayerToTeam(lobby.get(first), team1);
		removePlayer(lobby.get(first), lobby);
		int i = 0;
		while(i < 10){
			second = random.nextInt(9);
			if(second != first) break;
			else i++;
		}
		//System.out.println("second: " + second);
		lobby.get(second).isCaptain = true;
		addPlayerToTeam(lobby.get(second), team2);
		removePlayer(lobby.get(second), lobby);
	}

	public void passCaptain(bigPlayer currentCaptain, bigPlayer futureCaptain){
		currentCaptain.isCaptain = false;
		futureCaptain.isCaptain = true;
	}

	/*public String getLobby(){
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
	}*/
}

class bigPlayer{
	String name = "";
	boolean isCaptain = false;

	bigPlayer(String playerName, boolean captain){
		this.name = playerName;
		this.isCaptain = captain;
	}

	public String getName(){
		return this.name;
	}
}
