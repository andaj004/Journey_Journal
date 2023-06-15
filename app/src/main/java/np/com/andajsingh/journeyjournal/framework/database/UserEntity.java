package np.com.andajsingh.journeyjournal.framework.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created on 23/03/2022.
 */
@Entity(tableName = "user")
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    public long userId;

    @ColumnInfo(name = "email_address")
    public String emailAddress;

    @ColumnInfo(name = "full_name")
    public String fullName;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "password")
    public String password;
}
