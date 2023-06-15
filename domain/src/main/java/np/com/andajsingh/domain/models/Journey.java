package np.com.andajsingh.domain.models;

import java.io.Serializable;

/**
 * Created on 21/03/2022.
 */
public class Journey implements Serializable {
    private long id;
    private final String title;
    private final String description;
    private final String date;
    private final String imagePath;

    public Journey(
            long id,
            String title,
            String description,
            String date,
            String imagePath
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.imagePath = imagePath;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getImagePath() {
        return imagePath;
    }

}
