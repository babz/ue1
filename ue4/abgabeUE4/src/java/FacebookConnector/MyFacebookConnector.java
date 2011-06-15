/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FacebookConnector;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultJsonMapper;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.Post;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MyFacebookConnector implements FacebookConnector {

    private static final Logger log = Logger.getLogger(MyFacebookConnector.class.getSimpleName());
    private FacebookClient facebookClient;
    private DefaultJsonMapper mapper = new DefaultJsonMapper();
    private String pageID = "182396081803634";

    public MyFacebookConnector() {
        facebookClient = new DefaultFacebookClient(getAccessToken());
    }

    @Override
    public String getAccessToken() {
        return "182396081803634|3h5qK2yRAsDfm5m_IB2qLSwONiA";
    }

    private List<Score> mapPosts(List<Post> posts) {
        List<Score> sList = new LinkedList<Score>();

        for (Post p : posts) {
            if (p.getMessage() != null) {
                String msg = p.getMessage();
                
                Pattern pat = Pattern.compile("^Points\\: (\\d+)\\; Name\\: ([a-zA-Z]+ *[a-zA-Z]*)");
                Matcher matcher = pat.matcher(msg);

                if (matcher.find()) {
                    sList.add(new Score(Integer.valueOf(matcher.group(1)), matcher.group(2)));
                    log.fine("points: " + matcher.group(1) + " name: " + matcher.group(2));
                } else {
                    log.info("no match found");
                }
            }
        }
//            if (p.getMessage() != null) {
//                Scanner sc = new Scanner(p.getMessage());
//                if (sc.next().equals("Points:")) {
//                    if (sc.hasNextInt()) {
//                        int points = sc.nextInt();
//                        if (sc.next().equals(";")) {
//                            if (sc.next().equals("Name:")) {
//                                if (sc.hasNext()) {
//                                    String name = sc.nextLine();
//                                    sList.add(new Score(points, name));
//                                } else {
//                                    log.severe("#### no next line");
//                                }
//                            } else {
//                                log.severe("#### no name:");
//                            }
//                        } else {
//                            log.severe("#### no ;");
//                        }
//                    } else {
//                        log.severe("#### no int");
//                    }
//                } else {
//                    log.severe("#### no points");
//                }
//            } else {
//                log.severe("#### msg null");
//            }
//        }
        return sList;
    }

    /**
     *
     * @return descending sorted list of all highscores
     */
    @Override
    public List<Score> getHighScoreList() throws Exception {
        Connection<Post> feed = facebookClient.fetchConnection(pageID + "/feed", Post.class);

        List<Score> sList = new LinkedList<Score>();
        sList = mapPosts(feed.getData());
//        while (feed.hasNext()) {
//            feed = facebookClient.fetchConnectionPage(feed.getNextPageUrl(), Post.class);
//            for (Score s : mapPosts(feed.getData())) {
//                sList.add(s);
//            }
//        }
        Collections.sort(sList);

        return sList;
    }

    @Override
    public Integer publishHighScoreResult(Score score) throws Exception {
        if (score.getScoreResult() > 0) {
            facebookClient.publish(pageID + "/feed", FacebookType.class, Parameter.with("message", score.getFacebookPublicationString()));
        }
        return null;
    }
}
