package com.example.sistemalistview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sistemalistview.dao.AlunoDAO;
import com.example.sistemalistview.model.Aluno;



public class Adicionar extends AppCompatActivity {

    EditText edtNome, edtCpf, edtTelefone;
    Button btnAdicionar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);

        Toolbar toolbar = findViewById(R.id.toolbarAdicionar);
        setSupportActionBar(toolbar);

        edtNome = findViewById(R.id.edtNome);
        edtCpf = findViewById(R.id.edtCpf);
        edtTelefone = findViewById(R.id.edtTelefone);
        btnAdicionar = findViewById(R.id.btnAdicionar);
        btnAdicionar.setEnabled(false);

        edtNome.addTextChangedListener(textWatcher);
        edtCpf.addTextChangedListener(textWatcher);
        edtTelefone.addTextChangedListener(textWatcher);
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String text1 = edtNome.getText().toString().trim();
            String text2 = edtCpf.getText().toString().trim();
            String text3 = edtTelefone.getText().toString().trim();
            btnAdicionar.setEnabled(!text1.isEmpty() && !text2.isEmpty() && !text3.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.adicionar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemLimpar) {
            edtNome.setText(null);
            edtCpf.setText(null);
            edtTelefone.setText(null);
        }
        return super.onOptionsItemSelected(item);
    }

    public void adicionarAluno(View v) {
        Aluno aluno = new Aluno();
        aluno.setNome(edtNome.getText().toString());
        aluno.setCpf(edtCpf.getText().toString());
        aluno.setTelefone(edtTelefone.getText().toString());
        AlunoDAO dao = new AlunoDAO(this);
        try {
            dao.insert(aluno);
            Toast.makeText(getApplicationContext(), "Salvo com sucesso", Toast.LENGTH_LONG).show();
        }catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Erro");
            builder.setMessage(e.getMessage());
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


    public void voltar(View v) {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
        finish();
    }
}