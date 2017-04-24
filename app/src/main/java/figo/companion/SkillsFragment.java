package figo.companion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ArrowKeyMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SkillsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SkillsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkillsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SkillsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SkillsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SkillsFragment newInstance(String param1, String param2) {
        SkillsFragment fragment = new SkillsFragment();
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

        View view = inflater.inflate(R.layout.fragment_skills, container, false);

        ArrayList<EditText> arrayList = new ArrayList<EditText>();

        arrayList.add((EditText) view.findViewById(R.id.editText1Skill));
        arrayList.add((EditText) view.findViewById(R.id.editText2Skill));
        arrayList.add((EditText) view.findViewById(R.id.editText3Skill));
        arrayList.add((EditText) view.findViewById(R.id.editText4Skill));
        arrayList.add((EditText) view.findViewById(R.id.editText5Skill));

        loadData(view);
        applyTextWatcher(arrayList);

        // Inflate the layout for this fragment
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void applyTextWatcher(ArrayList<EditText> arrayList){
        for(EditText e : arrayList){
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
        MainActivity.myHero.addSkill(1, ((EditText) getView().findViewById(R.id.editText1Skill)).getText().toString());
        MainActivity.myHero.addSkill(2, ((EditText) getView().findViewById(R.id.editText2Skill)).getText().toString());
        MainActivity.myHero.addSkill(3, ((EditText) getView().findViewById(R.id.editText3Skill)).getText().toString());
        MainActivity.myHero.addSkill(4, ((EditText) getView().findViewById(R.id.editText4Skill)).getText().toString());
        MainActivity.myHero.addSkill(5, ((EditText) getView().findViewById(R.id.editText5Skill)).getText().toString());

        MainActivity.myHero.saveHero(MainActivity.myHero);
    }

    private void loadData(View view){
        ((EditText) view.findViewById(R.id.editText1Skill)).setText(MainActivity.myHero.getSkill(1));
        ((EditText) view.findViewById(R.id.editText2Skill)).setText(MainActivity.myHero.getSkill(2));
        ((EditText) view.findViewById(R.id.editText3Skill)).setText(MainActivity.myHero.getSkill(3));
        ((EditText) view.findViewById(R.id.editText4Skill)).setText(MainActivity.myHero.getSkill(4));
        ((EditText) view.findViewById(R.id.editText5Skill)).setText(MainActivity.myHero.getSkill(5));
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
