
package project.udacity.com.moviesapp.Models;

import java.util.ArrayList;
import java.util.List;

public class VideosModel {

    private Integer id;
    private List<Video> results = new ArrayList<Video>();

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The results
     */
    public List<Video> getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(List<Video> results) {
        this.results = results;
    }

}
