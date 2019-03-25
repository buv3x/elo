package by.bsu.orienteering.model;

/**
 * Created by alexey.memelov on 21-Dec-18.
 */
public class ParseResultsRequest {

    private String data;

    private String source;

    private String token;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
