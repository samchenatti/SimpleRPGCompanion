package figo.companion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Hashtable;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatsFragment newInstance(String param1, String param2) {
        StatsFragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private TextWatcher textWatcher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void applyTextWatcher(ArrayList<EditText> arrayList){

        class MyTextWatcher implements TextWatcher{
            private EditText editText;
            private Integer  previousValue;

            MyTextWatcher(EditText e){
                this.editText = e;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String content = editText.getText().toString();

                if(!content.equals(""))
                    previousValue = Integer.parseInt(editText.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String att = "";
                Integer value = 0;

                if(editText.toString().contains("editTextSTR"))
                    att = "STR";
                else if(editText.toString().contains("editTextDEX"))
                    att = "DEX";
                else if(editText.toString().contains("editTextCON"))
                    att = "CON";
                else if(editText.toString().contains("editTextINT"))
                    att = "INT";
                else if(editText.toString().contains("editTextWIS"))
                    att = "WIS";
                else if(editText.toString().contains("editTextCHA"))
                    att = "CHA";

                if (editText.getText().toString().equals(""))
                    value = 0;
                else
                    value = Integer.parseInt(editText.getText().toString());

                if(value < 0 || value > 25) {
                    toastText(value.toString() + " esta fora dos limites :(");
                    editText.setText(Integer.toString(previousValue));
                }else{
                    attributeModifier(att, value);

                    MainActivity.myHero.setStat(att, value);

                    MainActivity.myHero.saveHero(MainActivity.myHero);
                }
            }
        }

        for(EditText e : arrayList){
            e.addTextChangedListener(new MyTextWatcher(e));
        }
    }

    private void toastText(String s){
        Toast.makeText(this.getContext(), s, Toast.LENGTH_LONG).show();
    }

    private void attributeModifier(String att, Integer value){
        Hashtable<Integer, Integer> hash = new Hashtable<Integer, Integer>();
        Integer modifier;
        TextView textView = null;

        hash.put(0, +0);
        hash.put(1, -5);
        hash.put(2, -4);
        hash.put(3, -4);
        hash.put(4, -3);
        hash.put(5, -3);
        hash.put(6, -2);
        hash.put(7, -2);
        hash.put(8, -1);
        hash.put(9, -1);
        hash.put(10, 0);
        hash.put(11, 0);
        hash.put(12, +1);
        hash.put(13, +1);
        hash.put(14, +2);
        hash.put(15, +2);
        hash.put(16, +3);
        hash.put(17, +3);
        hash.put(18, +4);
        hash.put(19, +4);
        hash.put(20, +5);
        hash.put(21, +6);
        hash.put(22, +7);
        hash.put(23, +8);
        hash.put(24, +9);
        hash.put(25, +10);

        if (att.equals("STR"))
            textView = (TextView) getView().findViewById(R.id.textViewStrMod);
        else if (att.equals("DEX"))
            textView = (TextView) getView().findViewById(R.id.textViewDexMod);
        else if (att.equals("CON"))
            textView = (TextView) getView().findViewById(R.id.textViewConMod);
        else if (att.equals("INT"))
            textView = (TextView) getView().findViewById(R.id.textViewIntMod);
        else if (att.equals("WIS"))
            textView = (TextView) getView().findViewById(R.id.textViewWisMod);
        else if (att.equals("CHA"))
            textView = (TextView) getView().findViewById(R.id.textViewChaMod);


        String t = "";

        if(hash.get(value) >= 0 )
            t = "+" + hash.get(value).toString();
        else
            t = hash.get(value).toString();

        textView.setText(t);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        ArrayList<EditText> arrayList = new ArrayList<>();

        arrayList.add((EditText) view.findViewById(R.id.editTextSTR));
        arrayList.add((EditText) view.findViewById(R.id.editTextDEX));
        arrayList.add((EditText) view.findViewById(R.id.editTextCON));
        arrayList.add((EditText) view.findViewById(R.id.editTextINT));
        arrayList.add((EditText) view.findViewById(R.id.editTextWIS));
        arrayList.add((EditText) view.findViewById(R.id.editTextCHA));

        loadStatus(arrayList);
        applyTextWatcher(arrayList);


        // Inflate the layout for this fragment

        return view;
    }

    private void loadStatus(ArrayList<EditText> arrayList){
        String att = "";

        for(EditText e : arrayList){
            if(e.toString().contains("editTextSTR"))
                att = "STR";
            else if(e.toString().contains("editTextDEX"))
                att = "DEX";
            else if(e.toString().contains("editTextCON"))
                att = "CON";
            else if(e.toString().contains("editTextINT"))
                att = "INT";
            else if(e.toString().contains("editTextWIS"))
                att = "WIS";
            else if(e.toString().contains("editTextCHA"))
                att = "CHA";


            e.setText(String.valueOf(MainActivity.myHero.getState(att)));
        }
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
