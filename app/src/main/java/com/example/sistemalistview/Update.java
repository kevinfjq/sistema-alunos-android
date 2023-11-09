package com.example.sistemalistview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
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

public class Update extends AppCompatActivity {
    private int idAluno;
    private Aluno aluno;

    private EditText edtAttNome, edtAttCpf, edtAttTelefone;
    Button btnAlterar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent it = getIntent();
        idAluno = it.getIntExtra("p_id", 0);
        AlunoDAO dao = new AlunoDAO(this);
        aluno = dao.read(idAluno);


        Toolbar toolbar = findViewById(R.id.toolbarAlterar);
        setSupportActionBar(toolbar);

        edtAttNome = findViewById(R.id.edtAttNome);
        edtAttCpf = findViewById(R.id.edtAttCpf);
        edtAttTelefone = findViewById(R.id.edtAttTelefone);
        btnAlterar = findViewById(R.id.btnAlterar);

        edtAttNome.setText(aluno.getNome());
        edtAttCpf.setText(aluno.getCpf());
        edtAttTelefone.setText(aluno.getTelefone());

        edtAttNome.addTextChangedListener(textWatcher);
        edtAttCpf.addTextChangedListener(textWatcher);
        edtAttTelefone.addTextChangedListener(textWatcher);
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String text1 = edtAttNome.getText().toString().trim();
            String text2 = edtAttCpf.getText().toString().trim();
            String text3 = edtAttTelefone.getText().toString().trim();
            btnAlterar.setEnabled(!text1.isEmpty() && !text2.isEmpty() && !text3.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.clean_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemLimpar) {
            edtAttNome.setText(null);
            edtAttCpf.setText(null);
            edtAttTelefone.setText(null);
        }
        return super.onOptionsItemSelected(item);
    }

    public void alterarAluno(View v) {
        try {
            AlunoDAO dao = new AlunoDAO(this);
            Aluno novoAluno = new Aluno();
            if (!edtAttNome.getText().toString().matches("^[a-zA-Z]+$")) {
                throw new Exception("Nome deve ser composto apenas por caracteres");
            }
            novoAluno.setId(aluno.getId());
            novoAluno.setNome(edtAttNome.getText().toString());
            novoAluno.setCpf(edtAttCpf.getText().toString());
            novoAluno.setTelefone(edtAttTelefone.getText().toString());
            dao.update(novoAluno);
            Toast.makeText(getApplicationContext(), "Alterado com sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLiteConstraintException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Erro");
            builder.setMessage("Cpf ja esta cadastrado");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (Exception er) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Erro");
            builder.setMessage(er.getMessage());
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void voltar(View v){
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
        finish();
    }
}