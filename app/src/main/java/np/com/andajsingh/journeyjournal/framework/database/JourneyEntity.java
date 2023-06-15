package np.com.andajsingh.journeyjournal.framework.database;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "journey",
        foreignKeys = @ForeignKey(
                entity = UserEntity.class,
                parentColumns = "user_id",
                childColumns = "user_id",
                onDelete = CASCADE
        )
)
public class JourneyEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "journey_id")
    public long journeyId;

    @ColumnInfo(name = "user_id")
    public long userId;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "image_path")
    public String imagePath;
}
