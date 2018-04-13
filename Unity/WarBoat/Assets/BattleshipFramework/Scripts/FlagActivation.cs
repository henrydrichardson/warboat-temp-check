using UnityEngine;
using System.Collections;

public class FlagActivation : MonoBehaviour {

	/// <summary>
	/// We are using separate flag objects to mark the game tiles with true or false hits.
	/// This way we can easily manage the correct/false hit of both Human Player and AI.
	/// </summary>

	public float activationDelay = 0;		//show the flag after this delay

	IEnumerator Start () {
		GetComponent<Renderer>().enabled = false;
		yield return new WaitForSeconds(activationDelay);
		GetComponent<Renderer>().enabled = true;
	}

}
