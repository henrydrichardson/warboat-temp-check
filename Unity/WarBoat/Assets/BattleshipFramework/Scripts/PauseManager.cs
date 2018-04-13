using UnityEngine;
using System.Collections;
using UnityEngine.SceneManagement;

public class PauseManager : MonoBehaviour {
		
	/// <summary>
	// This class manages pause and unpause states.
	// Please note that the pause scene is the best place to show your full screen ads.
	/// </summary>

	public static bool isPaused;
	private float savedTimeScale;
	public GameObject pausePlane;

	enum Page {
		PLAY, PAUSE
	}
	private Page currentPage = Page.PLAY;

	//*****************************************************************************
	// Init.
	//*****************************************************************************
	void Start (){		

		isPaused = false;	
		Time.timeScale = 1.0f;
		Time.fixedDeltaTime = 0.02f;
		pausePlane.SetActive(false); 
	}

	//*****************************************************************************
	// FSM
	//*****************************************************************************
	void Update (){

		//touch control
		touchManager();
		
		//optional pause in Editor & Windows (just for debug)
		if(Input.GetKeyDown(KeyCode.P) || Input.GetKeyUp(KeyCode.Escape)) {
			//PAUSE THE GAME
			switch (currentPage) {
	            case Page.PLAY: 
	            	PauseGame(); 
	            	break;
	            case Page.PAUSE: 
	            	UnPauseGame(); 
	            	break;
	            default: 
	            	currentPage = Page.PLAY;
	            	break;
	        }
		}
		
		//debug restart
		if(Input.GetKeyDown(KeyCode.R)) {
			SceneManager.LoadScene(SceneManager.GetActiveScene().name);
		}
	}

	//*****************************************************************************
	// This function monitors player touches on menu buttons.
	// detects both touch and clicks and can be used inside editor, handheld device and 
	// every other platforms at once.
	//*****************************************************************************
	void touchManager (){
		if(Input.GetMouseButtonUp(0)) {
			RaycastHit hitInfo;
			Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);
			if (Physics.Raycast(ray, out hitInfo)) {
				string objectHitName = hitInfo.transform.gameObject.name;
				switch(objectHitName) {
				case "BtnPause":
					switch (currentPage) {
			            case Page.PLAY: 
			            	PauseGame();
			            	break;
			            case Page.PAUSE: 
			            	UnPauseGame(); 
			            	break;
			            default: 
			            	currentPage = Page.PLAY;
			            	break;
			        }
					break;

				case "BtnResume":	
					switch (currentPage) {
			            case Page.PLAY: 
			            	PauseGame();
			            	break;
			            case Page.PAUSE: 
			            	UnPauseGame(); 
			            	break;
			            default: 
			            	currentPage = Page.PLAY;
			            	break;
			        }
					break;
					
				case "BtnRestart":
					UnPauseGame();
					SceneManager.LoadScene(SceneManager.GetActiveScene().name);
					break;
					
				case "BtnMenu":
					UnPauseGame();
					SceneManager.LoadScene("Menu");
					break;
				}
			}
		}
	}

	void PauseGame (){
		//print("Game is Paused...");
		isPaused = true;
	    Time.timeScale = 0;
		Time.fixedDeltaTime = 0;
	    pausePlane.SetActive(true);
	    currentPage = Page.PAUSE;
	}

	void UnPauseGame (){
		//print("Unpause");
	    isPaused = false;
		Time.timeScale = 1.0f;
		Time.fixedDeltaTime = 0.02f;
		pausePlane.SetActive(false);   
	    currentPage = Page.PLAY;
	}

}