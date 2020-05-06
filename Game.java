import java.util.*;

public class Game{

	List<bigPlayer> lobby = new ArrayList<bigPlayer>();
	List<bigPlayer> team1 = new ArrayList<bigPlayer>();
	List<bigPlayer> team2 = new ArrayList<bigPlayer>();
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) throws notCaptainException{
		Game newGame = new Game();
		int i = 0;
		while(newGame.lobby.size() != 10){
			System.out.println("Please enter your name: ");
			String s = in.nextLine().trim();
			while(s.length() == 0){
				System.out.println("Please enter a valid name: ");
				s = in.nextLine().trim();
			}
			newGame.addPlayer(s, false, i);
			i++;
		}
		System.out.println("The lobby is now full");
		newGame.chooseCaptains(newGame.lobby);
		newGame.captainChoice(newGame.team1.get(0), newGame.team2.get(0), newGame.lobby);		/*for(int j = 0; j<5; j++){
			System.out.println("Team 1: " + newGame.team1.get(i).getName());
			System.out.println("Team 2: " + newGame.team2.get(i).getName());
		}*/
		
		/*System.out.println("lobby before: " + newGame.lobby.toString());
		System.out.println("team1 before: " + newGame.team1.toString());
		System.out.println("team2 before: " + newGame.team2.toString());
		newGame.chooseCaptains(newGame.lobby);
		System.out.println("team1 after picking captains: " + newGame.team1.toString());
		System.out.println("team2 after picking captains: " + newGame.team2.toString());
		newGame.captainChoice(newGame.team1.get(0), newGame.team2.get(0), newGame.lobby.get(0), newGame.lobby);
		newGame.addPlayerToTeam(newGame.lobby.get(0), newGame.team1);
		System.out.println("lobby after: " + newGame.lobby.toString());
		System.out.println("team1 after: " + newGame.team1.toString());
		System.out.println("team2 after: " + newGame.team2.toString());
		for(int i = 0; i < 5; i++){
			System.out.println("Team 1:" + newGame.team1.get(i).getName());
			System.out.println("Team 2:" + newGame.team2.get(i).getName());
			System.out.println(newGame.lobby.get(i).isCaptain);
			System.out.println(newGame.lobby.get(i).toString());
		}*/
	}

	public Game(){
	}

	public void addPlayer(String playerName, boolean iscaptain, int id){
		bigPlayer newPlayer = new bigPlayer(playerName, iscaptain, id);
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

	public void captainChoiceSecondary(HashSet<String> setOfPlayers, int captainNum){
		for(int i = 0; i<lobby.size(); i++){
			System.out.println(lobby.get(i).getName() + " - " + lobby.get(i).getId());
		}
		bigPlayer temp = new bigPlayer();
		System.out.println("Captain " + captainNum + ", please choose a player using their name:");
		String s = in.nextLine();
		boolean isIncluded = false;
		while(isIncluded == false){
			if(setOfPlayers.contains(s)){
				isIncluded = true;
				break;
			}else{
				System.out.print("Please enter a valid name: ");
				s = in.nextLine();
			}
		}
		for(int i = 0; i < lobby.size(); i++){
			if(lobby.get(i).getName().equals(s)){
				temp = lobby.get(i);
				break;
			}
		}
		addPlayerToTeam(temp, team1);
		removePlayer(temp, lobby);
		System.out.print("This is the current Lobby ");
		for(int i = 0; i < lobby.size(); i++){
			System.out.print(lobby.get(i).getName() + " ");
		}
		System.out.println();
		System.out.print("This is the current Team1 ");
		for(int i = 0; i < team1.size(); i++){
			System.out.print(team1.get(i).getName() + " ");
		}
		System.out.println();
		System.out.print("This is the current Team2 ");
		for(int i = 0; i < team2.size(); i++){
			System.out.print(team2.get(i).getName() + " ");
		}
		System.out.println();
	}

	public void captainChoice(bigPlayer captain1, bigPlayer captain2, List<bigPlayer> lobby) throws notCaptainException{
		if(captain1.isCaptain == false){
			throw new notCaptainException("this person is not a captain");
		}
		if(captain2.isCaptain == false){
			throw new notCaptainException("this person is not a captain");
		}
		System.out.println("Captains are choosing teams");
		boolean captain1Choice = true;
		boolean captain2Pick2 = true;
		boolean captain1Pick2 = false;
		while(!this.lobby.isEmpty()){
			HashSet<String> setOfPlayers = new HashSet<String>();
			for(int i = 0; i < lobby.size(); i++){
				setOfPlayers.add(lobby.get(i).getName());
			}
			if(captain1Choice == true){
				captainChoiceSecondary(setOfPlayers, 1);
				captain1Choice = false;
			}if(captain1Pick2 == true){
				captainChoiceSecondary(setOfPlayers, 1);
				captain1Choice = false;
				captain1Pick2 = false;
			}
			if(captain1Choice == false){
				captainChoiceSecondary(setOfPlayers, 2);
				captain1Choice = true;
			}
			if(captain2Pick2 == true){
				captainChoiceSecondary(setOfPlayers, 2);
				captain2Pick2 = false;
				captain1Pick2 = true;
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
	int playerId = 0;

	bigPlayer(){
	}

	bigPlayer(String playerName, boolean captain, int id){
		this.name = playerName;
		this.isCaptain = captain;
		this.playerId = id;
	}

	public String getName(){
		return this.name;
	}

	public int getId(){
		return this.playerId;
	}
}
