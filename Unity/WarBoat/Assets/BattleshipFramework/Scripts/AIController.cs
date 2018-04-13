using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class AIController : MonoBehaviour {

	/// <summary>
	/// Simple controller for AI which is responsible for remembering shooted/hitted tiles, target tile selection,
	/// missile shooting, turn switching and more...
	/// 
	/// AI counts tile indexs from 0 to 63, instead of 1 to 64 !!
	/// </summary>

	public List<int> shootedTiles;		//tiles that enemy AI has shot
	public List<int> hittedTiles;		//tiles with successful hit

	private bool canShoot;				//AI shoot status

	private GameObject gc;				//reference to game controller
	public GameObject tapEffect;		//reference to prefab


	void Awake () {

		//reset the arrays
		shootedTiles = new List<int>();
		hittedTiles = new List<int>();

		canShoot = true;
		gc = GameObject.FindGameObjectWithTag("GameController");
	}


	IEnumerator Start () {
		//wait a little before the first shoot
		yield return new WaitForSeconds(2.0f);
	}


	/// <summary>
	/// FSM
	/// </summary>
	void Update () {

		if(GameController.gameIsFinished)
			return;

		//shoot a missile whenever possible
		StartCoroutine(shootMissile());
	}


	/// <summary>
	/// Shoots a missile towards the selected map tile.
	/// </summary>
	IEnumerator shootMissile() {

		//if game is not started or this is not AI's turn
		if(!GameController.gameIsStarted || !GameController.opponentsTurn)
			yield break;

		//if AI is already shooting
		if(!canShoot)
			yield break;

		canShoot = false;

		//get a random (not hitted/shooted before) tile to shoot
		GameObject targetTile = getUniqueTargetTile();

		if(!targetTile) {
			//exit the loop and search for a new target
			canShoot = true;
			yield break;
		}

		//if this is the first AI shoot, wait a little longer 
		if(GameController.enemyShoots == 0)
			yield return new WaitForSeconds(1.85f);

		//normal thinking process
		yield return new WaitForSeconds(0.75f);

		//set the counters
		GameController.enemyShoots++;

		//create a tap effect
		Instantiate(tapEffect, targetTile.transform.position, Quaternion.Euler(0, 180, 0));

		//tell the game controller to shoot a missile
		gc.GetComponent<GameController>().shootMissile(targetTile, GameController.enemyShoots);
		yield return new WaitForSeconds(GameController.missileTravelTime); //Important!

		//shoot to target tile
		bool successfulHit = targetTile.GetComponent<MapTileController>().receiveHit("AI");

		if(successfulHit) {
			print ("AI Shoot happened: " + GameController.enemyShoots + " And hit a ship.");
			GameController.updateGameStatus("AI");
			hittedTiles.Add(targetTile.GetComponent<MapTileController>().tileID - 1);
		} else {
			print ("AI Shoot happened: " + GameController.enemyShoots + " And was a miss.");
		}

		yield return new WaitForSeconds(0.3f);
		canShoot = true;

		//if AI performed all the shoots
		if(GameController.enemyShoots >= GameController.allowedShootsInRound) {
			GameController.round++;
			GameController.enemyShoots = 0;
			StartCoroutine(gc.GetComponent<GameController>().roundTurnManager());
			yield break;
		}
	}

	/// <summary>
	/// Search for a unique (new) tile on the map and return the result.
	/// Please note that this is a very simple target selection routine and is not using any brain to
	/// shoot near a tile which has been shot before. 
	/// 
	/// We will update this function to be able to shoot cleverly and sink player ships with as few shoots as possible.
	/// 
	/// </summary>
	/// <returns>a unique tile : GameObject</returns>
	public GameObject getUniqueTargetTile() {

		GameObject target = null;
		int randomIndex = Random.Range(0, 64);
		bool beenHitBefore = false;

		//check if we have shot random index before
		for(int i = 0; i < shootedTiles.Count; i++) {
			if(shootedTiles[i] == randomIndex)
				beenHitBefore = true;
		}

		if(beenHitBefore == false) {
			//we can shoot this tile index
			target = GameController.mapTiles[randomIndex];
			//print ("AI Target tile to shoot is: " + target.name);
			shootedTiles.Add(randomIndex);
			return target;
		} else {
			//we need to find a new target tile
			//print ("Can not find a suitable target. Searching again.");
			return null;
		}

	}
}
