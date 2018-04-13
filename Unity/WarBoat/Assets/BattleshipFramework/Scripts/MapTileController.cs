using UnityEngine;
using System.Collections;

public class MapTileController : MonoBehaviour {

	/// <summary>
	/// This controller saves the state of each individual tile object on the scene and presents them to
	/// other controller for further usage. It also handle the after-shoot events and set the correct flags 
	/// when a missile is hit/not-hit.
	/// </summary>

	public int tileID;							//index from 1 to 64

	public bool isHitByPlayer;					//flag if has been hit by player
	public bool isHitByAI;						//flag if has been hit by AI

	public static GameObject hoveredTile;		//the last tile that player has hovered
		
	public Material normalMat;					//normal material for tiles
	public Material hoveredMat;					//hovered (a little brighter) material for tiles

	private GameObject playerInputGo;			//small sphere object used to show player input position

	public GameObject correctHitFlag;			//reference to flag prefab
	public GameObject falseHitFlag;				//reference to flag prefab

	public GameObject explosionEffect;			//reference to fx prefab


	void Awake () {
		isHitByPlayer = false;
		isHitByAI = false;
	}


	void Start () {
		playerInputGo = GameObject.FindGameObjectWithTag("PlayerInput");
		hoveredTile = null;
	}


	/// <summary>
	/// FSM
	/// </summary>
	void Update () {

		checkDistanceToInput();
		//setNewColor();

		//print ("screenToWorldVector: " + screenToWorldVector);
	}


	/// <summary>
	/// Checks the distance of this tile with player input position.
	/// If the tile is near enough, it will be saved as the target tile.
	/// </summary>
	void checkDistanceToInput () {
		if(Vector3.Distance(transform.position, playerInputGo.transform.position) <= 0.4f) {
			hoveredTile = this.gameObject;
			//print ("Tile #" + hoveredTile.name + " is the closest tile to player input");
		}
	}


	/// <summary>
	/// Optional. We can set a new color/material/texture for hovered tile.
	/// </summary>
	void setNewColor() {

		if(!hoveredTile || isHitByPlayer || isHitByAI) {
			GetComponent<Renderer>().material = normalMat;
			return;
		}

		if(hoveredTile.name == gameObject.name)
			GetComponent<Renderer>().material = hoveredMat;
		else
			GetComponent<Renderer>().material = normalMat;
	}


	/// <summary>
	/// Receives the hit request by player/AI and set the required values afterward.
	/// </summary>
	/// <returns><c>true</c>, if hit was received, <c>false</c> otherwise.</returns>
	/// <param name="_shootby">_shootby.</param>
	public bool receiveHit(string _shootby) {

		//set the new state
		if(_shootby == "Player")
			isHitByPlayer = true;
		else
			isHitByAI = true;

		//define dummy object
		GameObject flag = null;

		//check if this tile is free or holds a ship 
		//(we do this just for the selected shooter, as the tile state differs for player and AI).
		bool isFree = GameController.checkTileState(tileID, _shootby);

		//if no opponent ship was placed in this tile
		if(isFree) {
			//create a (not hit) flag
			flag = Instantiate (falseHitFlag, transform.position, Quaternion.Euler(0, 180, 0)) as GameObject;
			flag.tag = _shootby + "Flag";
			flag.name = _shootby + "Flag (-)"; 
		} else {
			
			Instantiate(explosionEffect, transform.position, Quaternion.Euler(0, 180, 0));
	
			//create a (hit) flag
			flag = Instantiate (correctHitFlag, transform.position, Quaternion.Euler(0, 180, 0)) as GameObject;
			flag.tag = _shootby + "Flag";
			flag.name = _shootby + "Flag (X)";
		}

		return !isFree;
	}
		

}
