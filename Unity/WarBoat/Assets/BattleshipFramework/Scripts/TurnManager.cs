using UnityEngine;
using System.Collections;

public class TurnManager : MonoBehaviour {

	/// <summary>
	/// This is a simple class which is used to show/hide a plane that shows the turn of each side
	/// when the turn switches. This object will slide in/out from the top of the screen.
	/// </summary>

	public static bool isShowing;			//is visible
	private float moveSpeed = 0.5f;			//
	private float savedMoveSpeed;			//
	private float targetY = 5.0f;			//target position
	private Vector3 startPosition;			//starting position

	public Material[] status;				//two materials that show the turn of each side

	void Awake () {
		isShowing = false;
		savedMoveSpeed = moveSpeed;
		transform.position = new Vector3(0, 7, -2.5f);
		startPosition = transform.position;
	}

	void Update() {
		//Debug - Comment for the live game
		if(Input.GetKeyUp(KeyCode.T))
			StartCoroutine(turn());
	}

	/// <summary>
	/// slide the object in/out on each turn switch
	/// </summary>
	public IEnumerator turn () {

		if(isShowing)
			yield break;

		isShowing = true;

		//set the material
		if(GameController.playersTurn)
			GetComponent<Renderer>().material = status[0];
		else 
			GetComponent<Renderer>().material = status[1];

		//set initial state
		transform.position = new Vector3(0, 7, -2.5f);

		float t = 0;
		while(t < 1) {
			t += Time.deltaTime * moveSpeed;

			//move the object
			if(t <= 0.5f) {
				transform.position = new Vector3(startPosition.x,
				                                 Mathf.SmoothStep(startPosition.y, targetY, t * 2),
				                                 startPosition.z);
			} else if(t <= 0.6f && t > 0.5f) {
				moveSpeed = 0.1f;	//slow it a little
			} else {
				moveSpeed = savedMoveSpeed;
				transform.position = new Vector3(startPosition.x,
				                                 Mathf.SmoothStep(targetY, startPosition.y, (t - 0.6f) * 2.5f ),
												 startPosition.z);
			}

			if( t >= 1) {
				print("Completed.");
				isShowing = false;
				transform.position = new Vector3(0, 7, -2.5f);
			}
			yield return 0;
		}
	}
}