using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using UnityEngine.SceneManagement;

public class GameController : MonoBehaviour {

	/// <summary>
	/// This is the main game controller class. It handles tile instantiation, game initiation,
	/// turn management, calculating the score, monitoring and updating global arrays, and almost all
	/// the events that happen inside the game. If you want to start tweaking the framework, this is the
	/// place you need to start. Please feel free to contact us if you ever needed help.
	/// 
	/// Support (24/7): http://www.finalbossgame.com
	/// </summary>

	// !! Not editable //
	public static int tileX = 8;	//we have 8 tile in X direction
	public static int tileY = 8;	//we have 8 tile in Y direction (for a total of 8*8 = 64 tiles)
	// !! Not editable //

	// Static variables //
	public static bool arePlayerShipsVisible;		//flag for showing player ships 
	public static int round;						//internal counter to assign turn to player and AI
	public static bool playersTurn;					//flag to check if this is player's turn
	public static bool opponentsTurn;				//flag to check if this is opponent's turn
	public static bool gameIsStarted;				//global flag for game start state
	public static bool gameIsFinished;				//global flag for game finish state
	private bool endgameFlag;						//private flag to run the finish sequence just once
	public static bool isWon;						//have we won the game?
	public static string whosTurn;					//current turn holder in string. useful if you want to extend the kit.
	public static int allowedShootsInRound = 5;		//shoots available to player in each turn
	public static int playerShoots;					//total shoots performed by player
	public static int enemyShoots;					//total shoots performed by AI
	public static int score;						//player score
	public static int scoreRatio;					//player score multiplier for streaks
	public static int playerCorrectHits;			//total successful shoots of the player
	public static int enemyCorrectHits;				//total successful shoots of the AI
	public static float missileTravelTime = 2.0f;	//missile speed
	// Static variables //

	public GameObject[] playerShips; 				//prefabs of player ships
	public GameObject[] enemyShips; 				//prefabs of player ships

	public static List<GameObject> playerShipsInScene;	//actual player ships object inside the scene
	public static List<GameObject> enemyShipsInScene;		//actual enemy ships object inside the scene

	// we need to have two arrays to determine the occupied tiles by player and enemy ships //
	public static List<int> playerTiles;
	public static List<int> enemyTiles;

	public GameObject mapTile;					//basic maptile prefab
	public static List<GameObject> mapTiles;	//array of all maptile objects inside the scene

	//we will create all the tiles in realtime. So we need a position to begin instantiating them.
	private Vector3 startingTilePosition = new Vector3(-3.22f, -2.55f, 0); //Very Important
	private float mapTileSpaceX = 0.12f; //Very Important
	private float mapTileSpaceY = 0.22f; //Very Important

	private bool canTap = true;

	// reference to game objects //
	public GameObject playerInput;		//player input object (small sphere)
	public GameObject btnStart;
	public GameObject btnShuffle;
	// delete
	//public GameObject fader;			//fader plane
	public GameObject turnManager;		//trun manager plane
	public GameObject playerScore;
	public GameObject finishPlane;		
	public GameObject finishStatusLabel;
	public GameObject gameScoreLabel;
	public GameObject bestScoreLabel;

	public GameObject missile;			//missile prefab
	public GameObject[] missiles;		//reference to 5 missile object on the panel
	public GameObject[] missileIcons;	//reference to 5 missile icon object on the panel
	public Material[] missileStatus;	//on/off material => [0] = off / [1] = on
	// reference to game objects //


	/// <summary>
	/// Awake this instance.
	/// </summary>
	void Awake () {

		//reset/init all variables
		round = 1;
		gameIsStarted = false;
		gameIsFinished = false;
		endgameFlag = false;
		canTap = true;
		isWon = false;
		score = 0;
		scoreRatio = 0;
		playerCorrectHits = 0;
		enemyCorrectHits = 0;

		playerShoots = 0;
		enemyShoots = 0;

		mapTiles = new List<GameObject>();	//clear the array
		playerTiles = new List<int>();		//clear the array
		enemyTiles = new List<int>();		//clear the array

		//so we can shuffle the formation before starting the game
		arePlayerShipsVisible = true;

		//hide finish plane
		finishPlane.SetActive(false);

		//we need to track all ships inside the scene in separate arrays
		playerShipsInScene = new List<GameObject>();
		enemyShipsInScene = new List<GameObject>();

		//create the 8 x 8 maps in the game
		createMapTiles();
		
		setupMissiles();

		StartCoroutine(setPlayerShips());
		StartCoroutine(setEnemyShips());
	}


	/// <summary>
	/// Start this instance.
	/// </summary>
	void Start () {
		//call fader
		// delete
		//StartCoroutine(fader.GetComponent<Fader>().fadeToWhite());
	}


	/// <summary>
	/// Creates the map tiles.
	/// </summary>
	void createMapTiles() {
		for(int i = 0; i < tileY; i++) {
			for(int j = 0; j < tileX; j++) {
				GameObject mTile = Instantiate(mapTile, 
				                               startingTilePosition + new Vector3((j * (mapTile.transform.localScale.x + mapTileSpaceX)), (i * (mapTile.transform.localScale.y + mapTileSpaceY)), -0.2f),
				                               Quaternion.Euler(0, 180, 0)) as GameObject;
				mTile.name = "mapTile-" + ((i * tileX) + (j+1)).ToString();
				mTile.GetComponent<MapTileController>().tileID = (i * tileX) + (j+1);
				mapTiles.Add(mTile);
				//yield return new WaitForSeconds(0.005f);
			}
		}

		//debug result
		print ("Total maptiles: " + mapTiles.Count);
	}


	/// <summary>
	/// unhide the missiles and set the missile icons to full.
	/// </summary>
	void setupMissiles() {
		for(int i = 0; i < 5; i++) {
			missiles[i].GetComponent<Renderer>().enabled = true;
			missileIcons[i].GetComponent<Renderer>().material = missileStatus[1];
		}
	}


	/// <summary>
	/// Sets the player ships.
	/// </summary>
	IEnumerator setPlayerShips() {

		//clear the arrays
		playerTiles = new List<int>();
		playerShipsInScene = new List<GameObject>();

		//get a random formation
		int playerFormation = Random.Range(0, FormationPresets.formations);

		//set the ships on their positions
		for(int i = 0; i < 5; i++) {

			//get the target tile ID for this ship from the selected formation
			Vector3 formation = FormationPresets.getNewFormation(playerFormation, i);

			//convert the tile ID to actual vector3 position
			Vector3 position = convertTileIdToPosition( (int)formation.x , new Vector3(0, 0, 0.1f) );

			//create the ship on the result position
			GameObject playerShip = Instantiate(playerShips[i], 
			                                    position, 
			                                    Quaternion.Euler(0, 0, 0)) as GameObject;
			//rename the ship object
			playerShip.name = "PlayerShip-" + (i+1).ToString();

			//init the ship
			playerShip.GetComponent<ShipController>().anchorTile = (int)formation.x;	//get the anchor tile ID (1 ~ 64)

			//add it to the playerShipsInScene array
			playerShipsInScene.Add(playerShip);

			//save its position on the map tiles
			for(int j = 0; j < playerShip.GetComponent<ShipController>().shipSize; j++) {
				playerTiles.Add( (int)formation.x + j );
			}

			yield return new WaitForSeconds(0.005f);
		}

		//sort the occupied tiles array
		playerTiles.Sort();

		//debug
		//for(int k = 0; k < playerTiles.Count; k++) print ("player Ship Index " + k.ToString() + ": " +  playerTiles[k]);
	}


	/// <summary>
	/// Sets the AI ships.
	/// </summary>
	IEnumerator setEnemyShips() {

		//clear the arrays
		enemyTiles = new List<int>();
		enemyShipsInScene = new List<GameObject>();

		//get a random formation
		int enemyFormation = Random.Range(0, FormationPresets.formations);

		for(int i = 0; i < 5; i++) {
			
			Vector3 formation = FormationPresets.getNewFormation(enemyFormation, i);
			Vector3 position = convertTileIdToPosition( (int)formation.x , new Vector3(0, 0, 0.1f) );
			
			GameObject enemyShip = Instantiate(enemyShips[i], 
			                                   position, 
			                                   Quaternion.Euler(0, 0, 0)) as GameObject;
			//rename the ship object
			enemyShip.name = "EnemyShip-" + (i+1).ToString();

			//init the ship
			enemyShip.GetComponent<ShipController>().anchorTile = (int)formation.x;	//get the anchor tile ID (1 ~ 64)

			//add it to the enemyShipsInScene array
			enemyShipsInScene.Add(enemyShip);

			//hide this ship
			enemyShip.transform.GetChild(0).GetComponent<Renderer>().enabled = false;
			
			//save its position on the map tiles
			for(int j = 0; j < enemyShip.GetComponent<ShipController>().shipSize; j++) {
				enemyTiles.Add( (int)formation.x + j );
			}
			
			yield return new WaitForSeconds(0.005f);
		}
		
		//sort the occupied tiles array
		enemyTiles.Sort();
		
		//debug
		//for(int k = 0; k < enemyTiles.Count; k++) print ("Enemy Ship Index " + k.ToString() + ": " +  enemyTiles[k]);
	}


	/// <summary>
	/// Make the player ships visible.
	/// Will be used when its AI's turn.
	/// </summary>
	public void showPlayerShips(bool state) {

		arePlayerShipsVisible = state;
		for(int i = 0; i < playerShipsInScene.Count; i++)
			playerShipsInScene[i].transform.GetChild(0).GetComponent<Renderer>().enabled = state;

		print ("arePlayerShipsVisible: " + arePlayerShipsVisible);
	}


	/// <summary>
	/// Assign turns to player and AI.
	/// </summary>
	public IEnumerator roundTurnManager() {
		
		if(gameIsFinished)
			yield break;

		//call fader
		// delete
		//StartCoroutine(fader.GetComponent<Fader>().fade ());

		//refill missile stocks
		refillMissiles();
		
		//if round carry is odd, its players turn, otherwise its opponent's turn
		int carry;
		carry = round % 2;
		
		if(carry == 1) {
			playersTurn = true;
			opponentsTurn = false;
			whosTurn = "Player";

			yield return new WaitForSeconds(1.0f);
			//hide player ships when its player's turn
			showPlayerShips(false);
			//show player flags & hide AAI flags
			showFlags("Player", true);
			showFlags("AI", false);

		} else {
			playersTurn = false;
			opponentsTurn = true;
			whosTurn = "AI";

			yield return new WaitForSeconds(1.0f);
			//show all player ships when its AI's turn
			showPlayerShips(true);
			//hide player flags & show AI flags
			showFlags("Player", false);
			showFlags("AI", true);

		}

		//wait
		yield return new WaitForSeconds(0.7f);

		//call turn plane
		StartCoroutine(turnManager.GetComponent<TurnManager>().turn ());
	}


	/// <summary>
	/// Show/hide the flag objects based on the turns.
	/// </summary>
	public void showFlags(string owner, bool state) {

		GameObject[] flags = GameObject.FindGameObjectsWithTag(owner + "Flag");
		for(int i = 0; i < flags.Length; i++)
			flags[i].GetComponent<Renderer>().enabled = state;

	}


	/// <summary>
	/// FSM
	/// </summary>
	void Update () {

		if(canTap) {
			StartCoroutine(tapManager());
		}
	

		//debug restart
		if(Input.GetKeyUp(KeyCode.R))
			SceneManager.LoadScene(SceneManager.GetActiveScene().name);

		//debug show/hide player ships
		if(Input.GetKeyUp(KeyCode.S)) showPlayerShips(true);
		if(Input.GetKeyUp(KeyCode.H)) showPlayerShips(false);

		//set player score
		playerScore.GetComponent<TextMesh>().text = "Score: " + score.ToString();

		//check if the game is finished...
		if(gameIsFinished)
			gameover();

		//On screen informations
		printInfo();
	}


	//*****************************************************************************
	// This function monitors player touches on scene buttons
	//*****************************************************************************
	private RaycastHit hitInfo;
	private Ray ray;
	IEnumerator tapManager (){
		
		//Mouse of touch?
		if(	Input.touches.Length > 0 && Input.touches[0].phase == TouchPhase.Ended)  
			ray = Camera.main.ScreenPointToRay(Input.touches[0].position);
		else if(Input.GetMouseButtonUp(0))
			ray = Camera.main.ScreenPointToRay(Input.mousePosition);
		else
			yield break;
		
		if (Physics.Raycast(ray, out hitInfo)) {
			GameObject objectHit = hitInfo.transform.gameObject;
			switch(objectHit.name) {
				
			//status
			case "BtnStart":
				StartCoroutine(animateButton(objectHit));		//touch animation effect
				yield return new WaitForSeconds(0.25f);			//Wait for the animation to end
				gameIsStarted = true;
				btnShuffle.SetActive(false);					//we no longer need shuffle button
				btnStart.SetActive(false);						//we no longer need start button
				StartCoroutine(roundTurnManager());
				break;

			case "BtnShuffle":
				StartCoroutine(animateButton(objectHit));		//touch animation effect
				yield return new WaitForSeconds(0.05f);			//Wait for the animation to end

				//delete all player ships already inside the scene
				GameObject[] playerShips = GameObject.FindGameObjectsWithTag("PlayerShip");
				foreach(GameObject ship in playerShips)
					Destroy(ship);

				//fetch a new formation for player ships
				StartCoroutine(setPlayerShips());
				break;
			}	
		}
	}


	/// <summary>
	/// Show live game details with 3d text.
	/// </summary>
	public GameObject infoText;
	void printInfo() {

		int shootsRemained;
		if(playersTurn)
			shootsRemained = allowedShootsInRound - playerShoots;
		else
			shootsRemained = allowedShootsInRound - enemyShoots;

		infoText.GetComponent<TextMesh>().text = "Who's Turn? : " + whosTurn + 
			"\n" + "Shoots remained: " + shootsRemained +
			"\n" + "Round: " + round;
	}


	//*****************************************************************************
	// This function animates a button by modifying it's scales on x-y plane.
	// can be used on any element to simulate the tap effect.
	//*****************************************************************************
	IEnumerator animateButton ( GameObject _btn  ){
		canTap = false;
		float buttonAnimationSpeed = 7.0f;
		Vector3 startingScale = _btn.transform.localScale;	//initial scale	
		Vector3 destinationScale = startingScale * 1.1f;		//target scale
		
		//Scale up
		float t = 0.0f; 
		while (t <= 1.0f) {
			t += Time.deltaTime * buttonAnimationSpeed;
			_btn.transform.localScale = new Vector3( Mathf.SmoothStep(startingScale.x, destinationScale.x, t),
			                                        Mathf.SmoothStep(startingScale.y, destinationScale.y, t),
			                                        _btn.transform.localScale.z);
			yield return 0;
		}
		
		//Scale down
		float r = 0.0f; 
		if(_btn.transform.localScale.x >= destinationScale.x) {
			while (r <= 1.0f) {
				r += Time.deltaTime * buttonAnimationSpeed;
				_btn.transform.localScale = new Vector3( Mathf.SmoothStep(destinationScale.x, startingScale.x, r),
				                                        Mathf.SmoothStep(destinationScale.y, startingScale.y, r),
				                                        _btn.transform.localScale.z);
				yield return 0;
			}
		}
		
		if(r >= 1)
			canTap = true;
	}


	/// <summary>
	/// Checks the state of the tile, if its free or occupied
	/// </summary>
	/// <returns><c>true</c>, if tile is free, <c>false</c> if tile is occupied.</returns>
	/// <param name="_tileID">_tile I (index from 1 to 64)</param>
	/// <param name="_queryBy">_query by.</param>
	public static bool checkTileState(int _tileID, string _queryBy) {

		bool isFree = true;
		int counter = 0;

		if(_queryBy == "Player") {
			for(int i = 0; i < enemyTiles.Count; i++) {
				if(enemyTiles[i] == _tileID) {
					print ("Tile #" + _tileID + " is occupied by an enemy ship.");
					return false;
				} else {
					counter++;
					//print ("Tile #" + _tileID + " is free of enemy ships. | Counter: " + counter.ToString());
				}
			}

			//if we searched the whole array but found no match, then this tile is free
			if(counter == 17) {
				isFree = true;
			}
		} 
		else if(_queryBy == "AI") {
			for(int i = 0; i < playerTiles.Count; i++) {
				if(playerTiles[i] == _tileID) {
					print ("Tile #" + _tileID + " is occupied by a player ship.");
					return false;
				} else {
					counter++;
					//print ("Tile #" + _tileID + " is free of player ships. | Counter: " + counter.ToString());
				}
			}
			
			//if we searched the whole array but found no match, then this tile is free
			if(counter == 17) {
				isFree = true;
			}
		}

		return isFree;
	}


	/// <summary>
	/// Returns the ship object that is inside the target tile. 
	/// Might find nothing and return null.
	/// </summary>
	public static GameObject GetShipInTile(GameObject _targetTile, string _queryBy) {

		GameObject targetShip = null;
		int tID = _targetTile.GetComponent<MapTileController>().tileID;

		if(_queryBy == "Player") {
			for(int i = 0; i < enemyShipsInScene.Count; i++) {
				for(int j = 0; j < enemyShipsInScene[i].GetComponent<ShipController>().shipSize; j++) {
					if(enemyShipsInScene[i].GetComponent<ShipController>().usedTiles[j] == tID) {
						return enemyShipsInScene[i];
					}
				}
			}
		}
	
		return targetShip;
	}


	/// <summary>
	/// Updates the stats of each side after their shoots, and check if the game is finished or not
	/// </summary>
	public static void updateGameStatus(string _queryBy) {
		switch(_queryBy) {
		case "Player":
			playerCorrectHits++;
			if(playerCorrectHits >= 17) {
				isWon = true;
				gameIsFinished = true;
				print ("Congratulations! Game Won!");
			}
			break;

		case "AI":
			enemyCorrectHits++;
			if(enemyCorrectHits >= 17) {
				isWon = false;
				gameIsFinished = true;
				print ("You have lost. Game Over!!");
			}
			break;
		}
	}


	/// <summary>
	/// Shoots a missile towards the selected tile. It is just the missile animation and does not process the 
	/// actual hit event.
	/// </summary>
	/// <param name="_targetTile">_target tile.</param>
	/// <param name="_shootIndex">_shoot index.</param>
	public void shootMissile(GameObject _targetTile, int _shootIndex) {

		GameObject m = null;
		//create the misile prefab
		m = Instantiate (missile, missiles[_shootIndex - 1].transform.position, Quaternion.Euler(270, 90, 0)) as GameObject;
		m.name = "Missile";
		//set the new missile destination
		// delete
		//m.GetComponent<MissileMover>().destination = convertTileIdToPosition(_targetTile.GetComponent<MapTileController>().tileID , new Vector3(0, 0, 0));
		//offset the destination if required

		//hide the missile on the panel
		missiles[_shootIndex - 1].GetComponent<Renderer>().enabled = false;
		//set the missile icon to off
		missileIcons[_shootIndex - 1].GetComponent<Renderer>().material = missileStatus[0];
	}


	/// <summary>
	/// Refills the missiles.
	/// </summary>
	void refillMissiles() {
		for(int i = 0; i < 5; i++) {
			missiles[i].GetComponent<Renderer>().enabled = true;
			missileIcons[i].GetComponent<Renderer>().material = missileStatus[1];
		}
	}


	/// <summary>
	/// Finish the game
	/// </summary>
	void gameover() {

		if(endgameFlag)
			return;

		endgameFlag = true;

		//update status label
		if(isWon)
			finishStatusLabel.GetComponent<TextMesh>().text = "..::: You Won! :::..";
		else
			finishStatusLabel.GetComponent<TextMesh>().text = "..::: Try Again :::..";

		//show the finish plane
		finishPlane.SetActive(true);

		//set scores
		gameScoreLabel.GetComponent<TextMesh>().text = score.ToString();
		bestScoreLabel.GetComponent<TextMesh>().text = PlayerPrefs.GetInt("BestScore").ToString();

		//save best score
		int savedBestScore = PlayerPrefs.GetInt("BestScore");
		if(score > savedBestScore)
			PlayerPrefs.SetInt("BestScore", score);
	}


	/// <summary>
	/// Converts the tile identifier to position.
	/// </summary>
	public Vector3 convertTileIdToPosition(int tileID, Vector3 _offset) {
		return mapTiles[tileID - 1].transform.position + _offset;
	}
		
}
