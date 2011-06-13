package FacebookConnector;

import java.util.List;

/**
 * The interface provides the necessary methods for communicating with the EWA_SS11 FB wall
 * @author pl
 *
 */
public interface FacebookConnector {

	
	/**
	 * Get a list with all highscore results from the FB wall
	 * @return List of score elements
	 * @throws Exception An exception is thrown if the connection to FB failed
	 */
	public List<Score> getHighScoreList() throws Exception;
	
		
	/**
	 * Publish a highscore result to the FB wall
	 * @param score The score element to be published on the FB wall
	 * @return The rank of the score in the highscorelist on the FB wall
	 * @throws Exception An exception is thrown if the connection to FB failed
	 */
	public Integer publishHighScoreResult(Score score) throws Exception;
	
	/**
	 * Returns the access token of the FB connector
	 * @return Access token
	 */
	public String getAccessToken();
	
	
}
