/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FacebookConnector;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultJsonMapper;
import com.restfb.Facebook;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.Post;
import java.io.InputStream;
import java.util.AbstractList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author babz
 */
public class Highscore implements FacebookConnector {

    FacebookClient facebookClient;
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String pageID = "182396081803634";


    public Highscore() {
        facebookClient = new DefaultFacebookClient(getAccessToken());
    }

    @Override
    public String getAccessToken() {
//        FacebookClient.AccessToken token = new FacebookClient.AccessToken();
        //"https://graph.facebook.com/oauth/access_token?client_id=182396081803634&client_secret=b78130a706117c79d717dd0baa936e0a&grant_type=client_credentials";
        //return "182396081803634|3h5qK2yRAsDfm5m_IB2qLSwONiA";
        return "182396081803634|3h5qK2yRAsDfm5m_IB2qLSwONiA";
    }

    private List<Score> mapPosts(List<Post> posts) {
        List<Score> sList = new LinkedList<Score>();

        for (Post p : posts) {
            Scanner sc = new Scanner(p.getMessage());
            if (sc.next().equals("Points:")) {
                if (sc.hasNextInt()) {
                    int points = sc.nextInt();
                    if (sc.next().equals(";")) {
                        if (sc.next().equals("Name:")) {
                            if (sc.hasNext()) {
                                String name = sc.nextLine();
                                sList.add(new Score(points, name));
                                System.out.println(points + name);
                            }
                        }
                    }
                }
            }
        }
        return sList;
    }

    /**
     *
     * @return list of all highscores, TODO sorted by score
     */
    @Override
    public List<Score> getHighScoreList() throws Exception {
        Connection<Post> feed = facebookClient.fetchConnection(pageID + "/feed", Post.class);

        List<Score> sList = new LinkedList<Score>();
        sList = mapPosts(feed.getData());
        while(feed.hasNext()){
            feed = facebookClient.fetchConnectionPage(feed.getNextPageUrl(), Post.class);
            for(Score s : mapPosts(feed.getData())) {
                sList.add(s);
            }
        }

        return sList;
    }

    // TODO publish only, if score > 0
    @Override
    public Integer publishHighScoreResult(Score score) {
        facebookClient.publish(pageID + "/feed", FacebookType.class, Parameter.with("message", score.getFacebookPublicationString()));
        return null;
    }
}
