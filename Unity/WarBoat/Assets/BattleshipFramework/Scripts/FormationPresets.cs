using UnityEngine;
using System.Collections;

public class FormationPresets : MonoBehaviour {

	/// <summary>
	/// In the initial version of this battleship game framework, we are using a simple formation 
	/// system for ship placement which is fetching a random formation from the pool of available formations 
	/// designed for the game. You are free to add unlimited number of formations. Player can use the shuffle 
	/// button before starting the game to select a good formation for his ships.
	/// 
	/// In the next version, we will add the options of manually moving and rotating the ships on the map. 
	/// 
	/// Help:
	/// We have 5 ships, each with different size and index
	/// ship index || ship size
	/// 	0			5
	/// 	1			4
	/// 	2			3
	/// 	3			3
	/// 	4			2
	/// </summary>

	public static int formations = 4;		//total number of available formations

	/// <summary>
	/// This function receive a formation ID and the returns five vector3 as the position of 5 player/AI ships.
	/// Please note that we are only using the x value of the given vectors3. the X value is holding the tile ID 
	/// the selected ship is supposed to be on it. It is not referring to a position on the map!
	///  
	/// For example: Vector3(58, 0, 0) is referring to tile ID 58. We then convert this tile ID to actual position
	/// on the scene.
	/// 
	/// !! Important: indexes start from 1 to 64 
	/// 
	/// </summary>
	/// <returns>The new formation.</returns>
	/// <param name="_formationID">formation ID</param>
	/// <param name="_UnitIndex">unit index</param>
	public static Vector3 getNewFormation(int _formationID ,   int _UnitIndex) {

		Vector3 output = Vector3.zero;
		switch(_formationID) {

		case 0:
			if(_UnitIndex == 0) output = new Vector3(58, 0, 0);
			if(_UnitIndex == 1) output = new Vector3(43, 0, 0);
			if(_UnitIndex == 2) output = new Vector3(29, 0, 0);
			if(_UnitIndex == 3) output = new Vector3(17, 0, 0);
			if(_UnitIndex == 4) output = new Vector3(6, 0, 0);
			break;

		case 1:
			if(_UnitIndex == 0) output = new Vector3(60, 0, 0);
			if(_UnitIndex == 1) output = new Vector3(49, 0, 0);
			if(_UnitIndex == 2) output = new Vector3(45, 0, 0);
			if(_UnitIndex == 3) output = new Vector3(37, 0, 0);
			if(_UnitIndex == 4) output = new Vector3(26, 0, 0);
			break;

		case 2:
			if(_UnitIndex == 0) output = new Vector3(1, 0, 0);
			if(_UnitIndex == 1) output = new Vector3(11, 0, 0);
			if(_UnitIndex == 2) output = new Vector3(19, 0, 0);
			if(_UnitIndex == 3) output = new Vector3(37, 0, 0);
			if(_UnitIndex == 4) output = new Vector3(57, 0, 0);
			break;

		case 3:
			if(_UnitIndex == 0) output = new Vector3(10, 0, 0);
			if(_UnitIndex == 1) output = new Vector3(27, 0, 0);
			if(_UnitIndex == 2) output = new Vector3(37, 0, 0);
			if(_UnitIndex == 3) output = new Vector3(44, 0, 0);
			if(_UnitIndex == 4) output = new Vector3(51, 0, 0);
			break;

		}
	
		return output;
	}
}
