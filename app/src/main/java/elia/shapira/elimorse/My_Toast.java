package elia.shapira.elimorse;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A utility class for displaying customized Toast messages with an icon and centered text.
 * It uses the {@code R.layout.my_toast} layout for the toast's visual appearance.
 */
public class My_Toast {

    /**
     * Shows a custom toast with a default welcome icon.
     *
     * @param context The application context.
     * @param message The text message to display.
     */
    public static void showToast(Context context, String message) {
        showToast(context, message, R.drawable.welcome_user);
    }

    /**
     * Shows a custom toast with a specified icon resource.
     *
     * @param context   The application context.
     * @param message   The text message to display.
     * @param iconResId The resource ID of the image to display as an icon. Use 0 for no icon.
     */
    public static void showToast(Context context, String message, int iconResId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.my_toast, null);

        ImageView imageView = layout.findViewById(R.id.ivIn);
        TextView textView = layout.findViewById(R.id.tvIn);

        if (iconResId != 0) {
            imageView.setImageResource(iconResId);
        } else {
            imageView.setVisibility(View.GONE);
        }
        textView.setText(message);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setView(layout);
        toast.show();
    }
}
