package android.com.shaunalberts.triplogger;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Used to create a scaled bitmap, that will display the taken photo.
 *
 * Shaun      09-October-2016          Initial
 */
public class PictureUtils {

    public static Bitmap getScaledBitMap(String path, int destWidth, int destHeight) {
        //Read in the dimensions of the image on disk
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        //figure out how much to scale down by
        int inSampleSize = 1;
        if (srcHeight > destHeight) {
            //if the width > height then scale down by height
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight/destHeight);
            } else {
                inSampleSize = Math.round(srcWidth/destWidth);
            }
        }
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        //Read in and create final bitmap
        return BitmapFactory.decodeFile(path, options);

    }

    //Guess how big the image is, cause when fragment stats up dont know how big imageView is
    public static Bitmap getScaledBitmap(String path, Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return getScaledBitMap(path, size.x, size.y);
    }
}
