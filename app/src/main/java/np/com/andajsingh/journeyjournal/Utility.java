package np.com.andajsingh.journeyjournal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

public class Utility {
    public static Bitmap scaleImageToTheCorrespondingView(String imagePath, View view) {
        Bitmap bitmap = null;
        try {
            if (imagePath.isEmpty()) {
                return bitmap;
            }
            // Get the dimensions of the View
            int targetW = view.getMeasuredWidth();
            int targetH = view.getMeasuredHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(imagePath, bmOptions);

            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return bitmap;
    }
}
