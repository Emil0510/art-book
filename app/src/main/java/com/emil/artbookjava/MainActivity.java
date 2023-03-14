package com.emil.artbookjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.emil.artbookjava.databinding.ActivityArtBinding;
import com.emil.artbookjava.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Objects;

import javax.net.ssl.SSLSessionBindingEvent;

public class MainActivity extends AppCompatActivity {

    ArrayList<Art> artArrayList;
    ArtAdapter artAdapter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        artArrayList = new ArrayList<>();

        binding.recyclerVew.setLayoutManager(new LinearLayoutManager(this));
        artAdapter=new ArtAdapter(artArrayList);
        binding.recyclerVew.setAdapter(artAdapter);

        getData();
    }

    private void getData(){
        try{

            SQLiteDatabase database = this.openOrCreateDatabase("arts",MODE_PRIVATE,null);
            Cursor cursor = database.rawQuery("SELECT * FROM arts",null);
            int nameIx = cursor.getColumnIndex("artname");
            int idIx = cursor.getColumnIndex("id");

            while (cursor.moveToNext()){
                String name = cursor.getString(nameIx);
                int id = cursor.getInt(idIx);
                Art art = new Art(name,id);
                artArrayList.add(art);
            }

            artAdapter.notifyDataSetChanged();
            cursor.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.art_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add_art){
            Intent intent = new Intent(this,ArtActivity.class);
            intent.putExtra("info","new");
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
}