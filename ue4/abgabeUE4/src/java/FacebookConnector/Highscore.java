/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FacebookConnector;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;
import java.util.List;

/**
 *
 * @author babz
 */
public class Highscore implements FacebookConnector {

   FacebookClient facebookClient = new DefaultFacebookClient(getAccessToken());


    @Override
    public String getAccessToken() {
        return "https://graph.facebook.com/oauth/access_token?client_id=182396081803634"
                + "&client_secret=b78130a706117c79d717dd0baa936e0a&grant_type=client_credentials";
    }

    @Override
    public List<Score> getHighScoreList() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Integer publishHighScoreResult(Score score) {
        return score.getScoreResult();
    }
    
}
