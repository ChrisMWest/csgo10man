import java.util.*;

public class Game{

	List<Player> lobby = new ArrayList<Player>();
	List<Player> team1 = new ArrayList<Player>();
	List<Player> team2 = new ArrayList<Player>();
	List<Map> mapList = new ArrayList<Map>();
	List<Player> team1MapPickers = new ArrayList<Player>();
	List<Player> team2MapPickers = new ArrayList<Player>();
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
		newGame.captainPickPlayers(newGame.team1.get(0), newGame.team2.get(0), newGame.lobby);
		System.out.println("Available Competitive Maps");
		newGame.addMap("Mirage");
		newGame.addMap("Inferno");
		newGame.addMap("Overpass");
		newGame.addMap("Nuke");
		newGame.addMap("Train");
		newGame.addMap("Dust2");
		newGame.addMap("Cache");
		for(int j = 0; j < newGame.mapList.size(); j++){
			System.out.print(newGame.mapList.get(j).getMapName() + " ");
		}
		System.out.println();
		//2 players from team 1
		newGame.selectPlayersPickingMaps(newGame.team1, newGame.team1MapPickers);
		//2 players from team 2
		newGame.selectPlayersPickingMaps(newGame.team2, newGame.team2MapPickers);
		System.out.println("These players will be picking the maps:");
		System.out.println(newGame.team1MapPickers.get(0).getName() + " " +  newGame.team1MapPickers.get(1).getName());
		System.out.println(newGame.team2MapPickers.get(0).getName() + " " +  newGame.team2MapPickers.get(1).getName());
		//Players choosing maps to ban
		newGame.mapBanning(newGame.team1MapPickers.get(0));
		newGame.mapBanning(newGame.team2MapPickers.get(0));
		newGame.mapBanning(newGame.team2MapPickers.get(1));
		newGame.mapBanning(newGame.team1MapPickers.get(1));
		System.out.println("These are the available maps after the ban:");
		for(int j = 0; j < newGame.mapList.size(); j++){
			System.out.print(newGame.mapList.get(j).getMapName() + " ");
		}
		Collections.shuffle(newGame.mapList);
		for(int j = 0; j < newGame.mapList.size(); j++){
			System.out.print(newGame.mapList.get(j).getMapName() + " ");
		}
	}

	public Game(){
	}

	public void addPlayer(String playerName, boolean iscaptain, int id){
		Player newPlayer = new Player(playerName, iscaptain, id);
		lobby.add(newPlayer);
	}

	public void removePlayer(Player player, List<Player> placement){
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

	public void addMap(String map){
		Map newMap = new Map(map);
		mapList.add(newMap);
	}

	public void selectPlayersPickingMaps(List<Player> team, List<Player> teamMapPickers){
		Random rand = new Random();
		int first = rand.nextInt(5);
		teamMapPickers.add(team.get(first));
		int second = rand.nextInt(5);
		while(second == first){
			second = rand.nextInt(5);
		}
		teamMapPickers.add(team.get(second));
	}

	public void mapBanning(Player playerSelectingMapToBan){
		System.out.println(playerSelectingMapToBan.getName() + " - Please Select a map to ban from the following: ");
		for(int j = 0; j < mapList.size(); j++){
			System.out.print(mapList.get(j).getMapName() + " ");
		}
		System.out.println();
		HashSet<String> setOfPlayers = new HashSet<String>();
		for(int i = 0; i < mapList.size(); i++){
			setOfPlayers.add(mapList.get(i).getMapName());
		}
		String s = in.nextLine();
		boolean isIncluded = false;
		while(isIncluded == false){
			if(setOfPlayers.contains(s)){
				isIncluded = true;
				break;
			}else{
				System.out.print("Please enter a valid map name: ");
				s = in.nextLine();
			}
		}
		for(int i = 0; i < mapList.size(); i++){
			if(mapList.get(i).getMapName().equals(s)){
				System.out.println(mapList.get(i).getMapName() + " has been banned.");
				mapList.remove(mapList.get(i));
				break;
			}
		}
	}

	public void captainPickPlayersHelper(HashSet<String> setOfPlayers, int captainNum, List<Player> team){
		for(int i = 0; i<lobby.size(); i++){
			System.out.println(lobby.get(i).getName() + " - " + lobby.get(i).getId());
		}
		Player temp = new Player();
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
		addPlayerToTeam(temp, team);
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

	public void captainPickPlayers(Player captain1, Player captain2, List<Player> lobby) throws notCaptainException{
		if(captain1.isCaptain == false){
			throw new notCaptainException("this person is not a captain");
		}
		if(captain2.isCaptain == false){
			throw new notCaptainException("this person is not a captain");
		}
		System.out.println("Captains are choosing teams:");
		boolean captain1Choice = true;
		boolean captain2Pick2 = true;
		boolean captain1Pick2 = false;
		while(!this.lobby.isEmpty()){
			HashSet<String> setOfPlayers = new HashSet<String>();
			for(int i = 0; i < lobby.size(); i++){
				setOfPlayers.add(lobby.get(i).getName());
			}
			if(captain1Choice == true){
				captainPickPlayersHelper(setOfPlayers, 1, team1);
				captain1Choice = false;
			}if(captain1Pick2 == true){
				captainPickPlayersHelper(setOfPlayers, 1, team1);
				captain1Choice = false;
				captain1Pick2 = false;
			}
			if(captain1Choice == false){
				captainPickPlayersHelper(setOfPlayers, 2, team2);
				captain1Choice = true;
			}
			if(captain2Pick2 == true){
				captainPickPlayersHelper(setOfPlayers, 2, team2);
				captain2Pick2 = false;
				captain1Pick2 = true;
			}
		}
	}

	public void addPlayerToTeam(Player player, List<Player> team){
		team.add(player);
	}

	public void chooseCaptains(List<Player> lobby){
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

	public void passCaptain(Player currentCaptain, Player futureCaptain){
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

class Map{
	String mapName = "";

	Map(){
	}

	Map(String map){
		this.mapName = map;
	}

	public String getMapName(){
		return this.mapName;
	}
}

class Player{
	String name = "";
	boolean isCaptain = false;
	int playerId = 0;

	Player(){
	}

	Player(String playerName, boolean captain, int id){
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
