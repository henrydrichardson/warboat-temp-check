/*
 * delete 

using UnityEngine;
using System.Collections;

public class MissileMover : MonoBehaviour {

	/// <summary>
	/// This class rotates and moves the missile towards the selected target tile.
	/// Target tile will be set by other controllers.
	/// </summary>

	internal Vector3 destination;		//will be set by other controllers
	private Vector3 startPoint;
	private float distanceToTarget;

	void Start () {

		//note that we are using destination Z value as the starting z for this missile
		startPoint = new Vector3(transform.position.x, transform.position.y, destination.z);

		StartCoroutine(moveToDestination());

		distanceToTarget = Vector3.Distance(transform.position, destination);
		//print("destination: " + destination);

		print("Distance: " + distanceToTarget);
	}


	/// <summary>
	/// Move the missile slowly towards the selected tile (target position : Vector3)
	/// </summary>
	/// <returns>The to destination.</returns>
	IEnumerator moveToDestination() {

		float t = 0;
		while(t < 1) {

			t += (Time.deltaTime / GameController.missileTravelTime);	//Important!

			transform.LookAt(destination, Vector3.forward);				//rotate towards the target

			//move towards the target tile
			transform.position = new Vector3(Mathf.SmoothStep(startPoint.x, destination.x, t),
			                                 Mathf.SmoothStep(startPoint.y, destination.y, t),
			                                 startPoint.z);

			//if we are close enough, decrease the scale to simulate the collision
			if(Vector3.Distance(transform.position, destination) <= 2.0f) {
				if(transform.localScale.magnitude > 1.3f)
					transform.localScale -= new Vector3(0.01f, 0, 0.01f);
			}

			//destroy after reaching the target
			if(t >= 1)
				Destroy(gameObject);

			yield return 0;
		}
	}
		

}
*/