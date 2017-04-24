package figo.companion;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements BiographyFragment.OnFragmentInteractionListener,
        SkillsFragment.OnFragmentInteractionListener, StatsFragment.OnFragmentInteractionListener,
        InventoryFragment.OnFragmentInteractionListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private int PICK_IMAGE_REQUEST = 1;

    private final SkillsFragment       skillsFragment = new SkillsFragment().newInstance("", "");
    private final StatsFragment        statsFragment  = new StatsFragment().newInstance("", "");
    private final BiographyFragment biographyFragment = new BiographyFragment().newInstance("", "");
    private final InventoryFragment inventoryFragment = new InventoryFragment().newInstance("", "");

    public static HeroModel myHero = HeroModel.loadHero();

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager = (ViewPager) findViewById(R.id.container);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.icon_bio);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_skills);
        tabLayout.getTabAt(2).setIcon(R.drawable.icon_attributes);
        //tabLayout.getTabAt(3).setIcon(R.drawable.icon_inventory);

        loadImage();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void newAtt(View v){

    }

    @Override
    public void onFragmentInteraction(Uri uri){
    }

    public void rollAttributes(View view){
        Random random = new Random();
        ArrayList<Integer> rolls = new ArrayList<Integer>();
        String finalStats = "(";

        int min = 1;
        int max = 6;
        int sum  = 0;

        Integer minor = 30;

        for(int j = 0; j < 6; j++){
            rolls = new ArrayList<Integer>();

            for(int i = 0; i < 4; i++)
                rolls.add(random.nextInt((max - min) + 1) + min);

            minor = rolls.get(0);
            for(Integer i : rolls){
                if(i <= minor)
                    minor = i;
            }

            rolls.remove((Integer) minor);

            Log.v("asdadssdas", Integer.toString(rolls.size()));


            sum = 0;

            for(Integer i : rolls)
                sum += i;

            if(j == 5)
                finalStats += Integer.toString(sum) + ")";
            else
                finalStats += Integer.toString(sum) + ", ";
        }

        ((TextView) findViewById(R.id.textViewNewAtt)).setText(finalStats);

    }

    private void loadImage(){
        if (myHero.getImagePath() == null) return;

        Uri uri = Uri.fromFile(new File(myHero.getImagePath()));

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Log.d(TAG, String.valueOf(bitmap));

        if(bitmap == null) return;

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
    }

    public void selectImage(View view){
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            setImage(uri.getPath());
            myHero.setImagePath(getRealPathFromURI(this, uri));
            myHero.saveHero(myHero);
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void setImage(String imagePath){
        if (imagePath == null) return;

        Uri uri = Uri.fromFile(new File(imagePath));

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Log.d(TAG, String.valueOf(bitmap));

        if(bitmap == null) return;

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            Log.v("a", Integer.toString(position));

            Log.v("test", "inst");
           switch (position){
               case 0:
                   return biographyFragment;

               case 1:
                   return skillsFragment;
               case 2:
                   return statsFragment;
               case 3:
                   return inventoryFragment;
               default:
                   return biographyFragment;
           }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Biografia";
                case 1:
                    return "Habilidades";
                case 2:
                    return "Atributos";
                case 3:
                    return "Inventario";
            }
            return null;
        }
    }
}
