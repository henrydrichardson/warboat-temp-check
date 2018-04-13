using UnityEngine;
using System.Collections;

public class Fader : MonoBehaviour {

	/// <summary>
	/// 
	/// </summary>

	public static bool isFading;		//fade state
	private float fadeSpeed = 0.50f;	//fade speed
	public Color startColor;			//we are fading from [startColor] to pure black and then to complete transparent


	void Awake () {
		isFading = false;
		transform.position = new Vector3(0, 0, 2);
		startColor = GetComponent<Renderer>().material.color;	//default is black
	}

	void Update() {
		//just for debug
		if(Input.GetKeyUp(KeyCode.F))
			StartCoroutine(fade ());
	}

	/// <summary>
	/// Complete fade cycle from transparent to black and then from black to transparent
	/// </summary>
	public IEnumerator fade () {

		if(isFading)
			yield break;

		isFading = true;

		//set initial state
		transform.position = new Vector3(0, 0, -2);
		GetComponent<Renderer>().material.color = new Color(startColor.r, startColor.g, startColor.b, 0);

		float t = 0;
		while(t < 1) {
			t += Time.deltaTime * fadeSpeed;

			//fade to black
			if(t <= 0.5f) {
				GetComponent<Renderer>().material.color = new Color(startColor.r, 
				                                                    startColor.g, 
				                                                    startColor.b, 
				                                                    Mathf.SmoothStep(startColor.a, 1, t * 2));
			} else {	//fade from black to transparent
				GetComponent<Renderer>().material.color = new Color(startColor.r, 
				                                                    startColor.g, 
				                                                    startColor.b, 
				                                                    Mathf.SmoothStep(1, startColor.a, (t - 0.5f) * 2 ));
			}

			if( t >= 1) {
				print("Fade Completed.");
				isFading = false;

				//reset color and position
				transform.position = new Vector3(0, 0, 2);
				GetComponent<Renderer>().material.color = new Color(startColor.r, startColor.g, startColor.b, 0);
			}
			yield return 0;
		}
	}

	/// <summary>
	/// Half fade, which starts with black and then fades to transparent.
	/// Is used for starting the game.
	/// </summary>
	public IEnumerator fadeToWhite () {
		
		if(isFading)
			yield break;
		
		isFading = true;
		
		//set initial state
		transform.position = new Vector3(0, 0, -2);
		GetComponent<Renderer>().material.color = new Color(startColor.r, startColor.g, startColor.b, 1);
		
		float t = 0;
		while(t < 1) {
			t += Time.deltaTime * fadeSpeed;
			
			if(t <= 1) {
				GetComponent<Renderer>().material.color = new Color(startColor.r, 
				                                                    startColor.g, 
				                                                    startColor.b, 
				                                                    Mathf.SmoothStep(1, 0, t));
			} 
			
			if( t >= 1) {
				print("Fade Completed.");
				isFading = false;
				transform.position = new Vector3(0, 0, 2);
				GetComponent<Renderer>().material.color = new Color(startColor.r, startColor.g, startColor.b, 0);
			}
			yield return 0;
		}
	}
}