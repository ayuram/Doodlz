// ColorDialogFragment.java
// Allows user to set the drawing color on the DoodleView
package com.example.doodlz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ColorDialogFragment extends DialogFragment {
   private SeekBar alphaSeekBar;
   private SeekBar redSeekBar;
   private SeekBar greenSeekBar;
   private SeekBar blueSeekBar;
   private View colorView;
   protected int color;

   @Override
   public Dialog onCreateDialog(Bundle bundle) {
      // create dialog
      AlertDialog.Builder builder =
         new AlertDialog.Builder(getActivity());
      View colorDialogView = getActivity().getLayoutInflater().inflate(
         R.layout.fragment_color, null);
      builder.setView(colorDialogView);

      builder.setTitle(getTitle());

      alphaSeekBar = (SeekBar) colorDialogView.findViewById(
         R.id.alphaSeekBar);
      redSeekBar = (SeekBar) colorDialogView.findViewById(
         R.id.redSeekBar);
      greenSeekBar = (SeekBar) colorDialogView.findViewById(
         R.id.greenSeekBar);
      blueSeekBar = (SeekBar) colorDialogView.findViewById(
         R.id.blueSeekBar);
      colorView = colorDialogView.findViewById(R.id.colorView);
      alphaSeekBar.setOnSeekBarChangeListener(colorChangedListener);
      redSeekBar.setOnSeekBarChangeListener(colorChangedListener);
      greenSeekBar.setOnSeekBarChangeListener(colorChangedListener);
      blueSeekBar.setOnSeekBarChangeListener(colorChangedListener);
      final DoodleView doodleView = getDoodleFragment().getDoodleView();
      color = getColorDoodleView(doodleView);
      alphaSeekBar.setProgress(Color.alpha(color));
      redSeekBar.setProgress(Color.red(color));
      greenSeekBar.setProgress(Color.green(color));
      blueSeekBar.setProgress(Color.blue(color));
      builder.setPositiveButton(R.string.button_set_color,
         new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               setColorDoodleView(doodleView);
            }
         }
      );

      return builder.create(); // return dialog
   }

   protected int getColorDoodleView(DoodleView doodleView) {
      return doodleView.getDrawingColor();
   }

   protected void setColorDoodleView(DoodleView doodleView) {
      doodleView.setDrawingColor(color);
   }

   protected int getTitle() {
      return R.string.title_color_dialog;
   }

   private MainActivityFragment getDoodleFragment() {
      return (MainActivityFragment) getFragmentManager().findFragmentById(
         R.id.doodleFragment);
   }

   @Override
   public void onAttach(Activity activity) {
      super.onAttach(activity);
      MainActivityFragment fragment = getDoodleFragment();

      if (fragment != null)
         fragment.setDialogOnScreen(true);
   }

   @Override
   public void onDetach() {
      super.onDetach();
      MainActivityFragment fragment = getDoodleFragment();

      if (fragment != null)
         fragment.setDialogOnScreen(false);
   }

   private final OnSeekBarChangeListener colorChangedListener =
      new OnSeekBarChangeListener() {
         // display the updated color
         @Override
         public void onProgressChanged(SeekBar seekBar, int progress,
            boolean fromUser) {

            if (fromUser)
               color = Color.argb(alphaSeekBar.getProgress(),
                  redSeekBar.getProgress(), greenSeekBar.getProgress(),
                  blueSeekBar.getProgress());
            colorView.setBackgroundColor(color);
         }

         @Override
         public void onStartTrackingTouch(SeekBar seekBar) {}

         @Override
         public void onStopTrackingTouch(SeekBar seekBar) {}
      };
}
