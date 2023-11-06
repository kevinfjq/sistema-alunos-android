package com.example.sistemalistview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemalistview.dao.AlunoDAO;
import com.example.sistemalistview.model.Aluno;
import com.example.sistemalistview.util.CustomAdapter;

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
                showDialog(aluno);
            }
        });
    }

    private void showDialog(Aluno aluno) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        LinearLayout layoutVisualize = dialog.findViewById(R.id.layoutVisualize);
        LinearLayout layoutEdit = dialog.findViewById(R.id.layoutEdit);
        LinearLayout layoutRemove = dialog.findViewById(R.id.layoutRemove);
        TextView nomeAluno = dialog.findViewById(R.id.txtStudentName);
        nomeAluno.setText(aluno.getNome());

        layoutVisualize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Aluno");
                builder.setMessage("Nome: " + aluno.getNome() + "\n\nCpf: " + aluno.getCpf() + "\n\nTelefone: " + aluno.getTelefone());
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        layoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Edit", Toast.LENGTH_SHORT).show();
            }
        });

        layoutRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Remover aluno");
                builder.setMessage("\nRemover " + aluno.getNome() + "?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlunoDAO dao = new AlunoDAO(MainActivity.this);
                        dao.delete(aluno);
                        loadList();
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
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
        if (item.getItemId() == R.id.itemOrdernarNome) {
            loadListNome();
        }
        if (item.getItemId() == R.id.itemOrdenarData) {
            loadList();
        }

        return super.onOptionsItemSelected(item);
    }


    public void loadList() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.obterTodos();
        CustomAdapter adapter = new CustomAdapter(this, alunos);
        lsvAlunos.setAdapter(adapter);
    }

    public void loadListNome(){
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.listarPorNome();
        CustomAdapter adapter =new CustomAdapter(this, alunos);
        lsvAlunos.setAdapter(adapter);
    }

}