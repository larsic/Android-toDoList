package lars.be.simpletodolist;

import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity {

    private EditText item;
    private Button add;
    private ListView dynamicListView;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item = (EditText) findViewById(R.id.myEditText);
        add = (Button) findViewById(R.id.myAddButton);
        dynamicListView = (ListView) findViewById(R.id.myListView);
        list = new ArrayList<String>();
        //list.add("Android Atc");


        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list);
        dynamicListView.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = item.getText().toString();
                if(todoItem.length() > 0){

                    list.add(item.getText().toString());

                    adapter.notifyDataSetChanged();

                    item.setText("");

                }
            }
        });

        dynamicListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                list.remove(position);

                adapter.notifyDataSetChanged();

                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadList();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveList();

    }


    private void loadList(){
     SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        list.clear();
     list.addAll(preferences.getStringSet("list", new HashSet<String>()));

    }

    private void saveList(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Set<String> values = new HashSet<>();
        values.addAll(list);
        editor.putStringSet("list", values);
        editor.commit();
    }
}
