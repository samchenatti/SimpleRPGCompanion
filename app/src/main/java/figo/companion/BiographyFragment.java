package figo.companion;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BiographyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BiographyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BiographyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BiographyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BiographyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BiographyFragment newInstance(String param1, String param2) {
        BiographyFragment fragment = new BiographyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_biography, container, false);
        ArrayList<EditText> arrayList = new ArrayList<EditText>();

        arrayList.add((EditText) view.findViewById(R.id.editTextBiography));
        arrayList.add((EditText) view.findViewById(R.id.editTextName));

        loadData(view);
        applyTextWatcher(arrayList);

        ((MainActivity) getActivity()).setImage(MainActivity.myHero.getImagePath());

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    private void applyTextWatcher(ArrayList<EditText> arrayList){
        for(EditText e : arrayList){
            Log.v("edit", "applied");
            e.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    saveData();
                }
            });
        }
    }

    private void saveData(){
        Log.v("AIHEAIH", "H");
        MainActivity.myHero.setName(((EditText) getView().findViewById(R.id.editTextName)).getText().toString());
        MainActivity.myHero.setBiography(((EditText) getView().findViewById(R.id.editTextBiography)).getText().toString());

        MainActivity.myHero.saveHero(MainActivity.myHero);
    }

    private void loadData(View view){
        ((EditText) view.findViewById(R.id.editTextName)).setText(MainActivity.myHero.getName());
        ((EditText) view.findViewById(R.id.editTextBiography)).setText(MainActivity.myHero.getBiography());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
