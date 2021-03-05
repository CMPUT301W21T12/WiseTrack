package com.faanggang.wisetrack;

import android.content.Context;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class PublishExperiment extends DialogFragment {

    private EditText inputName;
    private EditText inputDescription;
    private EditText inputRegion;
    private EditText inputMinTrials;
    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onPublish(Experiment newExperiment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.add_experiment_fragment, null);

        inputYear = view.findViewById(R.id.add_year);
        inputMonth = view.findViewById(R.id.add_month);
        inputDay = view.findViewById(R.id.add_day);
        experimentDescription = view.findViewById(R.id.add_description);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Add Experiment")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String description = experimentDescription.getText().toString();

                        Calendar date = Calendar.getInstance();

                        int year, month, day;

                        try {
                            year = Integer.parseInt(inputYear.getText().toString());
                            month = Integer.parseInt(inputMonth.getText().toString());
                            day = Integer.parseInt(inputDay.getText().toString());

                            date.set(year, month, day);

                        } catch (Exception e){

                            builder.setMessage("Date error: failed to create Experiment object");

                        } finally {
                            listener.onOkPressed(new Experiment(date, description));
                        }


                    }}).create();

    }
}
