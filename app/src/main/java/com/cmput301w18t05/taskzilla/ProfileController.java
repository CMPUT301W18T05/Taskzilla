import android.view.View;

import com.cmput301w18t05.taskzilla.User;

/**
 * Created by wyatt on 09/03/18.
 */

public class ProfileController {

    private User user;

    public ProfileController(User user) {
        this.user = user;
    }

    public void updateProviderRating(Float rating) {
        user.setProviderRating(rating);
    }

    public void updateRequesterRating(Float rating) {
        user.setRequesterRating(rating);
    }

}
