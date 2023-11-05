package com.example.sistemalistview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sistemalistview.dao.AlunoDAO;
import com.example.sistemalistview.model.Aluno;
import com.example.sistemalistview.util.CustomAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lsvAlunos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lsvAlunos = findViewById(R.id.lsvAlunos);
        loadList();

        lsvAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Aluno aluno = (Aluno) adapterView.getAdapter().getItem(i);
                String id = String.valueOf(aluno.getId());
                Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemAdicionar) {
            Intent it = new Intent(this, Adicionar.class);
            startActivity(it);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadList() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.obterTodos();
        CustomAdapter adapter = new CustomAdapter(this, alunos);
        lsvAlunos.setAdapter(adapter);
    }
}