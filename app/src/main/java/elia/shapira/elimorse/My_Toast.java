package elia.shapira.elimorse;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class My_Toast {

    public static void showToast(Context context, String message) {
        showToast(context, message, R.drawable.welcome_user);
    }

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
