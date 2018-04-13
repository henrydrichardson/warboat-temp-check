using UnityEngine;
using System.Collections;

public class PlayerInput : MonoBehaviour {

	/// <summary>
	/// This class handles all player input interactions on the game map, including tap on tiles,
	/// shooting a missile, checking if the missile hit a ship, setting the scores and switching the turns
	/// after all shoots has been performed.
	/// </summary>

	// player input settings //
	[Range(0, 0.5f)]
	public float followSpeedDelay = 0.1f;		//we want the base to follow player's input with a small delay.
	//delay of 0 leads to immediate follow and delay of 0.5 leads to a lazy follow

	private float sideLimit = 3.5f;				//movement limiters for player input object
	private float topLimit = 5.0f;				//...
	private float bottomLimit = -3.0f;			//...
	private float xVelocity = 0.0f;
	private float yVelocity = 0.0f;
	private Vector3 screenToWorldVector;
	// player input settings //

	// static //
	public static bool isShooting;				//is there a missile in the scene?
	// static //


	private GameObject gc;						//reference to game controller
	public GameObject tapEffect;				//reference to prefab


	void Awake () {
		isShooting = false;
		gc = GameObject.FindGameObjectWithTag("GameController");
	}


	void Update () {

		if(GameController.gameIsFinished)
			return;

		//show player current inout position with a small sphere and two tall lines!
		renderPlayerInputPosition();

		//receive player touch/inputs
		touchManager();
	}



	//*****************************************************************************
	// This function monitors player touches
	//*****************************************************************************
	private RaycastHit hitInfo;
	private Ray ray;
	void touchManager (){
		
		//Mouse of touch?
		if(	Input.touches.Length > 0 && Input.touches[0].phase == TouchPhase.Ended)  
			ray = Camera.main.ScreenPointToRay(Input.touches[0].position);
		else if(Input.GetMouseButtonUp(0))
			ray = Camera.main.ScreenPointToRay(Input.mousePosition);
		else
			return;
		
		if (Physics.Raycast(ray, out hitInfo)) {
			GameObject objectHit = hitInfo.transform.gameObject;
			switch(objectHit.tag) {
			
			//we are only allowed to touch on game tiles!
			case "MapTile":
				StartCoroutine(manageShoots(objectHit));
				break;				
			}				
		}
	}


	/// <summary>
	/// We are using a small sphere object which follows player input position on the 2d screen space (x-y plane)
	/// </summary>
	private float touchX = 0;
	private float touchY = 0;
	void renderPlayerInputPosition () {

		if (Input.touchCount > 0 || Input.GetMouseButton(0) /*|| Application.isEditor*/) {
			screenToWorldVector = Camera.main.ScreenToWorldPoint(new Vector3 (Input.mousePosition.x, Input.mousePosition.y, 20));
			touchX = Mathf.SmoothDamp(transform.position.x, screenToWorldVector.x, ref xVelocity, followSpeedDelay);
			touchY = Mathf.SmoothDamp(transform.position.y, screenToWorldVector.y, ref yVelocity, followSpeedDelay);
			
			//movement limiters
			if(touchX > sideLimit)
				touchX = sideLimit;
			if(touchX < -sideLimit)
				touchX = -sideLimit;
			if(touchY > topLimit)
				touchY = topLimit;
			if(touchY < bottomLimit)
				touchY = bottomLimit;
			
			transform.position = new Vector3(touchX, touchY, -0.1f);
		}
	}


	/// <summary>
	/// Shoots a missile towards the current player input/touch position (selected tile)
	/// </summary>
	/// <param name="targetTile">Target tile to shoot</param>
	IEnumerator manageShoots(GameObject targetTile) {

		//print ("Tile Name: " + targetTile.name);

		//if this is not our turn, exit immediately.
		if(!GameController.gameIsStarted || !GameController.playersTurn || isShooting)
			yield break;

		//if we hit this tile before, we can not shoot it again
		if(targetTile.GetComponent<MapTileController>().isHitByPlayer)
			yield break;

		//set the state
		isShooting = true;

		//increase the shoot counter
		GameController.playerShoots++;

		//create a tap effect
		Instantiate(tapEffect, targetTile.transform.position, Quaternion.Euler(0, 180, 0));

		//tell the game controller to shoot a missile
		gc.GetComponent<GameController>().shootMissile(targetTile, GameController.playerShoots);
		yield return new WaitForSeconds(GameController.missileTravelTime); //very important!

		//check if we hit a ship?
		bool successfulHit = targetTile.GetComponent<MapTileController>().receiveHit("Player");
		
		if(successfulHit) {
			GameController.scoreRatio++;
			GameController.score += 50 * GameController.scoreRatio;
			print ("Player Shoot happened: " + GameController.playerShoots + " And hit a ship.");
			GameController.updateGameStatus("Player");
			GameController.GetShipInTile(targetTile, "Player").GetComponent<ShipController>().shipHealth--;
		} else {
			GameController.scoreRatio = 0;
			print ("Player Shoot happened: " + GameController.playerShoots + " And was a miss.");
		}
		
		//if this is our last shoot, switch the turn
		if(GameController.playerShoots >= GameController.allowedShootsInRound) {
			GameController.round++;
			GameController.playerShoots = 0;

			//wait
			yield return new WaitForSeconds(0.5f);
			isShooting = false;

			//switch the turn
			StartCoroutine(gc.GetComponent<GameController>().roundTurnManager());
			yield break;
		}

		//reactiveate the shoot sequence
		yield return new WaitForSeconds(0.1f);
		isShooting = false;
	}
		

}

