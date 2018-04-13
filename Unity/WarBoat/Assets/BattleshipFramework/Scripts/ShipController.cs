using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class ShipController : MonoBehaviour {

	/// <summary>
	/// Main ship controller.
	/// Controls ship size, health and occupied tiles.
	/// Ships are invisible at the start and will not be revealed until completely destroyed.
	/// When all tiles which a ship resides are hit, the ship gets completely destroyed.
	/// When its player's turn, all destroyed ships are always visible.
	/// </summary>

	[Range(2, 5)]
	public int shipSize = 2;		//we always set the size (occupied cells) of the ships via inspector

	// *** Not Editable *** //
	public int shipHealth;			//internal health
	public bool isDestroyed;		//internal status
	// *** Not Editable *** //

	public int anchorTile;			//the tile which this ship's pivot is on it (will be set by other controllers)
	public List<int> usedTiles;		//int array of all tile ids this ship has occupied

	void Awake () {
		shipHealth = shipSize;
		isDestroyed = false;
		usedTiles = new List<int>(shipSize);
	}

	void Start () {
		//save the used tiles of this ship
		for(int i = 0; i < shipSize; i++) {
			usedTiles.Add(anchorTile + i);
		}
	}


	void Update () {

		//a ship is destroyed, if all of its occupied tiles are getting shot
		if(shipHealth <= 0) {
			//set the ship as destroyed
			isDestroyed = true;
		}

		checkCondition();

		//debug - cheat : show the ships
		//showShips();

	}

	/// <summary>
	/// check if the ship is active or destroyed. Then show/hide it on the scene
	/// </summary>
	void checkCondition() {

		if(!isDestroyed)
			return;

		if(gameObject.tag == "EnemyShip" && GameController.playersTurn) {
			gameObject.transform.GetChild(0).GetComponent<Renderer>().enabled = true;
		} else if(gameObject.tag == "PlayerShip" && !GameController.playersTurn) {
			gameObject.transform.GetChild(0).GetComponent<Renderer>().enabled = true;
		} else {
			gameObject.transform.GetChild(0).GetComponent<Renderer>().enabled = false;
		}
	}


	/// <summary>
	/// Just For Debug. should not be used in final game
	/// </summary>
	void showShips() {

		if(gameObject.tag != "EnemyShip")
			return;

		if(Input.GetKey(KeyCode.Space) && gameObject.transform.GetChild(0).name == "EnemyShip-Body")
			gameObject.transform.GetChild(0).GetComponent<Renderer>().enabled = true;
		else
			gameObject.transform.GetChild(0).GetComponent<Renderer>().enabled = false;
	}

}
